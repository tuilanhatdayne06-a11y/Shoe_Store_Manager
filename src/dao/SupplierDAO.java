package dao;

import connectDB.connectDB;
import entity.Supplier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAO {
    public List<Supplier> getAll() {
        List<Supplier> suppliers = new ArrayList<>();
        String sql = "SELECT * FROM Supplier";
        Connection conn = connectDB.getConnection();

        try (
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery()
        ) {
            while (rs.next()) {
                suppliers.add(readSupplier(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return suppliers;
    }

    public Supplier findById(String supplierId) {
        String sql = "SELECT * FROM Supplier WHERE supplierId = ?";
        Connection conn = connectDB.getConnection();

        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, supplierId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return readSupplier(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean insert(Supplier supplier) {
        String sql = "INSERT INTO Supplier (supplierId, supplierName, address, phoneNumber, email, logo) VALUES (?, ?, ?, ?, ?, ?)";
        Connection conn = connectDB.getConnection();

        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            bindSupplier(pst, supplier);
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(String oldSupplierId, Supplier supplier) {
        String sql = "UPDATE Supplier SET supplierId = ?, supplierName = ?, address = ?, phoneNumber = ?, email = ?, logo = ? WHERE supplierId = ?";
        Connection conn = connectDB.getConnection();

        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            bindSupplier(pst, supplier);
            pst.setString(7, oldSupplierId);
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(String supplierId) {
        String sql = "DELETE FROM Supplier WHERE supplierId = ?";
        Connection conn = connectDB.getConnection();

        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, supplierId);
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private Supplier readSupplier(ResultSet rs) {
        try {
            return new Supplier(
                rs.getString("supplierId"),
                rs.getString("supplierName"),
                rs.getString("address"),
                rs.getString("phoneNumber"),
                rs.getString("email"),
                rs.getString("logo")
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void bindSupplier(PreparedStatement pst, Supplier supplier) throws Exception {
        pst.setString(1, supplier.getSupplierId());
        pst.setString(2, supplier.getSupplierName());
        pst.setString(3, supplier.getAddress());
        pst.setString(4, supplier.getPhoneNumber());
        pst.setString(5, supplier.getEmail());
        pst.setString(6, supplier.getLogo());
    }
}
