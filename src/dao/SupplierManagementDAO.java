package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.Supplier;

public class SupplierManagementDAO {

    // Lay danh sach nha cung cap tu database
    public ArrayList<Supplier> getAll() throws SQLException {
        ArrayList<Supplier> list = new ArrayList<Supplier>();
        String sql = "SELECT supplierId, supplierName, address, phoneNumber, email, logo "
                   + "FROM Supplier ORDER BY supplierId";

        Connection con = ConnectDB.getConnection();
        if (con == null || con.isClosed()) {
            throw new SQLException("Khong ket noi duoc database ShoeStore");
        }

        try (PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Supplier ncc = new Supplier(
                        rs.getString("supplierId"),
                        rs.getString("supplierName"),
                        rs.getString("address"),
                        rs.getString("phoneNumber"),
                        rs.getString("email"),
                        rs.getString("logo"));
                list.add(ncc);
            }
        }
        return list;
    }

    // Tim nha cung cap theo ma
    public Supplier findById(String supplierId) throws SQLException {
        String sql = "SELECT supplierId, supplierName, address, phoneNumber, email, logo "
                   + "FROM Supplier WHERE supplierId = ?";

        Connection con = ConnectDB.getConnection();
        if (con == null || con.isClosed()) {
            throw new SQLException("Khong ket noi duoc database ShoeStore");
        }

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, supplierId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return new Supplier(
                            rs.getString("supplierId"),
                            rs.getString("supplierName"),
                            rs.getString("address"),
                            rs.getString("phoneNumber"),
                            rs.getString("email"),
                            rs.getString("logo"));
                }
            }
        }
        return null;
    }

    // Tu tang IDCounter va them nha cung cap trong cung giao dich
    public boolean insert(Supplier supplier) throws SQLException {
        String sqlMa = "UPDATE IDCounter SET currentValue = currentValue + 1 "
                     + "OUTPUT INSERTED.currentValue WHERE prefix = 'NCC'";
        String sqlThem = "INSERT INTO Supplier "
                       + "(supplierId, supplierName, address, phoneNumber, email, logo) "
                       + "VALUES (?, ?, ?, ?, ?, ?)";

        Connection con = ConnectDB.getConnection();
        if (con == null || con.isClosed()) {
            throw new SQLException("Khong ket noi duoc database ShoeStore");
        }
        if (!con.getAutoCommit()) {
            throw new SQLException("Ket noi dang co giao dich chua hoan tat");
        }

        con.setAutoCommit(false);
        try {
            String maNCC;
            try (PreparedStatement pstMa = con.prepareStatement(sqlMa);
                 ResultSet rs = pstMa.executeQuery()) {
                if (!rs.next()) {
                    throw new SQLException("Chua co prefix NCC trong bang IDCounter");
                }
                int so = rs.getInt(1);
                maNCC = "NCC" + String.format("%03d", so);
            }

            int ketQua;
            try (PreparedStatement pst = con.prepareStatement(sqlThem)) {
                pst.setString(1, maNCC);
                pst.setString(2, supplier.getSupplierName());
                pst.setString(3, supplier.getAddress());
                pst.setString(4, supplier.getPhoneNumber());
                pst.setString(5, supplier.getEmail());
                pst.setString(6, supplier.getLogo());
                ketQua = pst.executeUpdate();
            }

            if (ketQua == 0) {
                con.rollback();
                return false;
            }

            con.commit();
            supplier.setSupplierId(maNCC);
            return true;
        } catch (SQLException e) {
            con.rollback();
            throw e;
        } finally {
            con.setAutoCommit(true);
        }
    }

    // Sua thong tin, khong doi ma nha cung cap
    public boolean update(String oldSupplierId, Supplier supplier) throws SQLException {
        String sql = "UPDATE Supplier SET supplierName = ?, address = ?, "
                   + "phoneNumber = ?, email = ?, logo = ? WHERE supplierId = ?";

        Connection con = ConnectDB.getConnection();
        if (con == null || con.isClosed()) {
            throw new SQLException("Khong ket noi duoc database ShoeStore");
        }

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, supplier.getSupplierName());
            pst.setString(2, supplier.getAddress());
            pst.setString(3, supplier.getPhoneNumber());
            pst.setString(4, supplier.getEmail());
            pst.setString(5, supplier.getLogo());
            pst.setString(6, oldSupplierId);
            return pst.executeUpdate() > 0;
        }
    }

    // Xoa nha cung cap theo ma
    public boolean delete(String supplierId) throws SQLException {
        String sql = "DELETE FROM Supplier WHERE supplierId = ?";

        Connection con = ConnectDB.getConnection();
        if (con == null || con.isClosed()) {
            throw new SQLException("Khong ket noi duoc database ShoeStore");
        }

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, supplierId);
            return pst.executeUpdate() > 0;
        }
    }
}
