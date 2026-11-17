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
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import dao.CustomerDAO;
import entity.Customer;
import utils.AppUtils;

public class CustomerUI extends JPanel implements ActionListener, MouseListener {

    private JTextField txtCustomerCode;
    private JTextField txtCustomerName;
    private JTextField txtCustomerPhone;
    private JTextField txtCustomerAddress;
    private JTextField txtCustomerPoints;
    private JTextField txtFind;
    private JRadioButton radNam;
    private JRadioButton radNu;
    private ButtonGroup groupGender;
    private JButton btnAddCustomer;
    private JButton btnEditCustomer;
    private JButton btnRemoveCustomer;
    private JButton btnClear;
    private JButton btnFind;
    private JButton btnRefresh;
    private JTable table;
    private DefaultTableModel modelTable;
    private JLabel lblCustomerImg;

    private CustomerDAO customerDAO = new CustomerDAO();

    private Color bgColor = new Color(248, 247, 255);
    private Color primaryColor = new Color(94, 53, 165);
    private AppUtils appUtil = new AppUtils();

    public CustomerUI() {
        setLayout(new BorderLayout(10, 10));
        setBackground(bgColor);

        JPanel pNorth = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 12));
        pNorth.setBackground(new Color(234, 230, 250));

        JLabel lblTieuDe = new JLabel("QUẢN LÝ KHÁCH HÀNG");
        lblTieuDe.setFont(new Font("Arial", Font.BOLD, 24));
        lblTieuDe.setForeground(primaryColor);
        java.net.URL iconURL = getClass().getResource("/img/customer.png");
        if (iconURL != null) {
            ImageIcon icon = new ImageIcon(iconURL);
            Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            lblTieuDe.setIcon(new ImageIcon(img));
        }
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

        String[] cols = {"Mã KH", "Tên khách hàng", "SĐT", "Địa chỉ", "Điểm tích lũy", "Giới tính", "Ảnh"};
        modelTable = new DefaultTableModel(cols, 0) {
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 6) return ImageIcon.class;
                return Object.class;
            }
        };
        table = new JTable(modelTable);
        table.setRowHeight(52);
        table.setDefaultEditor(Object.class, null);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setBackground(new Color(230, 216, 255));
        table.getColumnModel().getColumn(6).setPreferredWidth(60);
        pDanhSach.add(new JScrollPane(table), BorderLayout.CENTER);
        pCenter.add(pDanhSach, BorderLayout.CENTER);

        JPanel pInput = new JPanel(new GridBagLayout());
        pInput.setBackground(new Color(230, 216, 255));
        pInput.setPreferredSize(new Dimension(375, 0));
        pInput.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        txtCustomerCode = new JTextField("KH001", 15);
        txtCustomerCode.setEditable(false);
        txtCustomerName = new JTextField(15);
        txtCustomerPhone = new JTextField(15);
        txtCustomerAddress = new JTextField(15);
        radNam = new JRadioButton("Nam");
        radNu = new JRadioButton("Nữ");
        radNam.setOpaque(false);
        radNu.setOpaque(false);
        groupGender = new ButtonGroup();
        groupGender.add(radNam);
        groupGender.add(radNu);
        JPanel pGender = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        pGender.setOpaque(false);
        pGender.add(radNam);
        pGender.add(radNu);
        txtCustomerPoints = new JTextField("0", 15);
        txtCustomerPoints.setEditable(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.weightx = 0.35;
        gbc.gridx = 0; gbc.gridy = 0; pInput.add(new JLabel("Mã khách hàng:"), gbc);
        gbc.gridy = 1; pInput.add(new JLabel("Tên khách hàng:"), gbc);
        gbc.gridy = 2; pInput.add(new JLabel("Số điện thoại:"), gbc);
        gbc.gridy = 3; pInput.add(new JLabel("Địa chỉ:"), gbc);
        gbc.gridy = 4; pInput.add(new JLabel("Giới tính:"), gbc);
        gbc.gridy = 5; pInput.add(new JLabel("Điểm tích lũy:"), gbc);

        gbc.weightx = 0.65;
        gbc.gridx = 1; gbc.gridy = 0; pInput.add(txtCustomerCode, gbc);
        gbc.gridy = 1; pInput.add(txtCustomerName, gbc);
        gbc.gridy = 2; pInput.add(txtCustomerPhone, gbc);
        gbc.gridy = 3; pInput.add(txtCustomerAddress, gbc);
        gbc.gridy = 4; pInput.add(pGender, gbc);
        gbc.gridy = 5; pInput.add(txtCustomerPoints, gbc);

        JPanel pAnh = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 4));
        pAnh.setOpaque(false);
        lblCustomerImg = new JLabel("Chưa có ảnh", SwingConstants.CENTER);
        lblCustomerImg.setPreferredSize(new Dimension(95, 95));
        lblCustomerImg.setBorder(BorderFactory.createLineBorder(new Color(175, 145, 210)));
        pAnh.add(lblCustomerImg);

        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2; gbc.weightx = 1;
        pInput.add(pAnh, gbc);

        JPanel pButton = new JPanel(new FlowLayout(FlowLayout.CENTER, 7, 5));
        pButton.setOpaque(false);
        btnAddCustomer = new JButton("Thêm");
        btnEditCustomer = new JButton("Sửa");
        btnRemoveCustomer = new JButton("Xóa");
        btnClear = new JButton("Xóa trắng");
        btnRefresh = new JButton("Làm mới");
        pButton.add(btnAddCustomer);
        pButton.add(btnEditCustomer);
        pButton.add(btnRemoveCustomer);
        pButton.add(btnClear);
        pButton.add(btnRefresh);

        gbc.gridy = 7; gbc.anchor = GridBagConstraints.NORTH;
        pInput.add(pButton, gbc);
        pCenter.add(pInput, BorderLayout.EAST);
        add(pCenter, BorderLayout.CENTER);

        btnAddCustomer.addActionListener(this);
        btnEditCustomer.addActionListener(this);
        btnRemoveCustomer.addActionListener(this);
        btnClear.addActionListener(this);
        btnFind.addActionListener(this);
        btnRefresh.addActionListener(this);
        radNam.addActionListener(this);
        radNu.addActionListener(this);
        txtFind.addActionListener(this);
        table.addMouseListener(this);

        hienThiAnhKhachHang();
        loadDataToTable();
    }

    private void loadDataToTable() {
        try {
            ArrayList<Customer> ds = customerDAO.getAllCustomers();
            showCustomerList(ds);
        } catch (SQLException e) {
            showMessage("Không tải được danh sách khách hàng: " + e.getMessage());
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
                kh.isGender() ? "Nam" : "Nữ",
                getCustomerAvatar(kh.isGender(), 44)
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

        if(sdt.isBlank())
        {
            JOptionPane.showMessageDialog(this, "Số điện thoại không được trống");
            txtCustomerPhone.requestFocus();
            return false;
        }

        if(diaChi.isBlank())
        {
            JOptionPane.showMessageDialog(this, "Địa chỉ không được trống");
            txtCustomerAddress.requestFocus();
            return false;
        }
        if (!sdt.matches("0[0-9]{9}")) {
            showMessage("Số điện thoại phải gồm 10 số, bắt đầu bằng 0!");
            txtCustomerPhone.requestFocus();
            return false;
        }
        if (diaChi.length() > 255) {
            showMessage("Địa chỉ không được quá 255 ký tự!");
            txtCustomerAddress.requestFocus();
            return false;
        }
        if (!radNam.isSelected() && !radNu.isSelected()) {
            showMessage("Vui lòng chọn giới tính khách hàng!");
            return false;
        }
        return true;
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    private Customer getCustomerFromForm(String id) {
        int points = 0;
        try {
            points = Integer.parseInt(txtCustomerPoints.getText().trim());
        } catch (Exception e) {}
        
        return new Customer(
                id,
                txtCustomerName.getText().trim(),
                txtCustomerAddress.getText().trim(),
                txtCustomerPhone.getText().trim(),
                points,
                radNam.isSelected());
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
            if (customerId == null) {
                showMessage("Không thể sinh mã khách hàng!");
                return;
            }
            
            Customer customer = getCustomerFromForm(customerId);

            if (customerDAO.addCustomer(customer)) {
                loadDataToTable();
                clearForm();
                showMessage("Thêm khách hàng thành công! Mã: " + customerId);
            }
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

            Customer customer = getCustomerFromForm(ma);
            if (customerDAO.updateCustomer(customer)) {
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
        int chon = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa khách hàng " + ma + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (chon != JOptionPane.YES_OPTION) return;

        try {
            if (customerDAO.removeCustomer(ma)) {
                loadDataToTable();
                clearForm();
                showMessage("Xóa khách hàng thành công!");
            }
        } catch (SQLException e) {
            showMessage("Không thể xóa khách hàng (có thể đã phát sinh hóa đơn): " + e.getMessage());
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
        txtCustomerCode.setText("Tự sinh khi thêm");
        txtCustomerName.setText("");
        txtCustomerPhone.setText("");
        txtCustomerAddress.setText("");
        txtCustomerPoints.setText("0");
        groupGender.clearSelection();
        txtFind.setText("");
        lblCustomerImg.setToolTipText(null);
        hienThiAnhKhachHang();
        txtCustomerName.requestFocus();
    }

    private void fillFormFromTable() {
        int row = table.getSelectedRow();
        if (row == -1) return;

        row = table.convertRowIndexToModel(row);
        String ma = modelTable.getValueAt(row, 0).toString();

        try {
            Customer kh = customerDAO.findById(ma);
            if (kh == null) return;

            txtCustomerCode.setText(kh.getCustomerId());
            txtCustomerName.setText(kh.getCustomerName());
            txtCustomerPhone.setText(kh.getPhoneNumber());
            txtCustomerAddress.setText(kh.getAddress() == null ? "" : kh.getAddress());
            txtCustomerPoints.setText(String.valueOf(kh.getLoyaltyPoints()));
            
            if (kh.isGender()) {
                radNam.setSelected(true);
            } else {
                radNu.setSelected(true);
            }

            hienThiAnhKhachHang(); 
            lblCustomerImg.setToolTipText("<html><b>Họ tên:</b> " + kh.getCustomerName()
                    + "<br><b>Giới tính:</b> " + (kh.isGender() ? "Nam" : "Nữ")
                    + "<br><b>SĐT:</b> " + kh.getPhoneNumber() + "</html>");
        } catch (Exception e) {
            showMessage("Không lấy được thông tin khách hàng: " + e.getMessage());
        }
    }

    private ImageIcon getCustomerAvatar(boolean isGender, int size) {
        ImageIcon icon = null;
        String path = isGender ? "/img/employee_male.png" : "/img/employee_female.png";
        java.net.URL url = getClass().getResource(path);
        if (url != null) icon = new ImageIcon(url);

        if (icon == null || icon.getIconWidth() <= 0) return null;
        Image img = icon.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    private void hienThiAnhKhachHang() {
        ImageIcon icon = null;
        String ma = txtCustomerCode.getText().trim();
        boolean gender = radNam.isSelected();
        
        if (!radNam.isSelected() && !radNu.isSelected() && (ma.isEmpty() || ma.equals("Tự sinh khi thêm"))) {
            java.net.URL url = getClass().getResource("/img/customer.png");
            if (url != null) {
                Image img = new ImageIcon(url).getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH);
                icon = new ImageIcon(img);
            }
        } else {
            icon = getCustomerAvatar(gender, 90);
        }

        lblCustomerImg.setIcon(icon);
        lblCustomerImg.setText(icon == null ? "Chưa có ảnh" : "");
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == table) fillFormFromTable();
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
        } else if (o.equals(btnFind) || o.equals(txtFind)) {
            timKhachHang();
        } else if (o.equals(radNam) || o.equals(radNu)) {
            hienThiAnhKhachHang();
        }
        else if(o.equals(btnRefresh)){
            loadDataToTable();
        }
    }

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}