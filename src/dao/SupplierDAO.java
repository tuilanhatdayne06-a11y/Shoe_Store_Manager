package dao;

import connectDB.ConnectDB;
import entity.Supplier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAO {
	
	public Supplier getSupplierByName(String name)
	{
		String sql = "select * from Supplier where supplierName = ?";
		Connection con = ConnectDB.getConnection();
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			
			pst.setString(1, name);
			
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				return new Supplier(
					rs.getString("supplierId"),
					rs.getString("supplierName"),
					rs.getString("address"),
					rs.getString("phoneNumber"),
					rs.getString("email"),
					rs.getString("logo")
				);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
    public List<Supplier> getAll() throws SQLException {
        List<Supplier> suppliers = new ArrayList<>();
        String sql = "SELECT * FROM Supplier";
        Connection conn = ConnectDB.getConnection();
        if (conn == null) {
            throw new SQLException("Không thể kết nối cơ sở dữ liệu.");
        }

        try (PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                suppliers.add(readSupplier(rs));
            }
        }
        return suppliers;
    }

    public List<String> getSupplierName() {
        List<String> suppliers = new ArrayList<>();
        String sql = "SELECT * FROM Supplier";
        Connection conn = ConnectDB.getConnection();


        try (
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery()
        ) {
            while (rs.next()) {
                suppliers.add(rs.getString(2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return suppliers;
    }
    public Supplier findById(String supplierId) {
        String sql = "SELECT * FROM Supplier WHERE supplierId = ?";
        Connection conn = ConnectDB.getConnection();

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

    public String getNextSupplierId() throws SQLException {
        String sql = "SELECT currentValue + 1 FROM IDCounter WHERE prefix = ?";
        Connection conn = ConnectDB.getConnection();
        if (conn == null) {
            throw new SQLException("Không thể kết nối cơ sở dữ liệu.");
        }

        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, "NCC");
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return "NCC" + String.format("%03d", rs.getInt(1));
                }
            }
        }
        throw new SQLException("Không tìm thấy bộ đếm mã nhà cung cấp NCC.");
    }


    public boolean insertSupplier(Supplier supplier) {
        String sqlId = "UPDATE IDCounter SET currentValue = currentValue + 1 "
                     + "OUTPUT INSERTED.currentValue WHERE prefix = 'NCC'";
        String sql = "INSERT INTO Supplier (supplierId, supplierName, address, phoneNumber, email, logo) VALUES (?, ?, ?, ?, ?, ?)";
        Connection conn = ConnectDB.getConnection();
        if (conn == null) {
            return false;
        }

        try {
            conn.setAutoCommit(false);
            int number;

            try (PreparedStatement pstId = conn.prepareStatement(sqlId);
                 ResultSet rs = pstId.executeQuery()) {
                if (!rs.next()) {
                    conn.rollback();
                    return false;
                }
                number = rs.getInt(1);
            }

            supplier.setSupplierId("NCC" + String.format("%03d", number));

            try (PreparedStatement pst = conn.prepareStatement(sql)) {
                bindSupplier(pst, supplier);
                if (pst.executeUpdate() == 0) {
                    conn.rollback();
                    return false;
                }
            }

            conn.commit();
            return true;
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public boolean updateSupplier(String oldSupplierId, Supplier supplier) {
        String sql = "UPDATE Supplier SET supplierId = ?, supplierName = ?, address = ?, phoneNumber = ?, email = ?, logo = ? WHERE supplierId = ?";
        Connection conn = ConnectDB.getConnection();

        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            bindSupplier(pst, supplier);
            pst.setString(7, oldSupplierId);
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteSupplier(String supplierId) {
        String sql = "DELETE FROM Supplier WHERE supplierId = ?";
        Connection conn = ConnectDB.getConnection();


        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, supplierId);
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private Supplier readSupplier(ResultSet rs) throws SQLException {
        return new Supplier(
                rs.getString("supplierId"),
                rs.getString("supplierName"),
                rs.getString("address"),
                rs.getString("phoneNumber"),
                rs.getString("email"),
                rs.getString("logo")
        );
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
