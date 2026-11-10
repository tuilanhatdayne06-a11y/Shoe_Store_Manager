package entity;

public class ProductDetail {
	private String productDetailId;
	private int size;
	private String color;
	private int stockQuantity;
	private Product product;
	private String imgProductColor;
	
	
	public ProductDetail() {
		
	}

	public ProductDetail(String productDetailId) {
		super();
		this.productDetailId = productDetailId;
	}


	public ProductDetail(String productDetailId, int size, String color, int stockQuantity, Product product,
			String imgProductColor) {

		setProductDetailId(productDetailId);
		setSize(size);
		setColor(color);
		setStockQuantity(stockQuantity);
		setProduct(product);
		setImgProductColor(imgProductColor);

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


	public int getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(int stockQuantity) {

		this.stockQuantity = stockQuantity;
	}


	public Product getProduct() {
		return product;
	}


	public void setProduct(Product product) {
		this.product = product;
	}
	public String getImgProductColor() {
		return imgProductColor;
	}

	public void setImgProductColor(String imgProductColor) {
		this.imgProductColor = imgProductColor;
	}


	@Override
	public String toString() {
		return "ProductDetail [productDetailId=" + productDetailId + ", size=" + size + ", color=" + color
				+ ", stockQuantity=" + stockQuantity + ", product=" + product + ", imgPathColor=" + imgProductColor + "]";
	}
}
