package entity;

import java.sql.Timestamp;


public class PurchaseOrder {
    private String purchaseOrderId;
    private Timestamp orderDate;
    private Employee employeeId;
    private Supplier supplierId;

    public PurchaseOrder() {
    }

    public PurchaseOrder(String purchaseOrderId, Timestamp orderDate,
            Employee employeeId, Supplier supplierId) {
        this.purchaseOrderId = purchaseOrderId;
        this.orderDate = orderDate;
        this.employeeId = employeeId;
        this.supplierId = supplierId;
    }

    public PurchaseOrder(String purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public String getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(String purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public Employee getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Employee employeeId) {
        this.employeeId = employeeId;
    }

    public Supplier getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Supplier supplierId) {
        this.supplierId = supplierId;
    }
}
