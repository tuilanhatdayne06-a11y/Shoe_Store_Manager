package entity;

public class PurchaseOrderDetail {
    private PurchaseOrder purchaseOrderId;
    private ProductDetail productDetailId;
    private int quantity;
    private double importPrice;

    public PurchaseOrderDetail() {
    }

    public PurchaseOrderDetail(PurchaseOrder purchaseOrderId, ProductDetail productDetailId,
            int quantity, double importPrice) {
        this.purchaseOrderId = purchaseOrderId;
        this.productDetailId = productDetailId;
        this.quantity = quantity;
        this.importPrice = importPrice;
    }

    public PurchaseOrder getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(PurchaseOrder purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public ProductDetail getProductDetailId() {
        return productDetailId;
    }

    public void setProductDetailId(ProductDetail productDetailId) {
        this.productDetailId = productDetailId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getImportPrice() {
        return importPrice;
    }

    public void setImportPrice(double importPrice) {
        this.importPrice = importPrice;
    }
}
