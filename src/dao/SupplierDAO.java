package dao;

import HSK.gui_Default.entity.Supplier;
import connectDB.connectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAO {
    public List<Supplier> getAll() throws SQLException {
        List<Supplier> suppliers = new ArrayList<>();
        String sql = "select * from Supplier";

        try (Connection con = connectDB.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                suppliers.add(readSupplier(rs));
            }
        }
        return suppliers;
    }

    public List<String> getAllSupplier() throws SQLException {
        List<String> suppliers = new ArrayList<>();
        String sql = "select * from Supplier";

        try (Connection con = connectDB.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                suppliers.add(rs.getString(2));
            }
        }
        return suppliers;
    }

    public Supplier findById(String supplierId) throws SQLException {
        String sql = "select * from Supplier where supplierId = ?";

        try (Connection con = connectDB.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, supplierId);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next() ? readSupplier(rs) : null;
            }
        }
    }

    public boolean insert(Supplier supplier) throws SQLException {
        String sql = "insert into Supplier "
                + "(supplierId, supplierName, address, phoneNumber, email, logo) "
                + "values (?, ?, ?, ?, ?, ?)";

        try (Connection con = connectDB.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            bindSupplier(pst, supplier);
            return pst.executeUpdate() > 0;
        }
    }

    public boolean update(String oldSupplierId, Supplier supplier) throws SQLException {
        String sql = "update Supplier set supplierId = ?, supplierName = ?, address = ?, "
                + "phoneNumber = ?, email = ?, logo = ? where supplierId = ?";

        try (Connection con = connectDB.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            bindSupplier(pst, supplier);
            pst.setString(7, oldSupplierId);
            return pst.executeUpdate() > 0;
        }
    }

    public boolean delete(String supplierId) throws SQLException {
        String sql = "delete from Supplier where supplierId = ?";

        try (Connection con = connectDB.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, supplierId);
            return pst.executeUpdate() > 0;
        }
    }

    private Supplier readSupplier(ResultSet rs) throws SQLException {
        return new Supplier(
                rs.getString("supplierId"),
                rs.getString("supplierName"),
                rs.getString("address"),
                rs.getString("phoneNumber"),
                rs.getString("email"),
                rs.getString("logo"));
    }

    private void bindSupplier(PreparedStatement pst, Supplier supplier)
            throws SQLException {
        pst.setString(1, supplier.getSupplierId());
        pst.setString(2, supplier.getSupplierName());
        pst.setString(3, supplier.getAddress());
        pst.setString(4, supplier.getPhoneNumber());
        pst.setString(5, supplier.getEmail());
        pst.setString(6, supplier.getLogo());
    }
}
