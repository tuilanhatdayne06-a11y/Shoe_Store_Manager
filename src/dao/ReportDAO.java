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

import connectDB.connectDB;
import entity.Report;

public class ReportDAO {

    // Tong hop doanh thu va chi phi trong mot khoang ngay
    public Report getRevenueAndImportSummary(LocalDate tuNgay, LocalDate denNgay) throws SQLException {
        String sqlBan = "SELECT COALESCE(SUM(totalAmount), 0) AS tong "
                      + "FROM dbo.Invoice WHERE creationDate >= ? AND creationDate < ?";
        String sqlNhap = "SELECT COALESCE(SUM(d.quantity * d.importPrice), 0) AS tong "
                       + "FROM dbo.PurchaseOrderDetail d "
                       + "JOIN dbo.PurchaseOrder p ON d.purchaseOrderId = p.purchaseOrderId "
                       + "WHERE p.orderDate >= ? AND p.orderDate < ?";

        Connection con = connectDB.getConnection();
        if (con == null || con.isClosed()) {
            throw new SQLException("Khong ket noi duoc database ShoeStore");
        }

        BigDecimal doanhThu = tinhTongTienTheoNgay(con, sqlBan, tuNgay, denNgay);
        BigDecimal chiPhi = tinhTongTienTheoNgay(con, sqlNhap, tuNgay, denNgay);
        return new Report(doanhThu, chiPhi);
    }

    private BigDecimal tinhTongTienTheoNgay(Connection con, String sql,
            LocalDate tuNgay, LocalDate denNgay) throws SQLException {
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setTimestamp(1, Timestamp.valueOf(tuNgay.atStartOfDay()));
            pst.setTimestamp(2, Timestamp.valueOf(denNgay.atStartOfDay()));
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    BigDecimal tong = rs.getBigDecimal("tong");
                    if (tong != null) {
                        return tong;
                    }
                }
            }
        }
        return BigDecimal.ZERO;
    }

    // Moi dong gom: ma giay, ten giay, so luong ban
    public ArrayList<Object[]> getTop5SellingShoes(LocalDate tuNgay, LocalDate denNgay)
            throws SQLException {
        ArrayList<Object[]> list = new ArrayList<Object[]>();
        String sql = "SELECT TOP 5 p.productId, p.productName, "
                   + "SUM(d.quantity) AS soLuong "
                   + "FROM dbo.InvoiceDetail d "
                   + "JOIN dbo.Invoice i ON i.invoiceId = d.invoiceId "
                   + "JOIN dbo.ProductDetail pd ON pd.productDetailId = d.productDetailId "
                   + "JOIN dbo.Product p ON p.productId = pd.productId "
                   + "WHERE i.creationDate >= ? AND i.creationDate < ? "
                   + "GROUP BY p.productId, p.productName "
                   + "ORDER BY SUM(d.quantity) DESC, p.productName";

        Connection con = connectDB.getConnection();
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

    // Moi dong bieu do: nhan cot, doanh thu, chi phi
    public ArrayList<Object[]> getRevenueAndImportChart(boolean theoTuan) throws SQLException {
        ArrayList<Object[]> list = new ArrayList<Object[]>();
        LocalDate ngayBatDau = getReportStartDate(theoTuan);

        if (theoTuan) {
            for (int i = 0; i < 7; i++) {
                LocalDate ngay = ngayBatDau.plusDays(i);
                Report report = getRevenueAndImportSummary(ngay, ngay.plusDays(1));
                String nhan = i == 6 ? "CN" : "T" + (i + 2);
                Object[] cot = {nhan, report.getTotalRevenue(), report.getTotalImportCost()};
                list.add(cot);
            }
        } else {
            LocalDate dauThangSau = ngayBatDau.plusMonths(1);
            int soTuan = 1;
            while (ngayBatDau.isBefore(dauThangSau)) {
                LocalDate ketThuc = ngayBatDau.plusDays(7);
                if (ketThuc.isAfter(dauThangSau)) {
                    ketThuc = dauThangSau;
                }

                Report report = getRevenueAndImportSummary(ngayBatDau, ketThuc);
                Object[] cot = {"Tuần " + soTuan,
                                report.getTotalRevenue(), report.getTotalImportCost()};
                list.add(cot);
                ngayBatDau = ketThuc;
                soTuan++;
            }
        }
        return list;
    }

    public LocalDate getReportStartDate(boolean theoTuan) {
        LocalDate homNay = LocalDate.now();
        if (theoTuan) {
            return homNay.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        }
        return homNay.withDayOfMonth(1);
    }

    public LocalDate getReportEndDate(boolean theoTuan) {
        LocalDate batDau = getReportStartDate(theoTuan);
        if (theoTuan) {
            return batDau.plusDays(7);
        }
        return batDau.plusMonths(1);
    }
}
