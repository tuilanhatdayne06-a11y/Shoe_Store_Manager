package gui_Default;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

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
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class KhachHangUI extends JPanel implements ActionListener, MouseListener {

    private JTextField txtMaKH, txtTenKH, txtSDT, txtDiaChi, txtDiem, txtTim;
    private JButton btnThem, btnSua, btnXoa, btnTim, btnLamMoi, btnChonAnh;
    private JTable table;
    private DefaultTableModel modelTable;
    private JLabel lblAnh, lblTenAnh, lblTrangThai;

    private String tenAnhDangChon = "";
    private HashMap<String, String> danhSachAnh = new HashMap<String, String>();

    private Color mauNen = new Color(253, 240, 255);
    private Color mauChinh = new Color(180, 60, 200);

    public KhachHangUI() {
        setLayout(new BorderLayout(12, 12));
        setBackground(mauNen);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTieuDe = new JLabel("QUẢN LÝ KHÁCH HÀNG", SwingConstants.CENTER);
        lblTieuDe.setFont(new Font("Arial", Font.BOLD, 24));
        lblTieuDe.setForeground(mauChinh);
        add(lblTieuDe, BorderLayout.NORTH);

        String[] cot = {"Mã KH", "Tên khách hàng", "SĐT", "Địa chỉ", "Điểm tích lũy"};
        modelTable = new DefaultTableModel(cot, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(modelTable);
        table.setRowHeight(28);
        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setBackground(new Color(235, 210, 240));
        table.addMouseListener(this);

        JPanel pTim = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 5));
        pTim.setBackground(mauNen);
        txtTim = new JTextField(16);
        btnTim = taoNut("Tìm");
        pTim.add(new JLabel("Tìm theo SĐT:"));
        pTim.add(txtTim);
        pTim.add(btnTim);

        JPanel pDanhSach = new JPanel(new BorderLayout(0, 8));
        pDanhSach.setOpaque(false);
        pDanhSach.add(pTim, BorderLayout.NORTH);
        pDanhSach.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel pForm = new JPanel(new BorderLayout(8, 12));
        pForm.setBackground(Color.WHITE);
        pForm.setPreferredSize(new Dimension(350, 0));
        pForm.setBorder(BorderFactory.createTitledBorder("Thông tin khách hàng"));

        txtMaKH = new JTextField();
        txtTenKH = new JTextField();
        txtSDT = new JTextField();
        txtDiaChi = new JTextField();
        txtDiem = new JTextField("0");
        txtDiem.setEditable(false);
        txtDiem.setBackground(new Color(245, 245, 245));

        JPanel pNhap = new JPanel(new GridLayout(5, 2, 8, 10));
        pNhap.setOpaque(false);
        pNhap.add(new JLabel("Mã khách hàng:"));
        pNhap.add(txtMaKH);
        pNhap.add(new JLabel("Tên khách hàng:"));
        pNhap.add(txtTenKH);
        pNhap.add(new JLabel("Số điện thoại:"));
        pNhap.add(txtSDT);
        pNhap.add(new JLabel("Địa chỉ:"));
        pNhap.add(txtDiaChi);
        pNhap.add(new JLabel("Điểm tích lũy:"));
        pNhap.add(txtDiem);
        pForm.add(pNhap, BorderLayout.NORTH);

        btnThem = taoNut("Thêm");
        btnSua = taoNut("Sửa");
        btnXoa = taoNut("Xóa");
        btnLamMoi = taoNut("Làm mới");
        btnChonAnh = taoNut("Chọn ảnh");

        JPanel pNut = new JPanel(new GridLayout(2, 2, 8, 8));
        pNut.setOpaque(false);
        pNut.add(btnThem);
        pNut.add(btnSua);
        pNut.add(btnXoa);
        pNut.add(btnLamMoi);

        JPanel pAnh = new JPanel(new BorderLayout(10, 5));
        pAnh.setBackground(Color.WHITE);
        pAnh.setBorder(BorderFactory.createTitledBorder("Ảnh đại diện"));
        pAnh.setPreferredSize(new Dimension(0, 145));

        lblAnh = new JLabel("Chưa có ảnh", SwingConstants.CENTER);
        lblAnh.setOpaque(true);
        lblAnh.setBackground(mauNen);
        lblAnh.setPreferredSize(new Dimension(115, 110));
        lblAnh.setBorder(BorderFactory.createLineBorder(new Color(215, 185, 230)));

        JPanel pChonAnh = new JPanel(new BorderLayout(5, 5));
        pChonAnh.setOpaque(false);
        lblTenAnh = new JLabel("Chưa chọn ảnh");
        pChonAnh.add(btnChonAnh, BorderLayout.NORTH);
        pChonAnh.add(lblTenAnh, BorderLayout.CENTER);

        pAnh.add(lblAnh, BorderLayout.WEST);
        pAnh.add(pChonAnh, BorderLayout.CENTER);

        JPanel pDuoiForm = new JPanel(new BorderLayout(0, 10));
        pDuoiForm.setOpaque(false);
        pDuoiForm.add(pNut, BorderLayout.NORTH);
        pDuoiForm.add(pAnh, BorderLayout.CENTER);
        pForm.add(pDuoiForm, BorderLayout.CENTER);

        JPanel pCenter = new JPanel(new BorderLayout(12, 0));
        pCenter.setOpaque(false);
        pCenter.add(pDanhSach, BorderLayout.CENTER);
        pCenter.add(pForm, BorderLayout.EAST);
        add(pCenter, BorderLayout.CENTER);

        lblTrangThai = new JLabel("Khách mới có 0 điểm. Mỗi 100.000đ thanh toán được cộng 1 điểm.");
        lblTrangThai.setForeground(Color.DARK_GRAY);
        add(lblTrangThai, BorderLayout.SOUTH);

        btnThem.addActionListener(this);
        btnSua.addActionListener(this);
        btnXoa.addActionListener(this);
        btnTim.addActionListener(this);
        btnLamMoi.addActionListener(this);
        btnChonAnh.addActionListener(this);
        txtTim.addActionListener(this);
    }

    private JButton taoNut(String ten) {
        JButton btn = new JButton(ten);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setBackground(mauChinh);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        return btn;
    }

    private boolean kiemTraDuLieu() {
        String ma = txtMaKH.getText().trim();
        String ten = txtTenKH.getText().trim();
        String sdt = txtSDT.getText().trim();
        String diaChi = txtDiaChi.getText().trim();

        if (!ma.matches("KH[A-Za-z0-9]{1,18}")) {
            JOptionPane.showMessageDialog(this, "Mã KH phải bắt đầu bằng KH và dài tối đa 20 ký tự!");
            txtMaKH.requestFocus();
            return false;
        }
        if (ten.isEmpty() || ten.length() > 100) {
            JOptionPane.showMessageDialog(this, "Tên khách hàng không được trống hoặc quá 100 ký tự!");
            txtTenKH.requestFocus();
            return false;
        }
        if (!sdt.matches("0[0-9]{9}")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại phải đúng 10 chữ số, bắt đầu bằng 0!");
            txtSDT.requestFocus();
            return false;
        }
        if (diaChi.length() > 200) {
            JOptionPane.showMessageDialog(this, "Địa chỉ không được quá 200 ký tự!");
            txtDiaChi.requestFocus();
            return false;
        }
        return true;
    }

    private void themKhachHang() {
        if (!kiemTraDuLieu()) {
            return;
        }
        String ma = txtMaKH.getText().trim();
        for (int i = 0; i < modelTable.getRowCount(); i++) {
            if (ma.equalsIgnoreCase(modelTable.getValueAt(i, 0).toString())) {
                JOptionPane.showMessageDialog(this, "Mã khách hàng đã tồn tại!");
                return;
            }
        }

        modelTable.addRow(new Object[] {
                ma, txtTenKH.getText().trim(), txtSDT.getText().trim(),
                txtDiaChi.getText().trim(), 0
        });
        if (!tenAnhDangChon.isEmpty()) {
            danhSachAnh.put(ma, tenAnhDangChon);
        }
        xoaTrang();
        lblTrangThai.setText("Đã thêm khách hàng " + ma);
        JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công!");
    }

    private void suaKhachHang() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng cần sửa!");
            return;
        }
        if (!kiemTraDuLieu()) {
            return;
        }
        String ma = modelTable.getValueAt(row, 0).toString();
        modelTable.setValueAt(txtTenKH.getText().trim(), row, 1);
        modelTable.setValueAt(txtSDT.getText().trim(), row, 2);
        modelTable.setValueAt(txtDiaChi.getText().trim(), row, 3);
        if (tenAnhDangChon.isEmpty()) {
            danhSachAnh.remove(ma);
        } else {
            danhSachAnh.put(ma, tenAnhDangChon);
        }
        lblTrangThai.setText("Đã sửa khách hàng " + ma);
        JOptionPane.showMessageDialog(this, "Sửa khách hàng thành công!");
    }

    private void xoaKhachHang() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng cần xóa!");
            return;
        }
        String ma = modelTable.getValueAt(row, 0).toString();
        int chon = JOptionPane.showConfirmDialog(this,
                "Bạn có muốn xóa khách hàng " + ma + "?",
                "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (chon == JOptionPane.YES_OPTION) {
            modelTable.removeRow(row);
            danhSachAnh.remove(ma);
            xoaTrang();
            lblTrangThai.setText("Đã xóa khách hàng " + ma);
        }
    }

    private void timKhachHang() {
        String sdt = txtTim.getText().trim();
        if (sdt.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số điện thoại cần tìm!");
            return;
        }
        for (int i = 0; i < modelTable.getRowCount(); i++) {
            if (modelTable.getValueAt(i, 2).toString().contains(sdt)) {
                table.setRowSelectionInterval(i, i);
                table.scrollRectToVisible(table.getCellRect(i, 0, true));
                napDuLieuLenForm(i);
                lblTrangThai.setText("Đã tìm thấy khách hàng có SĐT: " + sdt);
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng!");
    }

    private void napDuLieuLenForm(int row) {
        txtMaKH.setText(modelTable.getValueAt(row, 0).toString());
        txtMaKH.setEditable(false);
        txtTenKH.setText(modelTable.getValueAt(row, 1).toString());
        txtSDT.setText(modelTable.getValueAt(row, 2).toString());
        txtDiaChi.setText(modelTable.getValueAt(row, 3).toString());
        txtDiem.setText(modelTable.getValueAt(row, 4).toString());

        String ma = modelTable.getValueAt(row, 0).toString();
        tenAnhDangChon = danhSachAnh.get(ma);
        if (tenAnhDangChon == null) {
            tenAnhDangChon = "";
        }
        hienThiAnh();
    }

    private void xoaTrang() {
        table.clearSelection();
        txtMaKH.setText("");
        txtMaKH.setEditable(true);
        txtTenKH.setText("");
        txtSDT.setText("");
        txtDiaChi.setText("");
        txtDiem.setText("0");
        txtTim.setText("");
        tenAnhDangChon = "";
        hienThiAnh();
        txtMaKH.requestFocusInWindow();
    }

    private void chonAnh() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Ảnh PNG/JPG", "png", "jpg", "jpeg"));
        if (chooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File file = chooser.getSelectedFile();
        if (new ImageIcon(file.getAbsolutePath()).getIconWidth() <= 0) {
            JOptionPane.showMessageDialog(this, "File được chọn không phải ảnh hợp lệ!");
            return;
        }
        try {
            File thuMuc = new File("src/img");
            if (!thuMuc.exists()) {
                thuMuc.mkdirs();
            }
            File dich = new File(thuMuc, file.getName());
            if (!file.getCanonicalFile().equals(dich.getCanonicalFile())) {
                String ten = file.getName();
                int viTriCham = ten.lastIndexOf('.');
                String tenGoc = viTriCham < 0 ? ten : ten.substring(0, viTriCham);
                String duoi = viTriCham < 0 ? "" : ten.substring(viTriCham);
                int stt = 1;
                while (dich.exists()) {
                    dich = new File(thuMuc, tenGoc + "_" + stt + duoi);
                    stt++;
                }
                Files.copy(file.toPath(), dich.toPath());
            }
            tenAnhDangChon = dich.getName();
            hienThiAnh();
            lblTrangThai.setText("Đã chọn ảnh " + tenAnhDangChon);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Không thể chọn ảnh: " + ex.getMessage());
        }
    }

    private void hienThiAnh() {
        lblAnh.setIcon(null);
        lblAnh.setText("Chưa có ảnh");
        lblTenAnh.setText(tenAnhDangChon.isEmpty() ? "Chưa chọn ảnh" : tenAnhDangChon);
        lblTenAnh.setToolTipText(tenAnhDangChon);
        if (tenAnhDangChon.isEmpty()) {
            return;
        }

        ImageIcon icon = null;
        File file = new File("src/img", tenAnhDangChon);
        if (file.exists()) {
            icon = new ImageIcon(file.getPath());
        } else {
            java.net.URL url = getClass().getResource("/img/" + tenAnhDangChon);
            if (url != null) {
                icon = new ImageIcon(url);
            }
        }
        if (icon == null || icon.getIconWidth() <= 0) {
            lblAnh.setText("Không tìm thấy ảnh");
            return;
        }
        Image anh = icon.getImage().getScaledInstance(110, 105, Image.SCALE_SMOOTH);
        lblAnh.setIcon(new ImageIcon(anh));
        lblAnh.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnThem)) {
            themKhachHang();
        } else if (o.equals(btnSua)) {
            suaKhachHang();
        } else if (o.equals(btnXoa)) {
            xoaKhachHang();
        } else if (o.equals(btnTim) || o.equals(txtTim)) {
            timKhachHang();
        } else if (o.equals(btnLamMoi)) {
            xoaTrang();
            lblTrangThai.setText("Đã làm mới biểu mẫu.");
        } else if (o.equals(btnChonAnh)) {
            chonAnh();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(table)) {
            int row = table.getSelectedRow();
            if (row != -1) {
                napDuLieuLenForm(row);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

}
