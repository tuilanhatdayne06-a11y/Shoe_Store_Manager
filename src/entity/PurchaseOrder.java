package entity;

import java.sql.Timestamp;


public class PurchaseOrder {
    private String purchaseOrderId;
    private Timestamp orderDate;
    private Employee employee;
    private Supplier supplier;
    private String status;

    public PurchaseOrder() {
    }

    public PurchaseOrder(String purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public PurchaseOrder(String purchaseOrderId, Timestamp orderDate, Employee employee, Supplier supplier,
            String status) {
        this.purchaseOrderId = purchaseOrderId;
        this.orderDate = orderDate;
        this.employee = employee;
        this.supplier = supplier;
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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "PurchaseOrder [purchaseOrderId=" + purchaseOrderId + ", orderDate=" + orderDate + ", employee="
                + employee + ", supplier=" + supplier + ", status=" + status + "]";
    }

    
}
