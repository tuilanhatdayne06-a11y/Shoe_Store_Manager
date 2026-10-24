package gui_Default;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    private JPanel pnlCenter;
    private CardLayout mainCardLayout;

    public GUI() {
        setTitle("Phần Mềm Quản Lý Cửa Hàng Giày Dép - Nhóm 10");
        setSize(1200, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel pnlNorth = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 220));
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
                super.paintComponent(g);
            }
        };
        pnlNorth.setLayout(new BoxLayout(pnlNorth, BoxLayout.Y_AXIS));
        pnlNorth.setOpaque(false);

        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(Color.WHITE);
        pnlHeader.setPreferredSize(new Dimension(1200, 90));
        pnlHeader.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel lblAddress = new JLabel(" 12 Nguyễn Văn Bảo, Gò Vấp, TP.HCM");
        lblAddress.setFont(new Font("Arial", Font.PLAIN, 12));
        try {
            ImageIcon originalIcon = new ImageIcon(getClass().getResource("/img/map.png"));
            int targetHeight = 30;
            int targetWidth = (originalIcon.getIconWidth() * targetHeight) / originalIcon.getIconHeight();
            Image scaledImage = originalIcon.getImage().getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
            lblAddress.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            System.err.println("Không tìm thấy map.png");
        }
        lblAddress.setForeground(Color.DARK_GRAY);
        pnlHeader.add(lblAddress, BorderLayout.WEST);

        JLabel lblLogo = new JLabel("SHOE STORE");
        lblLogo.setFont(new Font("Arial", Font.BOLD, 16));
        try {
            ImageIcon originalIcon = new ImageIcon(getClass().getResource("/img/logo.png"));
            int targetHeight = 55;
            int targetWidth = (originalIcon.getIconWidth() * targetHeight) / originalIcon.getIconHeight();
            Image scaledImage = originalIcon.getImage().getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            System.err.println("Không tìm thấy logo.png");
        }
        lblLogo.setHorizontalTextPosition(SwingConstants.CENTER);
        lblLogo.setVerticalTextPosition(SwingConstants.BOTTOM);
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        pnlHeader.add(lblLogo, BorderLayout.CENTER);

        CardLayout loginCardLayout = new CardLayout();
        JPanel pnlRight = new JPanel(loginCardLayout);
        pnlRight.setOpaque(false);

        JButton btnChuaDangNhap = new JButton();
        try {
            java.net.URL empUrl = getClass().getResource("/img/employee.png");
            if (empUrl != null) {
                btnChuaDangNhap.setIcon(new ImageIcon(new ImageIcon(empUrl).getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH)));
            }
        } catch (Exception e) {
            System.err.println("Không tìm thấy employee.png");
        }
        btnChuaDangNhap.setToolTipText("Đăng nhập");
        btnChuaDangNhap.setContentAreaFilled(false);
        btnChuaDangNhap.setBorderPainted(false);
        btnChuaDangNhap.setFocusPainted(false);
        btnChuaDangNhap.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel pnlDaDangNhap = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        pnlDaDangNhap.setOpaque(false);

        JButton btnAvatar = new JButton();
        try {
            java.net.URL maleUrl = getClass().getResource("/img/employee_male.png");
            if (maleUrl != null) {
                btnAvatar.setIcon(new ImageIcon(new ImageIcon(maleUrl).getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH)));
            }
        } catch (Exception e) {
            System.err.println("Không tìm thấy employee_male.png");
        }
        btnAvatar.setToolTipText("Trương Minh Nhật (Thu ngân)");
        btnAvatar.setContentAreaFilled(false);
        btnAvatar.setBorderPainted(false);
        btnAvatar.setFocusPainted(false);
        btnAvatar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton btnLogout = new JButton();
        try {
            java.net.URL logoutUrl = getClass().getResource("/img/logout.png");
            if (logoutUrl != null) {
                btnLogout.setIcon(new ImageIcon(new ImageIcon(logoutUrl).getImage().getScaledInstance(28, 28, Image.SCALE_SMOOTH)));
            } else {
                btnLogout.setText("Đăng xuất");
            }
        } catch (Exception e) {
            btnLogout.setText("Đăng xuất");
        }
        btnLogout.setContentAreaFilled(false);
        btnLogout.setBorderPainted(false);
        btnLogout.setFocusPainted(false);
        btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));

        pnlDaDangNhap.add(btnAvatar);
        pnlDaDangNhap.add(btnLogout);

        pnlRight.add(btnChuaDangNhap, "ChuaDangNhap");
        pnlRight.add(pnlDaDangNhap, "DaDangNhap");
        loginCardLayout.show(pnlRight, "ChuaDangNhap");

        pnlHeader.add(pnlRight, BorderLayout.EAST);

        btnChuaDangNhap.addActionListener(e -> {
            new FrmLogin().setVisible(true);
            loginCardLayout.show(pnlRight, "DaDangNhap");
        });

        btnLogout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn muốn đăng xuất?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                loginCardLayout.show(pnlRight, "ChuaDangNhap");
            }
        });

        JPanel pnlMenu = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 10));
        pnlMenu.setBackground(new Color(245, 245, 245)); 
        
        JButton btnHome = createMenuButton("TRANG CHỦ");
        JButton btnProduct = createMenuButton("QUẢN LÝ SẢN PHẨM");
        JButton btnPOS = createMenuButton("LẬP HÓA ĐƠN");
        JButton btnReport = createMenuButton("THỐNG KÊ");

        pnlMenu.add(btnHome);
        pnlMenu.add(btnProduct);
        pnlMenu.add(btnPOS);
        pnlMenu.add(btnReport);

        pnlNorth.add(pnlHeader);
        pnlNorth.add(pnlMenu);
        add(pnlNorth, BorderLayout.NORTH);

        mainCardLayout = new CardLayout();
        pnlCenter = new JPanel(mainCardLayout);
        
        JPanel pnlDefaultHome = new JPanel(new BorderLayout());
        pnlDefaultHome.setBackground(Color.WHITE);
        JLabel lblBanner = new JLabel("WELCOME TO SHOE STORE SYSTEM", SwingConstants.CENTER);
        lblBanner.setFont(new Font("Arial", Font.BOLD, 40));
        lblBanner.setForeground(Color.LIGHT_GRAY);
        pnlDefaultHome.add(lblBanner, BorderLayout.CENTER);

        JPanel pnlProductHolder = new JPanel(); pnlProductHolder.setBackground(Color.CYAN);
        JPanel pnlPOSHolder = new JPanel(); pnlPOSHolder.setBackground(Color.ORANGE);
        JPanel pnlReportHolder = new JPanel(); pnlReportHolder.setBackground(Color.GREEN);

        pnlCenter.add(pnlDefaultHome, "HOME");
        pnlCenter.add(pnlProductHolder, "PRODUCT");
        pnlCenter.add(pnlPOSHolder, "POS");
        pnlCenter.add(pnlReportHolder, "REPORT");

        add(pnlCenter, BorderLayout.CENTER);

        btnHome.addActionListener(e -> mainCardLayout.show(pnlCenter, "HOME"));
        btnProduct.addActionListener(e -> mainCardLayout.show(pnlCenter, "PRODUCT"));
        btnPOS.addActionListener(e -> mainCardLayout.show(pnlCenter, "POS"));
        btnReport.addActionListener(e -> mainCardLayout.show(pnlCenter, "REPORT"));
    }

    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setForeground(Color.DARK_GRAY);
        btn.setContentAreaFilled(false); 
        btn.setBorderPainted(false);    
        btn.setFocusPainted(false);     
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
        return btn;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new GUI().setVisible(true);
        });
    }
}