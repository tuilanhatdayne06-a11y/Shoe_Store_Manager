package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import connectDB.ConnectDB;
import entity.Customer;

public class CustomerDAO {

    // Lay tat ca khach hang
    public ArrayList<Customer> getAllCustomers() throws SQLException {
        ArrayList<Customer> list = new ArrayList<Customer>();

        String sql = "SELECT customerId, customerName, address, phoneNumber, loyaltyPoints "
                   + "FROM dbo.Customer ORDER BY customerId";

        Connection con = ConnectDB.getConnection();
        if (con == null || con.isClosed()) {
            throw new SQLException("Khong ket noi duoc database ShoeStore");
        }

        try (PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Customer kh = new Customer(
                        rs.getString("customerId"),
                        rs.getString("customerName"),
                        rs.getString("address"),
                        rs.getString("phoneNumber"),
                        rs.getInt("loyaltyPoints")
                );
                list.add(kh);
            }
        }
        return list;
    }

    // Tim kiem khach hang theo so dien thoai
    public ArrayList<Customer> findCustomersByPhone(String sdt) throws SQLException {
        ArrayList<Customer> list = new ArrayList<Customer>();
        String sql = "SELECT customerId, customerName, address, phoneNumber, loyaltyPoints "
                   + "FROM dbo.Customer WHERE phoneNumber LIKE ? ORDER BY customerId";

        Connection con = ConnectDB.getConnection();
        if (con == null || con.isClosed()) {
            throw new SQLException("Khong ket noi duoc database ShoeStore");
        }

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, "%" + sdt + "%");
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Customer kh = new Customer(
                            rs.getString("customerId"),
                            rs.getString("customerName"),
                            rs.getString("address"),
                            rs.getString("phoneNumber"),
                            rs.getInt("loyaltyPoints")
                    );
                    list.add(kh);
                }
            }
        }
        return list;
    }
    public Customer findById(String customerId) throws SQLException {
        String sql = "SELECT customerId, customerName, address, phoneNumber, loyaltyPoints "
                   + "FROM dbo.Customer WHERE customerId = ?";
        Connection con = ConnectDB.getConnection();
        if (con == null || con.isClosed()) {
            throw new SQLException("Khong ket noi duoc database ShoeStore");
        }

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, customerId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return new Customer(
                            rs.getString("customerId"),
                            rs.getString("customerName"),
                            rs.getString("address"),
                            rs.getString("phoneNumber"),
                            rs.getInt("loyaltyPoints"));
                }
            }
        }
        return null;
    }

    // Kiem tra SDT da duoc su dung boi khach hang khac
    public boolean isCustomerPhoneUsed(String sdt, String maHienTai) throws SQLException {
        String sql = "SELECT COUNT(*) FROM dbo.Customer "
                   + "WHERE phoneNumber = ? AND customerId <> ?";

        Connection con = ConnectDB.getConnection();
        if (con == null || con.isClosed()) {
            throw new SQLException("Khong ket noi duoc database ShoeStore");
        }

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, sdt);
            pst.setString(2, maHienTai);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    // Sua thong tin, khong sua diem tich luy
    public boolean updateCustomer(Customer kh) throws SQLException {
        String sql = "UPDATE dbo.Customer SET customerName = ?, address = ?, "
                   + "phoneNumber = ? WHERE customerId = ?";

        Connection con = ConnectDB.getConnection();

        if (con == null || con.isClosed()) {
            throw new SQLException("Khong ket noi duoc database ShoeStore");
        }

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, kh.getCustomerName());
            pst.setString(2, kh.getAddress());
            pst.setString(3, kh.getPhoneNumber());
            pst.setString(4, kh.getCustomerId());
            return pst.executeUpdate() > 0;
        }
    }

    // Xoa khach hang theo ma
    public boolean removeCustomer(String maKH) throws SQLException {
        String sql = "DELETE FROM dbo.Customer WHERE customerId = ?";

        Connection con = ConnectDB.getConnection();
        if (con == null || con.isClosed()) {
            throw new SQLException("Khong ket noi duoc database ShoeStore");
        }

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, maKH);
            return pst.executeUpdate() > 0;
        }
    }
    public boolean addCustomer(Customer customer) throws SQLException {
        String sql = "INSERT INTO dbo.Customer "
                   + "(customerId, customerName, address, phoneNumber, loyaltyPoints) "
                   + "VALUES (?, ?, ?, ?, 0)";
        Connection con = ConnectDB.getConnection();
        if (con == null || con.isClosed()) {
            throw new SQLException("Khong ket noi duoc database ShoeStore");
        }
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, customer.getCustomerId());
            pst.setString(2, customer.getCustomerName());
            pst.setString(3, customer.getAddress());
            pst.setString(4, customer.getPhoneNumber());
            return pst.executeUpdate() > 0;
        }
    }
}
