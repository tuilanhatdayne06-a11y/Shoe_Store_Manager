package entity;

public class InvoiceDetail {
	private String invoiceDetailId;
	private Invoice invoice;
	private ProductDetail productDetail;
	private int quantity;
	private double sellingPrice;
	
	public InvoiceDetail() {
		// TODO Auto-generated constructor stub
	}

	public InvoiceDetail(String invoiceDetailId) {
		setInvoiceDetailId(invoiceDetailId);
	}

	

	public InvoiceDetail(String invoiceDetailId, Invoice invoice, ProductDetail productDetail, int quantity,
			double sellingPrice) {
		this.invoiceDetailId = invoiceDetailId;
		this.invoice = invoice;
		this.productDetail = productDetail;
		this.quantity = quantity;
		this.sellingPrice = sellingPrice;
	}

	public String getInvoiceDetailId() {
		return invoiceDetailId;
	}

	public void setInvoiceDetailId(String invoiceDetailId) {
		this.invoiceDetailId = invoiceDetailId;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
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

	public double getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	@Override
	public String toString() {
		return "InvoiceDetail [invoice=" + invoice + ", productDetail=" + productDetail + ", quantity=" + quantity
				+ ", sellingPrice=" + sellingPrice + ", invoiceDetailId=" + invoiceDetailId + "]";
	}
	
	
	
}
