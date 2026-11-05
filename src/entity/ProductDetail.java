package entity;

public class ProductDetail {
	private String productDetailId;
	private int size;
	private String color;
	private String stockQuantity;
	private Product product;
	
	
	public ProductDetail() {
		
	}


	public ProductDetail(String productDetailId) {
		super();
		this.productDetailId = productDetailId;
	}


	public ProductDetail(String productDetailId, int size, String color, String stockQuantity, Product product) {
		setProductDetailId(productDetailId);
		setSize(size);
		setColor(color);
		setStockQuantity(stockQuantity);
		setProduct(product);
	}


	public String getProductDetailId() {
		return productDetailId;
	}


	public void setProductDetailId(String productDetailId) {
		this.productDetailId = productDetailId;
	}


	public int getSize() {
		return size;
	}


	public void setSize(int size) {
		this.size = size;
	}


	public String getColor() {
		return color;
	}


	public void setColor(String color) {
		this.color = color;
	}


	public String getStockQuantity() {
		return stockQuantity;
	}


	public void setStockQuantity(String stockQuantity) {
		this.stockQuantity = stockQuantity;
	}


	public Product getProduct() {
		return product;
	}


	public void setProduct(Product product) {
		this.product = product;
	}


	@Override
	public String toString() {
		return "ProductDetail [productDetailId=" + productDetailId + ", size=" + size + ", color=" + color
				+ ", stockQuantity=" + stockQuantity + ", product=" + product + "]";
	}
	
	
	
}
