package entity;

import java.security.Timestamp;

public class Invoice {
	private String invoiceId;
	private Timestamp creationDate;
	private double totalAmount;
	private Employee employee;
	private Customer customer;
	
	
	public Invoice() {
		
	}

	
	public Invoice(String invoiceId) {
		setInvoiceId(invoiceId);
	}
	
	

	public Invoice(String invoiceId, Timestamp creationDate, double totalAmount, Employee employee, Customer customer) {
		setInvoiceId(invoiceId);
		setCreationDate(creationDate);
		setTotalAmount(totalAmount);
		setEmployee(employee);
		setCustomer(customer);
	}

	

	public String getInvoiceId() {
		return invoiceId;
	}


	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}


	public Timestamp getCreationDate() {
		return creationDate;
	}


	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}


	public double getTotalAmount() {
		return totalAmount;
	}


	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}


	public Employee getEmployee() {
		return employee;
	}


	public void setEmployee(Employee employee) {
		this.employee = employee;
	}


	public Customer getCustomer() {
		return customer;
	}


	public void setCustomer(Customer customer) {
		this.customer = customer;
	}


	@Override
	public String toString() {
		return "Invoice [invoiceId=" + invoiceId + ", creationDate=" + creationDate + ", totalAmount=" + totalAmount
				+ ", employee=" + employee + ", customer=" + customer + "]";
	}
	
	
}
