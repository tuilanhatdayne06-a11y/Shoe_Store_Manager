package entity;

public class Category {
	private String categoryId;
	private String categoryName;
	
	public Category() {
		// TODO Auto-generated constructor stub
	}

	public Category(String categoryId) {
		setCategoryId(categoryId);
	}

	public Category(String categoryId, String categoryName) {
		setCategoryId(categoryId);
		setCategoryName(categoryName);
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	

	@Override
	public String toString() {
		return "Category [categoryId=" + categoryId + ", categoryName=" + categoryName + "]";
	}
	
	
}
