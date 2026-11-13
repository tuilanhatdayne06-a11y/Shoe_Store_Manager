package entity;

public class InvoiceDetail {
	private Invoice invoice;
	private ProductDetail productDetail;
	private int quantity;
	private double sellingPrice;
	private String invoiceDetailId;
	
	public InvoiceDetail() {
		// TODO Auto-generated constructor stub
	}

	public InvoiceDetail(String invoiceDetailId) {
		setInvoiceDetailId(invoiceDetailId);
	}

	public InvoiceDetail(Invoice invoice, ProductDetail productDetail, int quantity, double sellingPrice,
			String invoiceDetailId) {
		setInvoice(invoice);
		setProductDetail(productDetail);
		setQuantity(quantity);
		setSellingPrice(sellingPrice);
		setInvoiceDetailId(invoiceDetailId);
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

	public String getInvoiceDetailId() {
		return invoiceDetailId;
	}

	public void setInvoiceDetailId(String invoiceDetailId) {
		this.invoiceDetailId = invoiceDetailId;
	}

	@Override
	public String toString() {
		return "InvoiceDetail [invoice=" + invoice + ", productDetail=" + productDetail + ", quantity=" + quantity
				+ ", sellingPrice=" + sellingPrice + ", invoiceDetailId=" + invoiceDetailId + "]";
	}
	
	
	
}
