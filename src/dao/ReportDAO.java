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

    private Connection getConnection() throws SQLException {
        Connection con = connectDB.getConnection();
        if (con == null || con.isClosed()) {
            throw new SQLException("Khong ket noi duoc database ShoeStore");
        }
        return con;
    }

    public static class TopShoe {
        public String ma;
        public String ten;
        public int soLuong;

        public TopShoe(String ma, String ten, int soLuong) {
            this.ma = ma;
            this.ten = ten;
            this.soLuong = soLuong;
        }
    }

    public static class ChartBar {
        public String nhan;
        public BigDecimal doanhThu;
        public BigDecimal chiPhi;

        public ChartBar(String nhan, BigDecimal doanhThu, BigDecimal chiPhi) {
            this.nhan = nhan;
            this.doanhThu = doanhThu;
            this.chiPhi = chiPhi;
        }
    }

    // Tong doanh thu va chi phi trong khoang [tuNgay, denNgay).
    public Report getSummary(LocalDate tuNgay, LocalDate denNgay) throws SQLException {
        Connection con = getConnection();
        return tinhDoanhThuVaChiPhi(con, tuNgay, denNgay);
    }

    private Report tinhDoanhThuVaChiPhi(Connection con, LocalDate tuNgay,
                                     LocalDate denNgay) throws SQLException {
        String sqlBan = "SELECT COALESCE(SUM(totalAmount), 0) AS tong "
                      + "FROM dbo.Invoice "
                      + "WHERE creationDate >= ? AND creationDate < ?";

        String sqlNhap = "SELECT COALESCE(SUM(d.quantity * d.importPrice), 0) AS tong "
                       + "FROM dbo.PurchaseOrderDetail d "
                       + "JOIN dbo.PurchaseOrder p ON d.purchaseOrderId = p.purchaseOrderId "
                       + "WHERE p.orderDate >= ? AND p.orderDate < ?";

        BigDecimal doanhThu = layTongTien(con, sqlBan, tuNgay, denNgay);
        BigDecimal chiPhi = layTongTien(con, sqlNhap, tuNgay, denNgay);
        return new Report(doanhThu, chiPhi);
    }

    private BigDecimal layTongTien(Connection con, String sql,
                               LocalDate tuNgay, LocalDate denNgay) throws SQLException {
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setTimestamp(1, Timestamp.valueOf(tuNgay.atStartOfDay()));
            stmt.setTimestamp(2, Timestamp.valueOf(denNgay.atStartOfDay()));

            try (ResultSet rs = stmt.executeQuery()) {
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

    // Top 5 mau giay theo tong so luong da ban.
    public ArrayList<TopShoe> getTop5(LocalDate tuNgay, LocalDate denNgay)
            throws SQLException {
        ArrayList<TopShoe> ds = new ArrayList<TopShoe>();
        String sql = "SELECT TOP 5 p.productId, p.productName, "
                   + "SUM(d.quantity) AS soLuong "
                   + "FROM dbo.InvoiceDetail d "
                   + "JOIN dbo.Invoice i ON i.invoiceId = d.invoiceId "
                   + "JOIN dbo.ProductDetail pd ON pd.productDetailId = d.productDetailId "
                   + "JOIN dbo.Product p ON p.productId = pd.productId "
                   + "WHERE i.creationDate >= ? AND i.creationDate < ? "
                   + "GROUP BY p.productId, p.productName "
                   + "ORDER BY SUM(d.quantity) DESC, p.productName";

        Connection con = getConnection();
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setTimestamp(1, Timestamp.valueOf(tuNgay.atStartOfDay()));
            stmt.setTimestamp(2, Timestamp.valueOf(denNgay.atStartOfDay()));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    TopShoe giay = new TopShoe(
                        rs.getString("productId"),
                        rs.getString("productName"),
                        rs.getInt("soLuong")
                    );
                    ds.add(giay);
                }
            }
        }
        return ds;
    }

    // Tuan: tung ngay trong tuan. Thang: tung nhom 7 ngay trong thang.
    public ArrayList<ChartBar> getBieuDo(boolean theoTuan) throws SQLException {
        ArrayList<ChartBar> ds = new ArrayList<ChartBar>();
        Connection con = getConnection();
        LocalDate batDau = getNgayBatDau(theoTuan);

        if (theoTuan) {
            for (int i = 0; i < 7; i++) {
                LocalDate ngay = batDau.plusDays(i);
                Report tong = tinhDoanhThuVaChiPhi(con, ngay, ngay.plusDays(1));
                String nhan = i == 6 ? "CN" : "T" + (i + 2);
                ds.add(new ChartBar(nhan, tong.getTotalRevenue(), tong.getTotalImportCost()));
            }
        } else {
            LocalDate dauThangSau = batDau.plusMonths(1);
            int tuan = 1;
            while (batDau.isBefore(dauThangSau)) {
                LocalDate ketThuc = batDau.plusDays(7);
                if (ketThuc.isAfter(dauThangSau)) {
                    ketThuc = dauThangSau;
                }
                Report tong = tinhDoanhThuVaChiPhi(con, batDau, ketThuc);
                ds.add(new ChartBar("Tuần " + tuan, tong.getTotalRevenue(), tong.getTotalImportCost()));
                batDau = ketThuc;
                tuan++;
            }
        }
        return ds;
    }

    public LocalDate getNgayBatDau(boolean theoTuan) {
        LocalDate homNay = LocalDate.now();
        if (theoTuan) {
            return homNay.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        }
        return homNay.withDayOfMonth(1);
    }

    public LocalDate getNgayKetThuc(boolean theoTuan) {
        LocalDate batDau = getNgayBatDau(theoTuan);
        if (theoTuan) {
            return batDau.plusDays(7);
        }
        return batDau.plusMonths(1);
    }
}
