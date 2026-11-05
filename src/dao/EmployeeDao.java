package dao;

import entity.Employee;
import connectDB.connectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeDao {

    public boolean themNhanVien(Employee nv) {
        String sql = "INSERT INTO Employee (employeeId, employeeName, gender, phoneNumber, position, password) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlAccount = "INSERT INTO Account (employeeId, password, role) VALUES (?, ?, ?)";
        Connection con = connectDB.getConnection();
        PreparedStatement pst = null;
        PreparedStatement pstAccount = null;
        
        try {
            con.setAutoCommit(false);
            
            pst = con.prepareStatement(sql);
            pstAccount = con.prepareStatement(sqlAccount);
            
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
        } catch (SQLException e) {
            try {
                if (con != null) con.rollback(); 
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (pst != null) pst.close();
                if (pstAccount != null) pstAccount.close();
                if (con != null) con.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Employee dangNhap(String maNV, String matKhau) {
        String sql = "SELECT * FROM Employee WHERE employeeId = ? AND password = ?;";
        
        Connection con = connectDB.getConnection();
        
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, maNV);
            pst.setString(2, matKhau);
            
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                System.out.println("Tim thay nhan vien!");

                return new Employee(
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