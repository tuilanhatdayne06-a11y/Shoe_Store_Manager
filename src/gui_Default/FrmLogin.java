package gui_Default;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FrmLogin extends JFrame {

    private JTextField txtMaNV;
    private JPasswordField txtMatKhau;
    private JButton btnDangNhap;

    private Color bgLight = new Color(253, 240, 255);
    private Color primaryColor = new Color(180, 60, 200);
    private Color hoverColor = new Color(150, 40, 170);
    private Color textDark = new Color(70, 20, 80);

    public FrmLogin() {
        setTitle("Hệ Thống - Đăng Nhập");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel pnlMain = new JPanel(new BorderLayout());
        pnlMain.setBackground(bgLight);
        setContentPane(pnlMain);

        JLabel lblTitle = new JLabel("ĐĂNG NHẬP", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setForeground(primaryColor);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(25, 0, 15, 0));
        pnlMain.add(lblTitle, BorderLayout.NORTH);

        JPanel pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setBackground(bgLight);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtMaNV = new JTextField();
        txtMatKhau = new JPasswordField();

        styleField(txtMaNV);
        styleField(txtMatKhau);

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.3;
        pnlForm.add(taoLabel("Mã nhân viên:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        pnlForm.add(txtMaNV, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.3;
        pnlForm.add(taoLabel("Mật khẩu:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        pnlForm.add(txtMatKhau, gbc);

        pnlMain.add(pnlForm, BorderLayout.CENTER);

        JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlBottom.setBackground(bgLight);
        pnlBottom.setBorder(BorderFactory.createEmptyBorder(10, 0, 25, 0));

        btnDangNhap = new JButton("ĐĂNG NHẬP");
        btnDangNhap.setFont(new Font("Arial", Font.BOLD, 14));
        btnDangNhap.setBackground(primaryColor);
        btnDangNhap.setForeground(Color.WHITE);
        btnDangNhap.setFocusPainted(false);
        btnDangNhap.setBorderPainted(false);
        btnDangNhap.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDangNhap.setPreferredSize(new Dimension(200, 40));

        btnDangNhap.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                btnDangNhap.setBackground(hoverColor);
            }
            public void mouseExited(MouseEvent evt) {
                btnDangNhap.setBackground(primaryColor);
            }
        });

        pnlBottom.add(btnDangNhap);
        pnlMain.add(pnlBottom, BorderLayout.SOUTH);

        btnDangNhap.addActionListener(e -> {
            String ma = txtMaNV.getText().trim();
            String pass = new String(txtMatKhau.getPassword()).trim();

            if (ma.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ mã và mật khẩu!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            NhanVienDao dao = new NhanVienDao();
            if (dao.dangNhap(ma, pass)) {
                JOptionPane.showMessageDialog(this, "Đăng nhập thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Sai mã nhân viên hoặc mật khẩu!", "Lỗi đăng nhập", JOptionPane.ERROR_MESSAGE);
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
        comp.setPreferredSize(new Dimension(0, 35));
        comp.setBorder(new CompoundBorder(
                new LineBorder(new Color(220, 190, 230), 1, true),
                new EmptyBorder(3, 8, 3, 8)
        ));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new FrmLogin().setVisible(true);
        });
    }
}