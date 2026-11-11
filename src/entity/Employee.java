package entity;

public class Employee {

	private String employeeId;
    private String employeeName;
    private boolean isGender;
    private String position;
    private String phoneNumber;
    
<<<<<<< HEAD
    public Employee(String id)
    {
    	this.EmployeeId = id;
=======
    public Employee(String employeeID) {
    		this.employeeId = employeeID;
>>>>>>> 41578e46e439ea9d03dc499b971a5e3051700e73
    }
    
    public Employee (String employeeID, String employeeName, boolean isGender, String phoneNumber, String position) {
    		this.employeeId = employeeID;	
        this.employeeName = employeeName;
        this.isGender = isGender;
        this.phoneNumber = phoneNumber;
        this.position = position;
    }
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeID(String employeeID) {
		this.employeeId = employeeID;
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
		return "NhanVien [EmployeeId=" + employeeId + ", EmployeeName=" + employeeName + ", isGender=" + isGender
				+ ", position=" + position + ", phoneNumber=" + phoneNumber + "]";
	}

}
