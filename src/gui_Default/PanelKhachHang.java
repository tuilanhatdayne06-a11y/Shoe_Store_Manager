package gui_Default;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.regex.Pattern;

public class PanelKhachHang extends JPanel {

    private Color bgLight = new Color(253, 240, 255);
    private Color primaryColor = new Color(180, 60, 200);

    private JTextField txtMa = new JTextField();
    private JTextField txtTen = new JTextField();
    private JTextField txtSDT = new JTextField();
    private JTextField txtDiaChi = new JTextField();
    private JTextField txtDiem = new JTextField();
    private JTextField txtTim = new JTextField(18);

    private DefaultTableModel model = new DefaultTableModel(
        new String[]{"Mã KH", "Tên khách hàng", "SĐT", "Địa chỉ", "Điểm tích lũy"}, 0
    ) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    private JTable tblKhachHang = new JTable(model);
    private TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
    private JLabel lblTrangThai = new JLabel("Chưa có khách hàng. Hãy nhập thông tin để thêm mới.");

    public PanelKhachHang() {
        setLayout(new BorderLayout(15, 15));
        setBackground(bgLight);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitle = new JLabel("QUẢN LÝ KHÁCH HÀNG", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(primaryColor);
        add(lblTitle, BorderLayout.NORTH);

        tblKhachHang.setRowHeight(28);
        tblKhachHang.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblKhachHang.getTableHeader().setBackground(new Color(235, 210, 240));
        tblKhachHang.setRowSorter(sorter);

        JPanel pnlTimKiem = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        pnlTimKiem.setBackground(bgLight);
        JButton btnTim = taoNut("Tìm kiếm");
        pnlTimKiem.add(new JLabel("SĐT khách hàng:"));
        pnlTimKiem.add(txtTim);
        pnlTimKiem.add(btnTim);

        JPanel pnlDanhSach = new JPanel(new BorderLayout(0, 10));
        pnlDanhSach.setOpaque(false);
        pnlDanhSach.add(pnlTimKiem, BorderLayout.NORTH);
        pnlDanhSach.add(new JScrollPane(tblKhachHang), BorderLayout.CENTER);

        JPanel pnlForm = new JPanel(new GridLayout(7, 2, 8, 12));
        pnlForm.setBackground(Color.WHITE);
        pnlForm.setBorder(BorderFactory.createTitledBorder("Thông tin khách hàng"));
        pnlForm.setPreferredSize(new Dimension(365, 0));

        txtMa.setToolTipText("Mã KH bắt đầu bằng KH, tối đa 20 ký tự");
        txtSDT.setToolTipText("Đúng 10 chữ số, bắt đầu bằng 0");
        txtDiem.setEditable(false);
        txtDiem.setBackground(new Color(245, 245, 245));
        txtDiem.setToolTipText("Điểm tích lũy do hệ thống cập nhật khi thanh toán");
        txtDiem.setText("0");

        pnlForm.add(new JLabel("Mã KH:"));
        pnlForm.add(txtMa);
        pnlForm.add(new JLabel("Tên KH:"));
        pnlForm.add(txtTen);
        pnlForm.add(new JLabel("SĐT:"));
        pnlForm.add(txtSDT);
        pnlForm.add(new JLabel("Địa chỉ:"));
        pnlForm.add(txtDiaChi);
        pnlForm.add(new JLabel("Điểm tích lũy:"));
        pnlForm.add(txtDiem);

        JButton btnThem = taoNut("Thêm");
        JButton btnSua = taoNut("Sửa");
        JButton btnXoa = taoNut("Xóa");
        JButton btnLamMoi = taoNut("Làm mới");

        pnlForm.add(btnThem);
        pnlForm.add(btnSua);
        pnlForm.add(btnXoa);
        pnlForm.add(btnLamMoi);

        JPanel pnlCenter = new JPanel(new BorderLayout(15, 0));
        pnlCenter.setOpaque(false);
        pnlCenter.add(pnlDanhSach, BorderLayout.CENTER);
        pnlCenter.add(pnlForm, BorderLayout.EAST);
        add(pnlCenter, BorderLayout.CENTER);

        JPanel pnlBottom = new JPanel(new GridLayout(2, 1));
        pnlBottom.setOpaque(false);
        JLabel lblGhiChu = new JLabel(
            "Khách mới: 0 điểm  |  Mỗi 100.000đ thanh toán: +1 điểm  |  Điểm không được âm"
        );
        lblGhiChu.setForeground(Color.DARK_GRAY);
        pnlBottom.add(lblGhiChu);
        pnlBottom.add(lblTrangThai);
        add(pnlBottom, BorderLayout.SOUTH);

        btnThem.addActionListener(e -> themKhachHang());
        btnSua.addActionListener(e -> suaKhachHang());
        btnXoa.addActionListener(e -> xoaKhachHang());
        btnTim.addActionListener(e -> timKhachHang());
        txtTim.addActionListener(e -> timKhachHang());
        btnLamMoi.addActionListener(e -> lamMoi());

        tblKhachHang.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tblKhachHang.getSelectedRow() != -1) {
                int row = tblKhachHang.convertRowIndexToModel(tblKhachHang.getSelectedRow());
                txtMa.setText(model.getValueAt(row, 0).toString());
                txtTen.setText(model.getValueAt(row, 1).toString());
                txtSDT.setText(model.getValueAt(row, 2).toString());
                txtDiaChi.setText(model.getValueAt(row, 3).toString());
                txtDiem.setText(model.getValueAt(row, 4).toString());
                txtMa.setEditable(false); 
            }
        });
    }

    private JButton taoNut(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(primaryColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        return btn;
    }

    private boolean kiemTraForm() {
        String ma = txtMa.getText().trim();
        String ten = txtTen.getText().trim();
        String sdt = txtSDT.getText().trim();
        String diaChi = txtDiaChi.getText().trim();

        if (!ma.matches("KH[A-Za-z0-9]{1,18}")) {
            lblTrangThai.setText("Lỗi: Mã phải bắt đầu KH, có ký tự theo sau, tối đa 20 ký tự.");
            return false;
        }
        if (ten.isEmpty() || ten.length() > 100) {
            lblTrangThai.setText("Lỗi: Tên khách hàng phải có từ 1 đến 100 ký tự.");
            return false;
        }
        if (!sdt.matches("0[0-9]{9}")) {
            lblTrangThai.setText("Lỗi: SĐT phải gồm 10 chữ số và bắt đầu bằng 0.");
            return false;
        }
        if (diaChi.length() > 200) {
            lblTrangThai.setText("Lỗi: Địa chỉ không được quá 200 ký tự.");
            return false;
        }
        return true;
    }

    private void themKhachHang() {
        if (!kiemTraForm()) return;
        String ma = txtMa.getText().trim();
        for (int i = 0; i < model.getRowCount(); i++) {
            if (model.getValueAt(i, 0).toString().equalsIgnoreCase(ma)) {
                lblTrangThai.setText("Lỗi: Mã khách hàng đã tồn tại.");
                return;
            }
        }
        model.addRow(new Object[]{
            ma, txtTen.getText().trim(), txtSDT.getText().trim(),
            txtDiaChi.getText().trim(), 0
        });
        lamMoiForm();
        lblTrangThai.setText("Đã thêm khách hàng " + ma + ".");
    }

    private void suaKhachHang() {
        int rowView = tblKhachHang.getSelectedRow();
        if (rowView == -1) {
            lblTrangThai.setText("Hãy chọn một khách hàng trong bảng để sửa.");
            return;
        }
        if (!kiemTraForm()) return;
        int row = tblKhachHang.convertRowIndexToModel(rowView);
        model.setValueAt(txtTen.getText().trim(), row, 1);
        model.setValueAt(txtSDT.getText().trim(), row, 2);
        model.setValueAt(txtDiaChi.getText().trim(), row, 3);
        lblTrangThai.setText("Đã sửa khách hàng " + model.getValueAt(row, 0) + ".");
    }

    private void xoaKhachHang() {
        int rowView = tblKhachHang.getSelectedRow();
        if (rowView == -1) {
            lblTrangThai.setText("Hãy chọn một khách hàng trong bảng để xóa.");
            return;
        }
        int row = tblKhachHang.convertRowIndexToModel(rowView);
        String ma = model.getValueAt(row, 0).toString();
        int chon = JOptionPane.showConfirmDialog(
            this, "Xóa khách hàng " + ma + " khỏi danh sách đang chạy?",
            "Xác nhận xóa", JOptionPane.YES_NO_OPTION
        );
        if (chon == JOptionPane.YES_OPTION) {
            model.removeRow(row);
            lamMoiForm();
            lblTrangThai.setText("Đã xóa khách hàng " + ma + ".");
        }
    }

    private void timKhachHang() {
        String sdt = txtTim.getText().trim();
        sorter.setRowFilter(sdt.isEmpty() ? null :
            RowFilter.regexFilter(Pattern.quote(sdt), 2));
        tblKhachHang.clearSelection();
        lblTrangThai.setText("Đang hiển thị " + tblKhachHang.getRowCount() + " khách hàng.");
    }

    private void lamMoiForm() {
        tblKhachHang.clearSelection();
        txtMa.setEditable(true);
        txtMa.setText("");
        txtTen.setText("");
        txtSDT.setText("");
        txtDiaChi.setText("");
        txtDiem.setText("0");
    }

    private void lamMoi() {
        txtTim.setText("");
        sorter.setRowFilter(null);
        lamMoiForm();
        lblTrangThai.setText("Đã làm mới. Có " + model.getRowCount() + " khách hàng trong danh sách.");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Khách hàng - Shoe Store");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setContentPane(new PanelKhachHang());
            frame.setSize(1100, 650);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
