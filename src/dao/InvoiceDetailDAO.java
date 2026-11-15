package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import connectDB.ConnectDB;
import entity.InvoiceDetail;

public class InvoiceDetailDAO {
    public void insertInvoiceDetail(InvoiceDetail invoiceDetail)
    {
        String sql = """
                INSERT INTO InvoiceDetail (invoiceId, productDetailId, quantity, sellingPrice, invoiceDetailId)
                VALUES (?, ?, ?, ?, ?)
            """;

        try {
            Connection con = ConnectDB.getConnection();
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, invoiceDetail.getInvoice().getInvoiceId());
            pst.setString(2, invoiceDetail.getProductDetail().getProductDetailId());
            pst.setInt(3, invoiceDetail.getQuantity());
            pst.setDouble(4, invoiceDetail.getSellingPrice());
            pst.setString(5, invoiceDetail.getInvoiceDetailId());

            pst.executeUpdate();

        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
