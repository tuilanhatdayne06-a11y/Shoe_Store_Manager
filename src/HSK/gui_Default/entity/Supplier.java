package HSK.gui_Default.entity;

public class Supplier {
	private String supplierId;
	private String supplierName;
	private String address;
	private String phoneNumber;
	private String email;
	private String logo;
	
	public Supplier() {
		// TODO Auto-generated constructor stub
	}

	public Supplier(String supplierId) {
		this.supplierId = supplierId;
	}

	public Supplier(String supplierId, String supplierName, String address, String phoneNumber, String email,
			String logo) {
		setSupplierId(supplierId);
		setSupplierName(supplierName);
		setAddress(address);
		setPhoneNumber(phoneNumber);
		setEmail(email);
		setLogo(logo);
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	@Override
	public String toString() {
		return "Supplier [supplierId=" + supplierId + ", supplierName=" + supplierName + ", address=" + address
				+ ", phoneNumber=" + phoneNumber + ", email=" + email + ", logo=" + logo + "]";
	}
	
	
}
