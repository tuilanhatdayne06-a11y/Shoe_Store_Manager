package dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;

import connectDB.ConnectDB;

public class ReportDAO {

    // Lay ngay co giao dich moi nhat de hien thi thong ke khi mo giao dien
    public LocalDate getLatestTransactionDate() throws SQLException {
        String sql = "SELECT MAX(ngay) AS ngayCuoi FROM ("
                   + "SELECT MAX(creationDate) AS ngay FROM dbo.Invoice "
                   + "UNION ALL "
                   + "SELECT MAX(orderDate) AS ngay FROM dbo.PurchaseOrder "
                   + "WHERE status = 'Da nhap') AS t";

        Connection con = ConnectDB.getConnection();
        if (con == null || con.isClosed()) {
            throw new SQLException("Khong ket noi duoc database ShoeStore");
        }

        try (PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                Timestamp ngay = rs.getTimestamp("ngayCuoi");
                if (ngay != null) {
                    return ngay.toLocalDateTime().toLocalDate();
                }
            }
        }
        return LocalDate.now();
    }

    // Tong doanh thu hoa don trong khoang ngay
    public BigDecimal getTotalRevenue(LocalDate tuNgay, LocalDate denNgay)
            throws SQLException {
        String sql = "SELECT COALESCE(SUM(totalAmount), 0) AS tong "
                   + "FROM dbo.Invoice WHERE creationDate >= ? AND creationDate < ?";

        Connection con = ConnectDB.getConnection();
        if (con == null || con.isClosed()) {
            throw new SQLException("Khong ket noi duoc database ShoeStore");
        }

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setTimestamp(1, Timestamp.valueOf(tuNgay.atStartOfDay()));
            pst.setTimestamp(2, Timestamp.valueOf(denNgay.atStartOfDay()));
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getBigDecimal("tong");
                }
            }
        }
        return BigDecimal.ZERO;
    }

    // Chi phi cac phieu nhap da nhap kho
    public BigDecimal getTotalImportCost(LocalDate tuNgay, LocalDate denNgay)
            throws SQLException {
        String sql = "SELECT COALESCE(SUM(d.quantity * d.importPrice), 0) AS tong "
                   + "FROM dbo.PurchaseOrderDetail d "
                   + "JOIN dbo.PurchaseOrder p ON d.purchaseOrderId = p.purchaseOrderId "
                   + "WHERE p.status = 'Da nhap' "
                   + "AND p.orderDate >= ? AND p.orderDate < ?";

        Connection con = ConnectDB.getConnection();
        if (con == null || con.isClosed()) {
            throw new SQLException("Khong ket noi duoc database ShoeStore");
        }

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setTimestamp(1, Timestamp.valueOf(tuNgay.atStartOfDay()));
            pst.setTimestamp(2, Timestamp.valueOf(denNgay.atStartOfDay()));
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getBigDecimal("tong");
                }
            }
        }
        return BigDecimal.ZERO;
    }

    // Top 5 giay ban chay, moi dong gom: ma, ten, so luong
    public ArrayList<Object[]> getTop5SellingShoes(LocalDate tuNgay, LocalDate denNgay)
            throws SQLException {
        ArrayList<Object[]> list = new ArrayList<Object[]>();
        String sql = "SELECT TOP 5 p.productId, p.productName, "
                   + "SUM(d.quantity) AS soLuong "
                   + "FROM dbo.InvoiceDetail d "
                   + "JOIN dbo.Invoice i ON d.invoiceId = i.invoiceId "
                   + "JOIN dbo.ProductDetail pd ON d.productDetailId = pd.productDetailId "
                   + "JOIN dbo.Product p ON pd.productId = p.productId "
                   + "WHERE i.creationDate >= ? AND i.creationDate < ? "
                   + "GROUP BY p.productId, p.productName "
                   + "ORDER BY SUM(d.quantity) DESC, p.productName";

        Connection con = ConnectDB.getConnection();
        if (con == null || con.isClosed()) {
            throw new SQLException("Khong ket noi duoc database ShoeStore");
        }

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setTimestamp(1, Timestamp.valueOf(tuNgay.atStartOfDay()));
            pst.setTimestamp(2, Timestamp.valueOf(denNgay.atStartOfDay()));
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Object[] giay = {
                        rs.getString("productId"),
                        rs.getString("productName"),
                        rs.getInt("soLuong")
                    };
                    list.add(giay);
                }
            }
        }
        return list;
    }

    // Du lieu bieu do: ngay/tuan, doanh thu, chi phi
    public ArrayList<Object[]> getChartData(LocalDate ngayThamChieu, boolean theoTuan)
            throws SQLException {
        ArrayList<Object[]> list = new ArrayList<Object[]>();
        LocalDate batDau = getStartDate(ngayThamChieu, theoTuan);

        if (theoTuan) {
            for (int i = 0; i < 7; i++) {
                LocalDate ngay = batDau.plusDays(i);
                String nhan = (i == 6) ? "CN" : "T" + (i + 2);
                Object[] cot = {
                    nhan,
                    getTotalRevenue(ngay, ngay.plusDays(1)),
                    getTotalImportCost(ngay, ngay.plusDays(1))
                };
                list.add(cot);
            }
        } else {
            LocalDate cuoiThang = batDau.plusMonths(1);
            int soTuan = 1;
            while (batDau.isBefore(cuoiThang)) {
                LocalDate ketThuc = batDau.plusDays(7);
                if (ketThuc.isAfter(cuoiThang)) {
                    ketThuc = cuoiThang;
                }
                Object[] cot = {
                    "Tuần " + soTuan,
                    getTotalRevenue(batDau, ketThuc),
                    getTotalImportCost(batDau, ketThuc)
                };
                list.add(cot);
                batDau = ketThuc;
                soTuan++;
            }
        }
        return list;
    }

    public LocalDate getStartDate(LocalDate ngay, boolean theoTuan) {
        if (theoTuan) {
            return ngay.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        }
        return ngay.withDayOfMonth(1);
    }

    public LocalDate getEndDate(LocalDate ngay, boolean theoTuan) {
        LocalDate batDau = getStartDate(ngay, theoTuan);
        if (theoTuan) {
            return batDau.plusDays(7);
        }
        return batDau.plusMonths(1);
    }
}
