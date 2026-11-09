package entity;

public class Product {
	private String productId;
	private String productName;
	private double unitPrice;
	private Category category;
	private Supplier supplier;
	
	
	public Product() {
		// TODO Auto-generated constructor stub
	}

	public Product(String productId) {
		super();
		this.productId = productId;
	}

	public Product(String productId, String productName, double unitPrice, Category category, Supplier supplier) {
		setProductId(productId);
		setProductName(productName);
		setUnitPrice(unitPrice);
		setCategory(category);
		setSupplier(supplier);
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", productName=" + productName + ", unitPrice=" + unitPrice
				+ ", category=" + category + ", supplier=" + supplier + "]";
	}
	
	
}
