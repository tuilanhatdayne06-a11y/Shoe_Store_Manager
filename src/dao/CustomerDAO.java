
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connectDB.connectDB;
import entity.Customer;

public class CustomerDAO {

    private Connection getConnection() throws SQLException {
        Connection con = connectDB.getConnection();
        if (con == null || con.isClosed()) {
            throw new SQLException("Khong ket noi duoc database ShoeStore");
        }
        return con;
    }

    // Lay danh sach khach hang
    public ArrayList<Customer> getAll() throws SQLException {
        ArrayList<Customer> ds = new ArrayList<Customer>();
        String sql = "SELECT customerId, customerName, address, phoneNumber, loyaltyPoints "
                   + "FROM dbo.Customer ORDER BY customerId";

        Connection con = getConnection();
        try (PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Customer kh = new Customer(
                    rs.getString("customerId"),
                    rs.getString("customerName"),
                    rs.getString("address"),
                    rs.getString("phoneNumber"),
                    rs.getInt("loyaltyPoints")
                );
                ds.add(kh);
            }
        }
        return ds;
    }

    // Tim khach hang theo so dien thoai
    public ArrayList<Customer> timTheoSDT(String sdt) throws SQLException {
        ArrayList<Customer> ds = new ArrayList<Customer>();
        String sql = "SELECT customerId, customerName, address, phoneNumber, loyaltyPoints "
                   + "FROM dbo.Customer WHERE phoneNumber LIKE ? ORDER BY customerId";

        Connection con = getConnection();
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, "%" + sdt + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Customer kh = new Customer(
                        rs.getString("customerId"),
                        rs.getString("customerName"),
                        rs.getString("address"),
                        rs.getString("phoneNumber"),
                        rs.getInt("loyaltyPoints")
                    );
                    ds.add(kh);
                }
            }
        }
        return ds;
    }

    // Khach hang moi duoc 0 diem
    public boolean them(Customer kh) throws SQLException {
        String sql = "INSERT INTO dbo.Customer "
                   + "(customerId, customerName, address, phoneNumber, loyaltyPoints) "
                   + "VALUES (?, ?, ?, ?, 0)";

        Connection con = getConnection();
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, kh.getCustomerId());
            stmt.setString(2, kh.getCustomerName());
            stmt.setString(3, kh.getAddress());
            stmt.setString(4, kh.getPhoneNumber());
            return stmt.executeUpdate() > 0;
        }
    }

    // Sua thong tin, khong sua diem tich luy
    public boolean sua(Customer kh) throws SQLException {
        String sql = "UPDATE dbo.Customer SET customerName = ?, address = ?, "
                   + "phoneNumber = ? WHERE customerId = ?";

        Connection con = getConnection();
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, kh.getCustomerName());
            stmt.setString(2, kh.getAddress());
            stmt.setString(3, kh.getPhoneNumber());
            stmt.setString(4, kh.getCustomerId());
            return stmt.executeUpdate() > 0;
        }
    }

    // Xoa khach hang theo ma
    public boolean xoa(String maKH) throws SQLException {
        String sql = "DELETE FROM dbo.Customer WHERE customerId = ?";

        Connection con = getConnection();
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, maKH);
            return stmt.executeUpdate() > 0;
        }
    }
    // Kiem tra so dien thoai da co o khach hang khac hay chua
    public boolean trungSoDienTho(String sdt, String maHienTai) throws SQLException {
        String sql = "SELECT COUNT(*) FROM dbo.Customer "
                   + "WHERE phoneNumber = ? AND customerId <> ?";

        Connection con = getConnection();
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, sdt);
            pst.setString(2, maHienTai);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        }
        return false;
    }


    // Tu sinh ma KH tu bang IDCounter va them khach hang.
    // Hai cau lenh duoc thuc hien chung mot transaction.
    public String themKhachHangTuSinhMa(String ten, String diaChi, String sdt)
            throws SQLException {
        Connection con = getConnection();
        if (!con.getAutoCommit()) {
            throw new SQLException("Ket noi dang co giao dich chua hoan tat");
        }

        String sqlMa = "UPDATE dbo.IDCounter SET currentValue = currentValue + 1 "
                     + "OUTPUT INSERTED.currentValue WHERE prefix = ?";
        String sqlThem = "INSERT INTO dbo.Customer "
                       + "(customerId, customerName, address, phoneNumber, loyaltyPoints) "
                       + "VALUES (?, ?, ?, ?, 0)";

        con.setAutoCommit(false);
        try {
            int soThuTu;
            try (PreparedStatement pst = con.prepareStatement(sqlMa)) {
                pst.setString(1, "KH");
                try (ResultSet rs = pst.executeQuery()) {
                    if (!rs.next()) {
                        throw new SQLException("Chua co ma KH trong bang IDCounter");
                    }
                    soThuTu = rs.getInt(1);
                }
            }

            String maKH = "KH" + String.format("%03d", soThuTu);
            try (PreparedStatement pst = con.prepareStatement(sqlThem)) {
                pst.setString(1, maKH);
                pst.setString(2, ten);
                pst.setString(3, diaChi);
                pst.setString(4, sdt);
                if (pst.executeUpdate() != 1) {
                    throw new SQLException("Them khach hang khong thanh cong");
                }
            }
            con.commit();
            return maKH;
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                e.addSuppressed(ex);
            }
            throw e;
        } finally {
            con.setAutoCommit(true);
        }
    }

}
