package gui_Default;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.ArrayList;
import java.nio.file.StandardCopyOption;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import javax.swing.table.DefaultTableModel;

import dao.CustomerDAO;
import entity.Customer;
import utils.AppUtils;
import utils.ImageUtil;

public class CustomerUI extends JPanel implements ActionListener, MouseListener {

    private JTextField txtCustomerCode;
    private JTextField txtCustomerName;
    private JTextField txtCustomerPhone;
    private JTextField txtCustomerAddress;
    private JTextField txtCustomerPoints;
    private JTextField txtFind;
    private JButton btnAddCustomer;
    private JButton btnEditCustomer;
    private JButton btnRemoveCustomer;
    private JButton btnClear;
    private JButton btnFind;
    private JButton btnBrowseImg;
    private JTable table;
    private DefaultTableModel modelTable;
    private JLabel lblCustomerImg;
    private JLabel lblImgName;

    private CustomerDAO customerDAO = new CustomerDAO();
    private File selectedImg = null;

    private Color bgColor = new Color(248, 247, 255);
    private Color primaryColor = new Color(94, 53, 165);
	private ImageUtil imgUtil = new ImageUtil();
	private AppUtils appUtil = new AppUtils();

    public CustomerUI() {
        setLayout(new BorderLayout(10, 10));
        setBackground(bgColor);

        JPanel pNorth = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 12));
        pNorth.setBackground(new Color(234, 230, 250));

        JLabel lblTieuDe = new JLabel("QUẢN LÝ KHÁCH HÀNG");
        lblTieuDe.setFont(new Font("Arial", Font.BOLD, 24));
        lblTieuDe.setForeground(primaryColor);
        pNorth.add(lblTieuDe);
        add(pNorth, BorderLayout.NORTH);

        JPanel pCenter = new JPanel(new BorderLayout(12, 0));
        pCenter.setBackground(bgColor);
        pCenter.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));


        JPanel pDanhSach = new JPanel(new BorderLayout(0, 10));
        pDanhSach.setBackground(bgColor);

        JPanel pTim = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        pTim.setBackground(bgColor);
        txtFind = new JTextField(18);
        btnFind = new JButton("Tìm theo SĐT");
        pTim.add(new JLabel("Số điện thoại:"));
        pTim.add(txtFind);
        pTim.add(btnFind);
        pDanhSach.add(pTim, BorderLayout.NORTH);

        String[] cols = {"Mã KH", "Tên khách hàng", "SĐT", "Địa chỉ", "Điểm tích lũy"};
        modelTable = new DefaultTableModel(cols, 0);
        table = new JTable(modelTable);
        table.setRowHeight(28);
        table.setDefaultEditor(Object.class, null);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setBackground(new Color(230, 216, 255));
        pDanhSach.add(new JScrollPane(table), BorderLayout.CENTER);
        pCenter.add(pDanhSach, BorderLayout.CENTER);

        JPanel pInput = new JPanel(new GridBagLayout());
        pInput.setBackground(new Color(230, 216, 255));
        pInput.setPreferredSize(new Dimension(375, 0));
        pInput.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        txtCustomerCode = new JTextField(15);
        txtCustomerCode.setEditable(false);
        txtCustomerName = new JTextField(15);
        txtCustomerPhone = new JTextField(15);
        txtCustomerAddress = new JTextField(15);
        txtCustomerPoints = new JTextField("0", 15);
        txtCustomerPoints.setEditable(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.35;
        gbc.gridx = 0;
        gbc.gridy = 0;
        pInput.add(new JLabel("Mã khách hàng:"), gbc);
        gbc.gridy = 1;
        pInput.add(new JLabel("Tên khách hàng:"), gbc);
        gbc.gridy = 2;
        pInput.add(new JLabel("Số điện thoại:"), gbc);
        gbc.gridy = 3;
        pInput.add(new JLabel("Địa chỉ:"), gbc);
        gbc.gridy = 4;
        pInput.add(new JLabel("Điểm tích lũy:"), gbc);

        gbc.weightx = 0.65;
        gbc.gridx = 1;
        gbc.gridy = 0;
        pInput.add(txtCustomerCode, gbc);
        gbc.gridy = 1;
        pInput.add(txtCustomerName, gbc);
        gbc.gridy = 2;
        pInput.add(txtCustomerPhone, gbc);
        gbc.gridy = 3;
        pInput.add(txtCustomerAddress, gbc);
        gbc.gridy = 4;
        pInput.add(txtCustomerPoints, gbc);


        JPanel pAnh = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 4));
        pAnh.setOpaque(false);
        lblCustomerImg = new JLabel("Chưa có ảnh", SwingConstants.CENTER);
        lblCustomerImg.setPreferredSize(new Dimension(95, 95));
        lblCustomerImg.setBorder(BorderFactory.createLineBorder(new Color(175, 145, 210)));
        lblImgName = new JLabel("Chưa chọn ảnh");
        btnBrowseImg = new JButton("Chọn ảnh");
        pAnh.add(lblCustomerImg);

        JPanel pChonAnh = new JPanel(new BorderLayout(0, 5));
        pChonAnh.setOpaque(false);
        pChonAnh.add(btnBrowseImg, BorderLayout.NORTH);
        pChonAnh.add(lblImgName, BorderLayout.SOUTH);
        pAnh.add(pChonAnh);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        pInput.add(pAnh, gbc);

        JPanel pButton = new JPanel(new FlowLayout(FlowLayout.CENTER, 7, 5));
        pButton.setOpaque(false);
        btnAddCustomer = new JButton("Thêm");
        btnEditCustomer = new JButton("Sửa");
        btnRemoveCustomer = new JButton("Xóa");
        btnClear = new JButton("Xóa trắng");
        pButton.add(btnAddCustomer);
        pButton.add(btnEditCustomer);
        pButton.add(btnRemoveCustomer);
        pButton.add(btnClear);

        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.NORTH;
        pInput.add(pButton, gbc);
        pCenter.add(pInput, BorderLayout.EAST);
        add(pCenter, BorderLayout.CENTER);

        btnAddCustomer.addActionListener(this);
        btnEditCustomer.addActionListener(this);
        btnRemoveCustomer.addActionListener(this);
        btnClear.addActionListener(this);
        btnFind.addActionListener(this);
        btnBrowseImg.addActionListener(this);
        txtFind.addActionListener(this);
        table.addMouseListener(this);

        loadDataToTable();
    }

    private void loadDataToTable() {
        try {
            ArrayList<Customer> ds = customerDAO.getAllCustomers();
            showCustomerList(ds);
        } catch (SQLException e) {
            showMessage(
                    "Không tải được danh sách khách hàng: " + e.getMessage());
        }
    }

    private void showCustomerList(ArrayList<Customer> ds) {
        modelTable.setRowCount(0);
        for (Customer kh : ds) {
            modelTable.addRow(new Object[] {
            		kh.getCustomerId(),
            		kh.getCustomerName(),
            		kh.getPhoneNumber(),
            		kh.getAddress(),
            		kh.getLoyaltyPoints(),
            		kh.getImgCustomer()
            });
        }
    }

    private boolean validData() {
        String ten = txtCustomerName.getText().trim();
        String sdt = txtCustomerPhone.getText().trim();
        String diaChi = txtCustomerAddress.getText().trim();

        if (ten.isEmpty() || ten.length() > 100) {
            showMessage("Tên khách hàng không hợp lệ!");
            txtCustomerName.requestFocus();
            return false;
        }
        if (!sdt.matches("0[0-9]{9}")) {
            showMessage("Số điện thoại phải gồm 10 số, bắt đầu bằng 0!");
            txtCustomerPhone.requestFocus();
            return false;
        }
        if (diaChi.length() > 200) {
            showMessage("Địa chỉ không được quá 200 ký tự!");
            txtCustomerAddress.requestFocus();
            return false;
        }
        return true;
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    private Customer getCustomerFromForm() {
        return new Customer(
                txtCustomerCode.getText().trim(),
                txtCustomerName.getText().trim(),
                txtCustomerAddress.getText().trim(),
                txtCustomerPhone.getText().trim(),
                0,
                selectedImg.getName()
          );
    }
    

    private void themKhachHang() {
        if (table.getSelectedRow() != -1) {
            showMessage("Hãy xóa trắng trước khi thêm khách hàng!");
            return;
        }
        if (!validData()) return;

        try {
            if (customerDAO.isCustomerPhoneUsed(txtCustomerPhone.getText().trim(), "")) {
                showMessage("Số điện thoại đã được đăng ký!");
                return;
            }

            String customerId = appUtil.autoGenerateId("KH");
            Customer customer = new Customer(
            		customerId,
            		txtCustomerName.getText(),
            		txtCustomerAddress.getText(),
            		txtCustomerPhone.getText(),
            		0,
            		selectedImg.getName()
            );
            
            modelTable.addRow(new Object[] {
            		customer.getCustomerId(),
            		customer.getCustomerName(),
            		customer.getPhoneNumber(),
            		customer.getAddress(),
            		0,
            		customer.getImgCustomer()
            });

            try {
                luuAnhKhachHang();
            } catch (Exception e) {
                showMessage(
                        "Đã thêm khách hàng nhưng không lưu được ảnh: " + e.getMessage());
            }
            
            customerDAO.addCustomer(customer);
            
            clearForm();
            showMessage("Thêm khách hàng thành công!");
        } catch (SQLException e) {
            showMessage("Không thể thêm khách hàng: " + e.getMessage());
        }
    }

    private void suaKhachHang() {
        if (table.getSelectedRow() == -1) {
            showMessage("Chọn khách hàng cần sửa!");
            return;
        }
        if (!validData()) return;

        String ma = txtCustomerCode.getText().trim();
        try {
            if (customerDAO.isCustomerPhoneUsed(txtCustomerPhone.getText().trim(), ma)) {
                showMessage("Số điện thoại đã được đăng ký!");
                return;
            }
            

            if (customerDAO.updateCustomer(getCustomerFromForm())) {
                try {
                    luuAnhKhachHang();
                } catch (Exception e) {
                    showMessage(
                            "Đã sửa khách hàng nhưng không lưu được ảnh: " + e.getMessage());
                }
                loadDataToTable();
                clearForm();
                showMessage("Sửa khách hàng thành công!");
            }
        } catch (SQLException e) {
            showMessage("Không thể sửa khách hàng: " + e.getMessage());
        }
    }

    private void xoaKhachHang() {
        int row = table.getSelectedRow();
        if (row == -1) {
            showMessage("Chọn khách hàng cần xóa!");
            return;
        }
        String ma = txtCustomerCode.getText().trim();
        int chon = JOptionPane.showConfirmDialog(this,
                "Bạn có muốn xóa khách hàng " + ma + "?",
                "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (chon != JOptionPane.YES_OPTION) return;

        try {
            if (customerDAO.removeCustomer(ma)) {
                xoaAnhKhachHang(ma);
                loadDataToTable();
                clearForm();
                showMessage("Xóa khách hàng thành công!");
            }
        } catch (SQLException e) {
            showMessage(
                    "Không thể xóa khách hàng (có thể đã phát sinh hóa đơn): " + e.getMessage());
        }
    }

    private void timKhachHang() {
        String sdt = txtFind.getText().trim();
        if (sdt.isEmpty()) {
        	JOptionPane.showMessageDialog(this, "Số điện thoại chưa điền");
            return;
        }
        try {
            ArrayList<Customer> ds = customerDAO.findCustomersByPhone(sdt);
            showCustomerList(ds);
            if (ds.isEmpty()) {
                showMessage("Không tìm thấy khách hàng!");
            }
        } catch (SQLException e) {
            showMessage("Không tìm được khách hàng: " + e.getMessage());
        }
    }

    private void clearForm() {
        table.clearSelection();
        txtCustomerCode.setText("");
        txtCustomerName.setText("");
        txtCustomerPhone.setText("");
        txtCustomerAddress.setText("");
        txtCustomerPoints.setText("0");
        txtFind.setText("");
        selectedImg = null;
        hienThiAnhKhachHang(null);
        txtCustomerName.requestFocus();
    }

    private void chonAnhKhachHang() {
   
        File file = imgUtil .browseImg();
        
        selectedImg = file;
        hienThiAnhKhachHang(file);
    }


    private void luuAnhKhachHang() {
        if (selectedImg == null) return;
        imgUtil.saveImg(selectedImg);
    }

    private void xoaAnhKhachHang(String ma) {
        String[] duoi = {".jpg", ".jpeg", ".png"};
        for (String ext : duoi) {
            File file = new File("src/img/" + ma + ext);
            if (file.exists()) file.delete();
        }
    }

    private void hienThiAnhKhachHang(File file) {
        lblCustomerImg.setIcon(null);
        lblCustomerImg.setText("Chưa có ảnh");
        lblImgName.setText(file == null ? "Chưa chọn ảnh" : file.getName());
        if (file == null) return;

        ImageIcon icon = new ImageIcon(file.getPath());
        if (icon.getIconWidth() <= 0) return;
        Image anh = icon.getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH);
        lblCustomerImg.setIcon(new ImageIcon(anh));
        lblCustomerImg.setText("");
    }

    private void fillFormFromTable() {
        int row = table.getSelectedRow();
        if (row == -1) return;
        txtCustomerCode.setText(modelTable.getValueAt(row, 0).toString());
        txtCustomerName.setText(modelTable.getValueAt(row, 1).toString());
        txtCustomerPhone.setText(modelTable.getValueAt(row, 2).toString());
        Object diaChi = modelTable.getValueAt(row, 3);
        txtCustomerAddress.setText(diaChi == null ? "" : diaChi.toString());
        txtCustomerPoints.setText(modelTable.getValueAt(row, 4).toString());
        selectedImg = null;
        String customerImg = customerDAO.findById(modelTable.getValueAt(row, 0).toString()).getImgCustomer();
        hienThiAnhKhachHang(new File("src/img/" + customerImg));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        fillFormFromTable();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnAddCustomer)) {
            themKhachHang();
        } else if (o.equals(btnEditCustomer)) {
            suaKhachHang();
        } else if (o.equals(btnRemoveCustomer)) {
            xoaKhachHang();
        } else if (o.equals(btnClear)) {
            clearForm();
            loadDataToTable();
        } else if (o.equals(btnFind) || o.equals(txtFind)) {
            timKhachHang();
        } else if (o.equals(btnBrowseImg)) {
            chonAnhKhachHang();
        }
    }

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}

}
