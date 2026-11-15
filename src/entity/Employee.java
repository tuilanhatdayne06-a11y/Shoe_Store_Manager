package entity;

public class Employee {
	private String employeeId;
    private String employeeName;
    private boolean isGender;
    private String email;
    private String position;
    private String phoneNumber;
    
    public Employee() {
	}

	public Employee(String id)
    {
    	this.employeeId = id;
    }
    
    public Employee (String employeeID, String employeeName, boolean isGender, String email,  String phoneNumber, String position) {
		setEmployeeId(employeeID);
		setEmployeeName(employeeName);
		setGender(isGender);
		setEmail(email);
		setPhoneNumber(phoneNumber);
		setPosition(position);
    }
	
	

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public boolean isGender() {
		return isGender;
	}

	public void setGender(boolean isGender) {
		this.isGender = isGender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return "NhanVien [EmployeeId=" + employeeId + ", EmployeeName=" + employeeName + ", isGender=" + isGender + email + ", email"
				+ ", position=" + position + ", phoneNumber=" + phoneNumber + "]";
	}

}
