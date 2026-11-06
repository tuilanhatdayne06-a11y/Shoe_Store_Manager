package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import connectDB.connectDB;

public class EmployeeDao {
    
    public ArrayList<Object[]> getAllEmployee() {
        ArrayList<Object[]> list = new ArrayList<>();
        String sql = "SELECT employeeId, employeeName, gender, phoneNumber, position FROM Employee";
        
        Connection con = connectDB.getConnection(); 
        
        try (
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
        ) {
            while (rs.next()) {
                String ma = rs.getString("employeeId");
                String ten = rs.getString("employeeName");
                String gioiTinh = rs.getBoolean("gender") ? "Nam" : "Nữ"; 
                String sdt = rs.getString("phoneNumber");
                String chucVu = rs.getString("position");
                
                list.add(new Object[]{ma, ten, gioiTinh, sdt, chucVu});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}