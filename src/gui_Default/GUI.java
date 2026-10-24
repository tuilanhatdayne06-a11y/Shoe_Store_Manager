package gui_Default;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    private JPanel pnlCenter;
    private CardLayout mainCardLayout;

    private Color bgLight = new Color(253, 240, 255);
    private Color primaryColor = new Color(180, 60, 200);
    private Color hoverColor = new Color(150, 40, 170);

    public GUI() {
        setTitle("Phần Mềm Quản Lý Cửa Hàng Giày Dép - Nhóm 10");
        setSize(1200, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel pnlNorth = new JPanel();
        pnlNorth.setLayout(new BoxLayout(pnlNorth, BoxLayout.Y_AXIS));
        pnlNorth.setBackground(Color.WHITE);

        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(Color.WHITE);
        pnlHeader.setPreferredSize(new Dimension(1200, 100));
        pnlHeader.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));

        JLabel lblAddress = new JLabel(" 12 Nguyễn Văn Bảo, Gò Vấp, TP.HCM");
        lblAddress.setFont(new Font("Arial", Font.PLAIN, 12));
        try {
            ImageIcon originalIcon = new ImageIcon(getClass().getResource("/img/map.png"));
            Image scaledImage = originalIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
            lblAddress.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {}
        lblAddress.setForeground(Color.DARK_GRAY);
        pnlHeader.add(lblAddress, BorderLayout.WEST);

        JLabel lblLogo = new JLabel("SHOE STORE");
        lblLogo.setFont(new Font("Arial", Font.BOLD, 18));
        try {
            ImageIcon originalIcon = new ImageIcon(getClass().getResource("/img/logo.png"));
            Image scaledImage = originalIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {}
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
        } catch (Exception e) {}
        btnChuaDangNhap.setContentAreaFilled(false);
        btnChuaDangNhap.setBorderPainted(false);
        btnChuaDangNhap.setFocusPainted(false);
        btnChuaDangNhap.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel pnlDaDangNhap = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        pnlDaDangNhap.setOpaque(false);

        JButton btnAvatar = new JButton();
        btnAvatar.setContentAreaFilled(false);
        btnAvatar.setBorderPainted(false);
        btnAvatar.setFocusPainted(false);
        btnAvatar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        pnlDaDangNhap.add(btnAvatar);

        pnlRight.add(btnChuaDangNhap, "ChuaDangNhap");
        pnlRight.add(pnlDaDangNhap, "DaDangNhap");
        loginCardLayout.show(pnlRight, "ChuaDangNhap");

        pnlHeader.add(pnlRight, BorderLayout.EAST);

        btnChuaDangNhap.addActionListener(e -> {
            new FrmLogin(nv -> {
                String iconPath = nv.isGioiTinh() ? "/img/employee_male.png" : "/img/employee_female.png";
                java.net.URL avatarUrl = getClass().getResource(iconPath);
                if (avatarUrl != null) {
                    btnAvatar.setIcon(new ImageIcon(new ImageIcon(avatarUrl).getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH)));
                }
                
                String gioiTinh = nv.isGioiTinh() ? "Nam" : "Nữ";
                String tooltipText = "<html><b>Họ tên:</b> " + nv.getHoTen() + "<br><b>Mã NV:</b> " + nv.getMaNV() + "<br><b>Chức vụ:</b> " + nv.getChucVu() + "<br><b>Giới tính:</b> " + gioiTinh + "</html>";
                btnAvatar.setToolTipText(tooltipText);
                
                loginCardLayout.show(pnlRight, "DaDangNhap");
            }).setVisible(true);
        });

        JPanel pnlMenu = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 12));
        pnlMenu.setBackground(bgLight);
        pnlMenu.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, new Color(230, 200, 240)));

        JButton btnHome = createMenuButton("TRANG CHỦ");
        JButton btnSupplier = createMenuButton("NHÀ CUNG CẤP");
        JButton btnProduct = createMenuButton("SẢN PHẨM");
        JButton btnEmployee = createMenuButton("NHÂN VIÊN");
        JButton btnCustomer = createMenuButton("KHÁCH HÀNG");
        JButton btnImport = createMenuButton("NHẬP HÀNG");
        JButton btnPOS = createMenuButton("BÁN HÀNG");
        JButton btnReport = createMenuButton("BÁO CÁO");

        pnlMenu.add(btnHome);
        pnlMenu.add(btnSupplier);
        pnlMenu.add(btnProduct);
        pnlMenu.add(btnEmployee);
        pnlMenu.add(btnCustomer);
        pnlMenu.add(btnImport);
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
        lblBanner.setForeground(new Color(220, 180, 230));
        pnlDefaultHome.add(lblBanner, BorderLayout.CENTER);

        pnlCenter.add(pnlDefaultHome, "HOME");
        pnlCenter.add(createDummyPanel(Color.CYAN, "Giao Diện Nhà Cung Cấp"), "SUPPLIER");
        pnlCenter.add(createDummyPanel(Color.ORANGE, "Giao Diện Sản Phẩm"), "PRODUCT");
        pnlCenter.add(createDummyPanel(Color.LIGHT_GRAY, "Giao Diện Nhân Viên"), "EMPLOYEE");
        pnlCenter.add(createDummyPanel(Color.PINK, "Giao Diện Khách Hàng"), "CUSTOMER");
        pnlCenter.add(createDummyPanel(Color.YELLOW, "Giao Diện Nhập Hàng"), "IMPORT");
        pnlCenter.add(createDummyPanel(Color.GREEN, "Giao Diện Bán Hàng"), "POS");
        pnlCenter.add(createDummyPanel(Color.MAGENTA, "Giao Diện Báo Cáo"), "REPORT");

        add(pnlCenter, BorderLayout.CENTER);

        btnHome.addActionListener(e -> mainCardLayout.show(pnlCenter, "HOME"));
        btnSupplier.addActionListener(e -> mainCardLayout.show(pnlCenter, "SUPPLIER"));
        btnProduct.addActionListener(e -> mainCardLayout.show(pnlCenter, "PRODUCT"));
        btnEmployee.addActionListener(e -> mainCardLayout.show(pnlCenter, "EMPLOYEE"));
        btnCustomer.addActionListener(e -> mainCardLayout.show(pnlCenter, "CUSTOMER"));
        btnImport.addActionListener(e -> mainCardLayout.show(pnlCenter, "IMPORT"));
        btnPOS.addActionListener(e -> mainCardLayout.show(pnlCenter, "POS"));
        btnReport.addActionListener(e -> mainCardLayout.show(pnlCenter, "REPORT"));
    }

    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setForeground(primaryColor);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setForeground(hoverColor);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setForeground(primaryColor);
            }
        });
        return btn;
    }

    private JPanel createDummyPanel(Color bgColor, String text) {
        JPanel pnl = new JPanel(new BorderLayout());
        pnl.setBackground(bgColor);
        JLabel lbl = new JLabel(text, SwingConstants.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 24));
        pnl.add(lbl, BorderLayout.CENTER);
        return pnl;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}
        SwingUtilities.invokeLater(() -> new GUI().setVisible(true));
    }
}	
