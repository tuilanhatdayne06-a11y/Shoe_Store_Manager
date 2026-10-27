package HSK.gui_Default.entity;

public class NhanVien {
	private String employeeId;
	private String employeeName;
	private boolean gender;
	private String phoneNumber;
	private String position;
	private String pass;
	
	public NhanVien() {
		
	}

	public NhanVien(String employeeId, String employeeName, boolean gender, String phoneNumber, String position,
			String pass) {
		setEmployeeId(employeeId);
		setEmployeeName(employeeName);
		setGender(gender);
		setPhoneNumber(phoneNumber);
		setPosition(position);
		setPass(pass);
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
		return gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
}