package HSK.gui_Default.entity;

public class NhaCungCap {
    private String supplierId;
    private String supplierName;
    private String address;
    private String phoneNumber;
    private String email;
    private String logo;

    public NhaCungCap() {
    }

    public NhaCungCap(String supplierId, String supplierName, String address,
                      String phoneNumber, String email, String logo) {
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.logo = logo;
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

    public Object[] toRow() {
        return new Object[]{supplierId, supplierName, address, phoneNumber, email, logo};
    }
}
