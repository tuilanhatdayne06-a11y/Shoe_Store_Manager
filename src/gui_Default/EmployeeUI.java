package gui_Default;

import dao.EmployeeDao;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class EmployeeUI extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;
    
    private JTextField txtMaNV, txtHoTen, txtSDT, txtEmail, txtTimKiem;
    private JRadioButton radNam, radNu;
    private JComboBox<String> cmbChucVu;
    private JButton btnThem, btnXoa, btnSua, btnXoaTrang, btnTimKiem;
    private JTable tblNhanVien;
    private DefaultTableModel modelNhanVien;

    private Color primaryColor = new Color(180, 60, 200);
    private Color bgLight = new Color(253, 240, 255);

    public EmployeeUI() {
        setLayout(new BorderLayout(10, 15));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

       
        JPanel pnlTop = new JPanel(new BorderLayout(0, 15));
        pnlTop.setBackground(Color.WHITE);

        // 1. Tiêu đề
        JLabel lblTitle = new JLabel("QUẢN LÝ NHÂN VIÊN", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(primaryColor);
        pnlTop.add(lblTitle, BorderLayout.NORTH);

        // 2. Form Nhập Liệu
        JPanel pnlForm = new JPanel(new GridLayout(3, 4, 15, 15));
        pnlForm.setBackground(bgLight);
        TitledBorder formBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(primaryColor), "Thông tin nhân viên");
        formBorder.setTitleColor(primaryColor);
        formBorder.setTitleFont(new Font("Arial", Font.BOLD, 14));
        pnlForm.setBorder(BorderFactory.createCompoundBorder(formBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        pnlForm.add(new JLabel("Mã Nhân Viên:"));
        txtMaNV = new JTextField();
        pnlForm.add(txtMaNV);

        pnlForm.add(new JLabel("Họ Tên:"));
        txtHoTen = new JTextField();
        pnlForm.add(txtHoTen);

        pnlForm.add(new JLabel("Số Điện Thoại:"));
        txtSDT = new JTextField();
        pnlForm.add(txtSDT);

        pnlForm.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        pnlForm.add(txtEmail);

        pnlForm.add(new JLabel("Giới Tính:"));
        JPanel pnlGender = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        pnlGender.setOpaque(false);
        radNam = new JRadioButton("Nam", true);
        radNam.setOpaque(false);
        radNu = new JRadioButton("Nữ");
        radNu.setOpaque(false);
        ButtonGroup bgGender = new ButtonGroup();
        bgGender.add(radNam);
        bgGender.add(radNu);
        pnlGender.add(radNam);
        pnlGender.add(radNu);
        pnlForm.add(pnlGender);

        pnlForm.add(new JLabel("Chức Vụ:"));
        String[] chucVu = {"Nhân viên bán hàng", "Quản lý cửa hàng", "Nhân viên kho", "Kế toán"};
        cmbChucVu = new JComboBox<>(chucVu);
        cmbChucVu.setBackground(Color.WHITE);
        pnlForm.add(cmbChucVu);

        pnlTop.add(pnlForm, BorderLayout.CENTER);

        // 3. Các nút chức năng & Tìm kiếm
        JPanel pnlAction = new JPanel(new BorderLayout());
        pnlAction.setBackground(Color.WHITE);

        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        pnlButtons.setBackground(Color.WHITE);
        
        btnThem = createButton("Thêm");
        btnXoa = createButton("Xóa");
        btnSua = createButton("Sửa");
        btnXoaTrang = createButton("Xóa trắng");

        pnlButtons.add(btnThem);
        pnlButtons.add(btnXoa);
        pnlButtons.add(btnSua);
        pnlButtons.add(btnXoaTrang);

        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        pnlSearch.setBackground(Color.WHITE);
        pnlSearch.add(new JLabel("Tìm kiếm:"));
        txtTimKiem = new JTextField(15);
        txtTimKiem.setPreferredSize(new Dimension(150, 28));
        btnTimKiem = createButton("Tìm");
        pnlSearch.add(txtTimKiem);
        pnlSearch.add(btnTimKiem);

        pnlAction.add(pnlButtons, BorderLayout.WEST);
        pnlAction.add(pnlSearch, BorderLayout.EAST);

        pnlTop.add(pnlAction, BorderLayout.SOUTH);
        

        add(pnlTop, BorderLayout.NORTH);


        String[] columns = {"Mã NV", "Họ Tên", "Giới Tính", "Số Điện Thoại", "Chức Vụ"};
        modelNhanVien = new DefaultTableModel(columns, 0);
        tblNhanVien = new JTable(modelNhanVien);
        tblNhanVien.setRowHeight(28); 
        tblNhanVien.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tblNhanVien.getTableHeader().setBackground(primaryColor);
        tblNhanVien.getTableHeader().setForeground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(tblNhanVien);
        TitledBorder tableBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(primaryColor), "Danh sách nhân viên");
        tableBorder.setTitleColor(primaryColor);
        tableBorder.setTitleFont(new Font("Arial", Font.BOLD, 14));
        scrollPane.setBorder(tableBorder);


        add(scrollPane, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.CENTER);
        loadDataToTable();
    }
    private void loadDataToTable() {
        modelNhanVien.setRowCount(0);
        EmployeeDao dao = new EmployeeDao();
        ArrayList<Object[]> list = dao.getAllEmployee();
        for (Object[] row : list) {
            modelNhanVien.addRow(row);
        }
    }
    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setBackground(primaryColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setOpaque(true); 
        btn.setBorderPainted(false); 
        btn.setPreferredSize(new Dimension(100, 32)); 
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addActionListener(this);
        return btn;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();

        if (o == btnThem) {
            String ma = txtMaNV.getText();
            String ten = txtHoTen.getText();
            String sdt = txtSDT.getText();
            String email = txtEmail.getText();
            String gioiTinh = radNam.isSelected() ? "Nam" : "Nữ";
            String chucVu = cmbChucVu.getSelectedItem().toString();

            if (ma.isEmpty() || ten.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đủ thông tin (Mã, Tên)!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            modelNhanVien.addRow(new Object[]{ma, ten, gioiTinh, sdt, email, chucVu});
            btnXoaTrang.doClick(); 
        } 
        else if (o == btnXoaTrang) {
            txtMaNV.setText("");
            txtHoTen.setText("");
            txtSDT.setText("");
            txtEmail.setText("");
            radNam.setSelected(true);
            cmbChucVu.setSelectedIndex(0);
            txtMaNV.requestFocus();
        }
        else if (o == btnXoa) {
            int row = tblNhanVien.getSelectedRow();
            if (row >= 0) {
                if (JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa nhân viên này?", "Xác nhận", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    modelNhanVien.removeRow(row);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}