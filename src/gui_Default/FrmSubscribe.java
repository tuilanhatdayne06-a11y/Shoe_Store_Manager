package gui_Default;

import HSK.gui_Default.entity.NhanVien;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FrmSubscribe extends JFrame {

    private JTextField txtMaNV;
    private JTextField txtHoTen;
    private JTextField txtChucVu;
    private JPasswordField txtMatKhau;
    private JRadioButton radNam;
    private JRadioButton radNu;
    private JButton btnLuu;

    private Color bgLight = new Color(253, 240, 255);
    private Color primaryColor = new Color(180, 60, 200);
    private Color hoverColor = new Color(150, 40, 170);
    private Color textDark = new Color(70, 20, 80);

    public FrmSubscribe() {
        setTitle("Hệ Thống - Đăng Ký");
        setSize(450, 470);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel pnlMain = new JPanel(new BorderLayout());
        pnlMain.setBackground(bgLight);
        setContentPane(pnlMain);

        JLabel lblTitle = new JLabel("ĐĂNG KÝ TÀI KHOẢN", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setForeground(primaryColor);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        pnlMain.add(lblTitle, BorderLayout.NORTH);

        JPanel pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setBackground(bgLight);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 15, 8, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtMaNV = new JTextField();
        txtHoTen = new JTextField();
        txtChucVu = new JTextField();
        txtMatKhau = new JPasswordField();

        styleField(txtMaNV);
        styleField(txtHoTen);
        styleField(txtChucVu);
        styleField(txtMatKhau);

        JPanel pnlGioiTinh = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        pnlGioiTinh.setBackground(bgLight);
        radNam = new JRadioButton("Nam", true);
        radNu = new JRadioButton("Nữ");
        radNam.setBackground(bgLight);
        radNu.setBackground(bgLight);
        radNam.setFont(new Font("Arial", Font.PLAIN, 14));
        radNu.setFont(new Font("Arial", Font.PLAIN, 14));
        radNam.setForeground(textDark);
        radNu.setForeground(textDark);
        
        ButtonGroup bg = new ButtonGroup();
        bg.add(radNam);
        bg.add(radNu);
        pnlGioiTinh.add(radNam);
        pnlGioiTinh.add(radNu);

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.3;
        pnlForm.add(taoLabel("Mã nhân viên:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        pnlForm.add(txtMaNV, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.3;
        pnlForm.add(taoLabel("Họ và tên:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        pnlForm.add(txtHoTen, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.3;
        pnlForm.add(taoLabel("Giới tính:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        pnlForm.add(pnlGioiTinh, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0.3;
        pnlForm.add(taoLabel("Chức vụ:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        pnlForm.add(txtChucVu, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0.3;
        pnlForm.add(taoLabel("Mật khẩu:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        pnlForm.add(txtMatKhau, gbc);

        pnlMain.add(pnlForm, BorderLayout.CENTER);

        JPanel pnlBottom = new JPanel();
        pnlBottom.setLayout(new BoxLayout(pnlBottom, BoxLayout.Y_AXIS));
        pnlBottom.setBackground(bgLight);
        pnlBottom.setBorder(BorderFactory.createEmptyBorder(10, 0, 15, 0));

        JPanel pnlBtn = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlBtn.setBackground(bgLight);

        btnLuu = new JButton("ĐĂNG KÝ");
        btnLuu.setFont(new Font("Arial", Font.BOLD, 14));
        btnLuu.setBackground(primaryColor);
        btnLuu.setForeground(Color.WHITE);
        btnLuu.setFocusPainted(false);
        btnLuu.setBorderPainted(false);
        btnLuu.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLuu.setPreferredSize(new Dimension(180, 40));

        btnLuu.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                btnLuu.setBackground(hoverColor);
            }
            public void mouseExited(MouseEvent evt) {
                btnLuu.setBackground(primaryColor);
            }
        });
        pnlBtn.add(btnLuu);

        JPanel pnlLink = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        pnlLink.setBackground(bgLight);
        
        JLabel lblDaCoTaiKhoan = new JLabel("Đã có tài khoản?");
        lblDaCoTaiKhoan.setFont(new Font("Arial", Font.PLAIN, 13));
        lblDaCoTaiKhoan.setForeground(textDark);
        
        JLabel lblDangNhap = new JLabel("Đăng nhập");
        lblDangNhap.setFont(new Font("Arial", Font.BOLD, 13));
        lblDangNhap.setForeground(primaryColor);
        lblDangNhap.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        lblDangNhap.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                new FrmLogin().setVisible(true);
                dispose();
            }
        });

        pnlLink.add(lblDaCoTaiKhoan);
        pnlLink.add(lblDangNhap);

        pnlBottom.add(pnlBtn);
        pnlBottom.add(pnlLink);

        pnlMain.add(pnlBottom, BorderLayout.SOUTH);

        btnLuu.addActionListener(e -> {
            String ma = txtMaNV.getText().trim();
            String ten = txtHoTen.getText().trim();
            boolean isNam = radNam.isSelected();
            String chucVu = txtChucVu.getText().trim();
            String pass = new String(txtMatKhau.getPassword()).trim();

            if (ma.isEmpty() || ten.isEmpty() || chucVu.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            NhanVien nv = new NhanVien(ma, ten, isNam, chucVu, pass);
            NhanVienDao dao = new NhanVienDao();

            if (dao.themNhanVien(nv)) {
                JOptionPane.showMessageDialog(this, "Đăng ký thành công! Vui lòng đăng nhập.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                new FrmLogin().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Đăng ký thất bại! Trùng mã nhân viên.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private JLabel taoLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Arial", Font.BOLD, 13));
        lbl.setForeground(textDark);
        return lbl;
    }

    private void styleField(JComponent comp) {
        comp.setFont(new Font("Arial", Font.PLAIN, 14));
        comp.setPreferredSize(new Dimension(0, 32));
        comp.setBorder(new CompoundBorder(
                new LineBorder(new Color(220, 190, 230), 1, true),
                new EmptyBorder(3, 8, 3, 8)
        ));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new FrmSubscribe().setVisible(true);
        });
    }
}