package dao;

import connectDB.ConnectDB;
import entity.Supplier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImportProductDAO {
    public List<Supplier> getSuppliers() throws SQLException {
        List<Supplier> suppliers = new ArrayList<>();
        String sql = "SELECT supplierId, supplierName FROM Supplier ORDER BY supplierName";
        Connection conn = ConnectDB.getConnection();

        try (PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                suppliers.add(new Supplier(rs.getString("supplierId"),
                        rs.getString("supplierName"), null, null, null, null));
            }
        }
        return suppliers;
    }

    public List<ProductOption> getProductsBySupplier(String supplierId) throws SQLException {
        List<ProductOption> products = new ArrayList<>();
        String sql = "SELECT pd.productDetailId, p.productName, pd.size, pd.color "
                + "FROM Product p JOIN ProductDetail pd ON p.productId = pd.productId "
                + "WHERE p.supplierId = ? ORDER BY p.productName, pd.color, pd.size";
        Connection conn = ConnectDB.getConnection();

        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, supplierId);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    products.add(new ProductOption(rs.getString("productDetailId"),
                            rs.getString("productName"), rs.getString("size"),
                            rs.getString("color")));
                }
            }
        }
        return products;
    }

    public List<Object[]> getPurchaseHistory() throws SQLException {
        List<Object[]> history = new ArrayList<>();
        String sql = "SELECT po.purchaseOrderId, s.supplierName, p.productName, "
                + "pd.size, d.importPrice, d.quantity, po.orderDate "
                + "FROM PurchaseOrder po "
                + "JOIN Supplier s ON s.supplierId = po.supplierId "
                + "JOIN PurchaseOrderDetail d ON d.purchaseOrderId = po.purchaseOrderId "
                + "JOIN ProductDetail pd ON pd.productDetailId = d.productDetailId "
                + "JOIN Product p ON p.productId = pd.productId "
                + "ORDER BY po.orderDate DESC";
        Connection conn = ConnectDB.getConnection();

        try (PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                double price = rs.getDouble("importPrice");
                int quantity = rs.getInt("quantity");
                history.add(new Object[]{rs.getString("purchaseOrderId"),
                    rs.getString("supplierName"), rs.getString("productName"),
                    rs.getString("size"), price, quantity, price * quantity,
                    rs.getTimestamp("orderDate")});
            }
        }
        return history;
    }

    public static class ProductOption {
        private final String productDetailId;
        private final String productName;
        private final String size;
        private final String color;

        public ProductOption(String productDetailId, String productName,
                String size, String color) {
            this.productDetailId = productDetailId;
            this.productName = productName;
            this.size = size;
            this.color = color;
        }

        public String getProductDetailId() {
            return productDetailId;
        }

        public String getProductName() {
            return productName;
        }

        public String getSize() {
            return size;
        }

        public String getColor() {
            return color;
        }
    }
}
