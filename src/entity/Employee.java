package entity;

public class Employee {

	private String EmployeeId;
    private String EmployeeName;
    private boolean isGender;
    private String position;
    private String phoneNumber;
    
    public Employee(String id)
    {
    	this.EmployeeId = id;
    }
    
    public Employee (String employeeID, String employeeName, boolean isGender, String phoneNumber, String position) {
        this.EmployeeId = employeeID;
        this.EmployeeName = employeeName;
        this.isGender = isGender;
        this.phoneNumber = phoneNumber;
        this.position = position;
    }
	public String getEmployeeId() {
		return EmployeeId;
	}
	public void setEmployeeID(String employeeID) {
		EmployeeId = employeeID;
	}
	public String getEmployeeName() {
		return EmployeeName;
	}
	public void setEmployeeName(String employeeName) {
		EmployeeName = employeeName;
	}
	public boolean isGender() {
		return isGender;
	}
	public void setGender(boolean isGender) {
		this.isGender = isGender;
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
		return "NhanVien [EmployeeId=" + EmployeeId + ", EmployeeName=" + EmployeeName + ", isGender=" + isGender
				+ ", position=" + position + ", phoneNumber=" + phoneNumber + "]";
	}

}
