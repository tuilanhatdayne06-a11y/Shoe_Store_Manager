package gui_Default;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import dao.EmployeeDao;
import entity.Employee;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class EmployeeUI extends JPanel implements ActionListener {
<<<<<<< HEAD

=======
	/**
	 * 
	 */
>>>>>>> 41578e46e439ea9d03dc499b971a5e3051700e73
    private static final long serialVersionUID = 1L;
    private JTextField txtMaNV;
    private JTextField txtHoTen;
    private JTextField txtSDT;
    private JTextField txtTimKiem;
    private JRadioButton radNam;
    private JRadioButton radNu;
    private JComboBox<String> cmbChucVu;
    private JButton btnThem;
    private JButton btnXoa;
    private JButton btnSua;
    private JButton btnXoaTrang;
    private JButton btnLamMoi;
    private JButton btnTimKiem;
    private JTable tblNhanVien;
    private DefaultTableModel modelNhanVien;

    private Color primaryColor = new Color(180, 60, 200);
    private Color bgLight = new Color(253, 240, 255);
    
    private EmployeeDao dao = new EmployeeDao();

    public EmployeeUI() {
        setLayout(new BorderLayout(10, 15));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel pnlTop = new JPanel(new BorderLayout(0, 15));
        pnlTop.setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel("QUẢN LÝ NHÂN VIÊN", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(primaryColor);
        pnlTop.add(lblTitle, BorderLayout.NORTH);

        JPanel pnlForm = new JPanel(new GridLayout(3, 4, 15, 15));
        pnlForm.setBackground(bgLight);
        TitledBorder formBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(primaryColor), "Thông tin nhân viên");
        formBorder.setTitleColor(primaryColor);
        formBorder.setTitleFont(new Font("Arial", Font.BOLD, 14));
        pnlForm.setBorder(BorderFactory.createCompoundBorder(formBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        pnlForm.add(new JLabel("Mã Nhân Viên:"));
        txtMaNV = new JTextField();
        txtMaNV.setEditable(false);
        txtMaNV.setBackground(Color.LIGHT_GRAY);
        pnlForm.add(txtMaNV);

        pnlForm.add(new JLabel("Họ Tên:"));
        txtHoTen = new JTextField();
        pnlForm.add(txtHoTen);

        pnlForm.add(new JLabel("Số Điện Thoại:"));
        txtSDT = new JTextField();
        pnlForm.add(txtSDT);

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

        JPanel pnlAction = new JPanel(new BorderLayout());
        pnlAction.setBackground(Color.WHITE);

        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        pnlButtons.setBackground(Color.WHITE);
        
        btnThem = createButton("Thêm");
        btnXoa = createButton("Xóa");
        btnSua = createButton("Sửa");
        btnXoaTrang = createButton("Xóa trắng");
        btnLamMoi = createButton("Làm mới");

        pnlButtons.add(btnThem);
        pnlButtons.add(btnXoa);
        pnlButtons.add(btnSua);
        pnlButtons.add(btnXoaTrang);
        pnlButtons.add(btnLamMoi);

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

        tblNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = tblNhanVien.getSelectedRow();
                if (row >= 0) {
                    txtMaNV.setText(modelNhanVien.getValueAt(row, 0).toString());
                    txtHoTen.setText(modelNhanVien.getValueAt(row, 1).toString());
                    String gioiTinh = modelNhanVien.getValueAt(row, 2).toString();
                    if (gioiTinh.equalsIgnoreCase("Nam")) {
                        radNam.setSelected(true);
                    } else {
                        radNu.setSelected(true);
                    }
                    txtSDT.setText(modelNhanVien.getValueAt(row, 3).toString());
                    cmbChucVu.setSelectedItem(modelNhanVien.getValueAt(row, 4).toString());
                }
            }
        });

        loadDataToTable();
        btnXoaTrang.doClick();
    }

    private void loadDataToTable() {
        modelNhanVien.setRowCount(0);
        ArrayList<Object[]> list = dao.getAllEmployee();
        for (Object[] row : list) {
            modelNhanVien.addRow(row);
        }
    }

    private String phatSinhMaNV() {
        ArrayList<Integer> danhSachSo = new ArrayList<>();
        for (int i = 0; i < modelNhanVien.getRowCount(); i++) {
            String id = modelNhanVien.getValueAt(i, 0).toString();
            try {
                int num = Integer.parseInt(id.replaceAll("\\D+", ""));
                danhSachSo.add(num);
            } catch (Exception e) {}
        }
        
        int soMoi = 1;
        while (danhSachSo.contains(soMoi)) {
            soMoi++;
        }
        
        return String.format("NV%03d", soMoi);
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
            boolean isGender = radNam.isSelected();
            String gioiTinh = isGender ? "Nam" : "Nữ";
            String chucVu = cmbChucVu.getSelectedItem().toString();

            if (ten.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập họ tên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Employee nv = new Employee(ma, ten, isGender, sdt, chucVu);
            String matKhauMacDinh = "123456"; 
            
            if (dao.themNhanVien(nv, matKhauMacDinh)) {
                modelNhanVien.addRow(new Object[]{ma, ten, gioiTinh, sdt, chucVu});
                JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công vào CSDL!");
                btnXoaTrang.doClick(); 
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi! Không thể thêm vào CSDL.");
            }
        } 
        else if (o == btnXoaTrang) {
            txtHoTen.setText("");
            txtSDT.setText("");
            radNam.setSelected(true);
            cmbChucVu.setSelectedIndex(0);
            txtMaNV.setText(phatSinhMaNV());
            txtHoTen.requestFocus();
        }
        else if (o == btnXoa) {
            int row = tblNhanVien.getSelectedRow();
            if (row >= 0) {
                if (JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa nhân viên này khỏi cơ sở dữ liệu?", "Xác nhận", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    String maNV = modelNhanVien.getValueAt(row, 0).toString();
                    if (dao.xoaNhanVien(maNV)) {
                        modelNhanVien.removeRow(row);
                        JOptionPane.showMessageDialog(this, "Xóa thành công!");
                        btnXoaTrang.doClick();
                    } else {
                        JOptionPane.showMessageDialog(this, "Xóa thất bại! Nhân viên này có thể đang tồn tại trong các hóa đơn.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        }
        else if (o == btnSua) {
            int row = tblNhanVien.getSelectedRow();
            if (row >= 0) {
                String ma = txtMaNV.getText();
                String ten = txtHoTen.getText();
                String sdt = txtSDT.getText();
                boolean isGender = radNam.isSelected();
                String gioiTinh = isGender ? "Nam" : "Nữ";
                String chucVu = cmbChucVu.getSelectedItem().toString();

                if (ten.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập họ tên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                Employee nv = new Employee(ma, ten, isGender, sdt, chucVu);
                
                if (dao.capNhatNhanVien(nv)) {
                    modelNhanVien.setValueAt(ten, row, 1);
                    modelNhanVien.setValueAt(gioiTinh, row, 2);
                    modelNhanVien.setValueAt(sdt, row, 3);
                    modelNhanVien.setValueAt(chucVu, row, 4);
                    JOptionPane.showMessageDialog(this, "Cập nhật thành công vào CSDL!");
                    btnXoaTrang.doClick();
                } else {
                    JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhân viên từ bảng để sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        }
        else if (o == btnLamMoi) {
            loadDataToTable();
            btnXoaTrang.doClick();
        }
    }
}