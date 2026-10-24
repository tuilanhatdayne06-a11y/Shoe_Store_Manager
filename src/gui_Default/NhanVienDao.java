package gui_Default;

import HSK.gui_Default.entity.NhanVien;
import connectDB.connectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
public class NhanVienDao {

	public boolean themNhanVien(NhanVien nv) {
        String sql = "INSERT INTO NhanVien (MaNV, HoTen, GioiTinh, ChucVu, MatKhau) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = connectDB.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            
            pst.setString(1, nv.getMaNV());
            pst.setString(2, nv.getHoTen());
            pst.setBoolean(3, nv.isGioiTinh());
            pst.setString(4, nv.getChucVu());
            pst.setString(5, nv.getMatKhau());			
            
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
	public NhanVien dangNhap(String maNV, String matKhau) {
	    String sql = "SELECT * FROM NhanVien WHERE MaNV = ? AND MatKhau = ?";
	    try (Connection con = connectDB.getConnection();
	         PreparedStatement pst = con.prepareStatement(sql)) {
	        
	        pst.setString(1, maNV);
	        pst.setString(2, matKhau);
	        ResultSet rs = pst.executeQuery();
	        
	        if (rs.next()) {
	            return new NhanVien(
	                rs.getString("MaNV"), 
	                rs.getString("HoTen"), 
	                rs.getBoolean("GioiTinh"), 
	                rs.getString("ChucVu"), 
	                rs.getString("MatKhau")
	            );
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}		
}
