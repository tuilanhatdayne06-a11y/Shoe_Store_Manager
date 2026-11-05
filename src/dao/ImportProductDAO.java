package dao;

import connectDB.connectDB;
import entity.Supplier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImportProductDAO {
    public List<Supplier> getSuppliers() throws SQLException {
        List<Supplier> list = new ArrayList<>();
        String sql = "SELECT supplierId, supplierName FROM Supplier ORDER BY supplierName";
        Connection conn = connectDB.getConnection();

        try (PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                list.add(new Supplier(rs.getString("supplierId"),
                        rs.getString("supplierName"), null, null, null, null));
            }
        }
        return list;
    }

    public List<ProductOption> getProductsBySupplier(String supplierId) throws SQLException {
        List<ProductOption> list = new ArrayList<>();
        String sql = "SELECT pd.productDetailId, p.productName, pd.size, pd.color "
                + "FROM Product p JOIN ProductDetail pd ON p.productId = pd.productId WHERE p.supplierId = ?";
        Connection conn = connectDB.getConnection();

        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, supplierId);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    list.add(new ProductOption(
                            rs.getString("productDetailId"),
                            rs.getString("productName"),
                            rs.getString("size"),
                            rs.getString("color")));
                }
            }
        }
        return list;
    }

    public List<Object[]> getPurchaseHistory() throws SQLException {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT po.purchaseOrderId, s.supplierName, p.productName, pd.size, d.importPrice, d.quantity, po.orderDate "
                + "FROM PurchaseOrder po JOIN Supplier s ON s.supplierId = po.supplierId "
                + "JOIN PurchaseOrderDetail d ON d.purchaseOrderId = po.purchaseOrderId "
                + "JOIN ProductDetail pd ON pd.productDetailId = d.productDetailId JOIN Product p ON p.productId = pd.productId "
                + "ORDER BY po.orderDate DESC";
        Connection conn = connectDB.getConnection();

        try (PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                double importPrice = rs.getDouble("importPrice");
                int quantity = rs.getInt("quantity");
                list.add(new Object[]{
                        rs.getString("purchaseOrderId"),
                        rs.getString("supplierName"),
                        rs.getString("productName"),
                        rs.getString("size"),
                        importPrice,
                        quantity,
                        importPrice * quantity,
                        rs.getTimestamp("orderDate")
                });
            }
        }
        return list;
    }

    public String createPurchaseOrder(String supplierId, String productDetailId,
            int quantity, double importPrice) throws SQLException {
        if (quantity <= 0 || Double.isNaN(importPrice) || Double.isInfinite(importPrice)
                || importPrice <= 0) {
            throw new IllegalArgumentException("Số lượng và đơn giá phải lớn hơn 0.");
        }

        Connection conn = connectDB.getConnection();
        boolean autoCommit = conn.getAutoCommit();

        try {
            conn.setAutoCommit(false);

            String idSql = "UPDATE IDCounter SET currentValue = currentValue + 1 OUTPUT INSERTED.currentValue WHERE prefix = 'PO'";
            String orderId;
            try (PreparedStatement pst = conn.prepareStatement(idSql);
                 ResultSet rs = pst.executeQuery()) {
                if (!rs.next()) {
                    throw new SQLException("Không tìm thấy mã tự tăng PO trong IDCounter.");
                }
                orderId = String.format("PO%03d", rs.getInt(1));
            }

            String orderSql = "INSERT INTO PurchaseOrder (purchaseOrderId, orderDate, employeeId, supplierId) VALUES (?, GETDATE(), NULL, ?)";
            try (PreparedStatement pst = conn.prepareStatement(orderSql)) {
                pst.setString(1, orderId);
                pst.setString(2, supplierId);
                pst.executeUpdate();
            }

            String checkSql = "SELECT COUNT(*) FROM Product p JOIN ProductDetail pd ON p.productId = pd.productId "
                    + "WHERE p.supplierId = ? AND pd.productDetailId = ?";
            try (PreparedStatement pst = conn.prepareStatement(checkSql)) {
                pst.setString(1, supplierId);
                pst.setString(2, productDetailId);
                try (ResultSet rs = pst.executeQuery()) {
                    if (!rs.next() || rs.getInt(1) == 0) {
                        throw new IllegalArgumentException(
                                "Mẫu giày không thuộc nhà cung cấp đã chọn.");
                    }
                }
            }

            String detailSql = "INSERT INTO PurchaseOrderDetail (purchaseOrderId, productDetailId, quantity, importPrice) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pst = conn.prepareStatement(detailSql)) {
                pst.setString(1, orderId);
                pst.setString(2, productDetailId);
                pst.setInt(3, quantity);
                pst.setDouble(4, importPrice);
                pst.executeUpdate();
            }

            String stockSql = "UPDATE ProductDetail SET stockQuantity = ISNULL(stockQuantity, 0) + ? WHERE productDetailId = ?";
            try (PreparedStatement pst = conn.prepareStatement(stockSql)) {
                pst.setInt(1, quantity);
                pst.setString(2, productDetailId);
                if (pst.executeUpdate() != 1) {
                    throw new IllegalArgumentException("Không tìm thấy mẫu giày cần nhập kho.");
                }
            }

            conn.commit();
            return orderId;
        } catch (SQLException | RuntimeException e) {
            try {
                conn.rollback();
            } catch (SQLException rollbackError) {
                e.addSuppressed(rollbackError);
            }
            throw e;
        } finally {
            conn.setAutoCommit(autoCommit);
        }
    }

    public static class ProductOption {
        private final String productDetailId;
        private final String productName;
        private final String size;
        private final String color;

        public ProductOption(String productDetailId, String productName, String size, String color) {
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
