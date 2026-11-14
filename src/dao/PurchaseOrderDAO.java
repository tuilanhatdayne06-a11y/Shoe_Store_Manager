package dao;

import connectDB.ConnectDB;
import entity.PurchaseOrder;
import entity.PurchaseOrderDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PurchaseOrderDAO {
    private final PurchaseOrderDetailDAO detailDAO = new PurchaseOrderDetailDAO();

    public String createImportOrder(PurchaseOrder order, PurchaseOrderDetail detail)
            throws SQLException {
        validate(order, detail);

        Connection con = ConnectDB.getConnection();
        boolean autoCommit = con.getAutoCommit();
        try {
            con.setAutoCommit(false);

            String sql = "UPDATE IDCounter SET currentValue = currentValue + 1 "
                    + "OUTPUT INSERTED.currentValue WHERE prefix = 'PO'";
            String orderId;
            try (PreparedStatement pst = con.prepareStatement(sql);
                 ResultSet rs = pst.executeQuery()) {
                if (!rs.next()) {
                    throw new SQLException("Không tìm thấy mã PO trong IDCounter.");
                }
                orderId = String.format("PO%03d", rs.getInt(1));
            }

            order.setPurchaseOrderId(orderId);
            detail.setPurchaseOrderId(order);

            sql = "SELECT 1 FROM Product p "
                    + "JOIN ProductDetail pd ON pd.productId = p.productId "
                    + "WHERE p.supplierId = ? AND pd.productDetailId = ?";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setString(1, order.getSupplierId().getSupplierId());
                pst.setString(2, detail.getProductDetailId().getProductDetailId());
                try (ResultSet rs = pst.executeQuery()) {
                    if (!rs.next()) {
                        throw new IllegalArgumentException(
                                "Sản phẩm không thuộc nhà cung cấp đã chọn.");
                    }
                }
            }

            sql = "INSERT INTO PurchaseOrder "
                    + "(purchaseOrderId, orderDate, employeeId, supplierId, status) "
                    + "VALUES (?, GETDATE(), ?, ?, ?)";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setString(1, orderId);
                pst.setString(2, order.getEmployeeId().getEmployeeId());
                pst.setString(3, order.getSupplierId().getSupplierId());
                pst.setString(4, "Da nhap");
                pst.executeUpdate();
            }

            if (!detailDAO.insert(con, detail)) {
                throw new SQLException("Không thể lưu chi tiết phiếu nhập.");
            }

            sql = "UPDATE ProductDetail SET stockQuantity = "
                    + "ISNULL(stockQuantity, 0) + ? WHERE productDetailId = ?";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setInt(1, detail.getQuantity());
                pst.setString(2, detail.getProductDetailId().getProductDetailId());
                if (pst.executeUpdate() != 1) {
                    throw new SQLException("Không tìm thấy chi tiết sản phẩm.");
                }
            }

            con.commit();
            return orderId;
        } catch (SQLException | RuntimeException e) {
            try {
                con.rollback();
            } catch (SQLException rollbackError) {
                e.addSuppressed(rollbackError);
            }
            throw e;
        } finally {
            con.setAutoCommit(autoCommit);
        }
    }

    private void validate(PurchaseOrder order, PurchaseOrderDetail detail) {
        if (order == null || detail == null
                || order.getEmployeeId() == null
                || isEmpty(order.getEmployeeId().getEmployeeId())
                || order.getSupplierId() == null
                || isEmpty(order.getSupplierId().getSupplierId())
                || detail.getProductDetailId() == null
                || isEmpty(detail.getProductDetailId().getProductDetailId())
                || detail.getQuantity() <= 0
                || !Double.isFinite(detail.getImportPrice())
                || detail.getImportPrice() <= 0) {
            throw new IllegalArgumentException("Thông tin phiếu nhập chưa hợp lệ.");
        }
    }

    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
}
