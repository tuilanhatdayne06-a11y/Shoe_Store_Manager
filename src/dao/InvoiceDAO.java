package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;

import connectDB.ConnectDB;
import entity.Invoice;

public class InvoiceDAO {
    public void createInvoice(Invoice invoice)
    {
        String sql = """
            INSERT INTO Invoice
            (invoiceId, creationDate, totalAmount, employeeId, customerId)
            VALUES
                (?, ?, ?, ?, ?);    
        """;

        try {
            Connection con = ConnectDB.getConnection();
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, invoice.getInvoiceId());
            pst.setTimestamp(2, invoice.getCreationDate());
            pst.setDouble(3, invoice.getTotalAmount());
            pst.setString(4, invoice.getEmployee().getEmployeeId());
            pst.setString(5, invoice.getCustomer().getCustomerId());

            pst.executeUpdate();

        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
