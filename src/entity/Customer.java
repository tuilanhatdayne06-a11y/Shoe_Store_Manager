
package entity;

public class Customer {
    private String customerId;
    private String customerName;
    private String address;
    private String phoneNumber;
    private int loyaltyPoints;
    private String imgCustomer;

    
    public Customer() {
		// TODO Auto-generated constructor stub
	}


	public Customer(String customerId) {
		super();
		this.customerId = customerId;
	}
	
	


	public Customer(String customerId, String customerName, String address, String phoneNumber, int loyaltyPoints,
			String imgCustomer) {
		setCustomerId(customerId);
		setCustomerName(customerName);
		setAddress(address);
		setPhoneNumber(phoneNumber);
		setLoyaltyPoints(loyaltyPoints);
		setImgCustomer(imgCustomer);
	}


	public String getCustomerId() {
		return customerId;
	}


	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}


	public String getCustomerName() {
		return customerName;
	}


	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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


	public int getLoyaltyPoints() {
		return loyaltyPoints;
	}


	public void setLoyaltyPoints(int loyaltyPoints) {
		this.loyaltyPoints = loyaltyPoints;
	}


	public String getImgCustomer() {
		return imgCustomer;
	}


	public void setImgCustomer(String imgCustomer) {
		this.imgCustomer = imgCustomer;
	}


	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", customerName=" + customerName + ", address=" + address
				+ ", phoneNumber=" + phoneNumber + ", loyaltyPoints=" + loyaltyPoints + ", imgCustomer=" + imgCustomer
				+ "]";
	}
}
