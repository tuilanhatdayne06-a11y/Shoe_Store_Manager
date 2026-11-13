package entity;

import java.sql.Timestamp;


public class PurchaseOrder {
    private String purchaseOrderId;
    private Timestamp orderDate;
    private Employee employeeId;
    private Supplier supplierId;
    private String status;

    public PurchaseOrder() {
    }

    public PurchaseOrder(String purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public PurchaseOrder(String purchaseOrderId, Timestamp orderDate, Employee employeeId, Supplier supplierId,
            String status) {
        this.purchaseOrderId = purchaseOrderId;
        this.orderDate = orderDate;
        this.employeeId = employeeId;
        this.supplierId = supplierId;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "PurchaseOrder [purchaseOrderId=" + purchaseOrderId + ", orderDate=" + orderDate + ", employeeId="
                + employeeId + ", supplierId=" + supplierId + ", status=" + status + "]";
    }

    
}
