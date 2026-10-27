
package dao;

import HSK.gui_Default.entity.NhanVien;
import connectDB.connectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
public class NhanVienDao {

	public boolean themNhanVien(NhanVien nv) {
        String sql = "INSERT INTO Employee (employeeId, employeeName, gender, phoneNumber, position, password) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlAccount = "INSERT INTO Account (employeeId, password, role) VALUES (?, ?, ?)";
        try (Connection con = connectDB.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
        		PreparedStatement pstAccount = con.prepareStatement(sqlAccount)){
            
            pst.setString(1, nv.getEmployeeId());
            pst.setString(2, nv.getEmployeeName());
            pst.setBoolean(3, nv.isGender());
            pst.setString(4, nv.getPhoneNumber());
            pst.setString(5, nv.getPosition());
            pst.setString(6, nv.getPass());
            
            int updateEmployee = pst.executeUpdate();
            
            pstAccount.setString(1, nv.getEmployeeId());
            pstAccount.setString(2, nv.getPass());
            pstAccount.setString(3, nv.getPosition());
            
            int updateAccount = pstAccount.executeUpdate();
            
            return updateEmployee > 0 && updateAccount > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
	public NhanVien dangNhap(String maNV, String matKhau) {
	    String sql = "SELECT * FROM Employee WHERE employeeId = ? AND password = ?;";
	    try (Connection con = connectDB.getConnection();
	         PreparedStatement pst = con.prepareStatement(sql)) {
	    	
	        pst.setString(1, maNV);
	        pst.setString(2, matKhau);
	        
	        ResultSet rs = pst.executeQuery();
	        if (rs.next()) {
                System.out.println("Tim thay nhan vien!");

                return new NhanVien(
                    rs.getString("employeeId"),
                    rs.getString("employeeName"),
                    rs.getBoolean("gender"),
                    rs.getString("phoneNumber"),
                    rs.getString("position"),
                    rs.getString("password")
                );
            } else {
                System.out.println("Khong tim thay nhan vien!");
            }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}		
}