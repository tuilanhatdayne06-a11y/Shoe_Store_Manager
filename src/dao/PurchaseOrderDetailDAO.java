package dao;

import entity.PurchaseOrderDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PurchaseOrderDetailDAO {
    public boolean insert(Connection con, PurchaseOrderDetail detail) throws SQLException {
        String sql = "INSERT INTO PurchaseOrderDetail (purchaseOrderId, productDetailId, quantity, importPrice) "
                + "VALUES (?, ?, ?, ?)";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, detail.getPurchaseOrderId().getPurchaseOrderId());
            pst.setString(2, detail.getProductDetailId().getProductDetailId());
            pst.setInt(3, detail.getQuantity());
            pst.setDouble(4, detail.getImportPrice());
            return pst.executeUpdate() == 1;
        }
    }
}
