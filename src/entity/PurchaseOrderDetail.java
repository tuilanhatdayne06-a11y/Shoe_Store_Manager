package entity;

public class PurchaseOrderDetail {
    private PurchaseOrder purchaseOrder;
    private ProductDetail productDetail;
    private int quantity;
    private double importPrice;

    public PurchaseOrderDetail() {
    }

    public PurchaseOrderDetail(PurchaseOrder purchaseOrder, ProductDetail productDetail,
            int quantity, double importPrice) {
        this.purchaseOrder = purchaseOrder;
        this.productDetail = productDetail;
        this.quantity = quantity;
        this.importPrice = importPrice;
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public ProductDetail getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(ProductDetail productDetail) {
        this.productDetail = productDetail;
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
