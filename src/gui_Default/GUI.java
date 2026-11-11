package gui_Default;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class GUI extends JFrame implements ActionListener {
	/**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    private JPanel pnlCenter;

    private CardLayout mainCardLayout;
    
    private JPanel pnlRight;
    private CardLayout loginCardLayout;

    private JButton btnChuaDangNhap;
    private JButton btnAvatar;
    private JButton btnLogout;
    private JButton btnHome;
    private JButton btnSupplier;
    private JButton btnProduct;
    private JButton btnEmployee;
    private JButton btnCustomer;
    private JButton btnImport;
    private JButton btnPOS;
    private JButton btnReport;

    private Color bgLight = new Color(253, 240, 255);
    private Color primaryColor = new Color(180, 60, 200);
    private Color hoverColor = new Color(150, 40, 170);
    
    private String currentNhanVienInfo = "";

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
            ImageIcon originalIcon = new ImageIcon(getClass().getResource("/img/Logo.png"));

            Image scaledImage = originalIcon.getImage().getScaledInstance(120, 50, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {}
        lblLogo.setHorizontalTextPosition(SwingConstants.CENTER);
        lblLogo.setVerticalTextPosition(SwingConstants.BOTTOM);
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        pnlHeader.add(lblLogo, BorderLayout.CENTER);

        loginCardLayout = new CardLayout();
        pnlRight = new JPanel(loginCardLayout);
        pnlRight.setOpaque(false);

        btnChuaDangNhap = new JButton("Đăng Nhập");
        btnChuaDangNhap.setFont(new Font("Arial", Font.BOLD, 20));
        try {
            java.net.URL empUrl = getClass().getResource("/img/employee.png");
            if (empUrl != null) {
                btnChuaDangNhap.setIcon(new ImageIcon(new ImageIcon(empUrl).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
            }
        } catch (Exception e) {}
        btnChuaDangNhap.setContentAreaFilled(false);
        btnChuaDangNhap.setBorderPainted(false);
        btnChuaDangNhap.setFocusPainted(false);
        btnChuaDangNhap.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnChuaDangNhap.addActionListener(this);

        JPanel pnlDaDangNhap = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        pnlDaDangNhap.setOpaque(false);

        btnAvatar = new JButton();
        btnAvatar.setFont(new Font("Arial", Font.BOLD, 20));
        btnAvatar.setIconTextGap(10);
        btnAvatar.setContentAreaFilled(false);
        btnAvatar.setBorderPainted(false);
        btnAvatar.setFocusPainted(false);
        btnAvatar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAvatar.addActionListener(this);

        btnLogout = new JButton("Đăng Xuất");
        btnLogout.setFont(new Font("Arial", Font.BOLD, 20));
        btnLogout.setIconTextGap(10);
        try {
            java.net.URL logoutUrl = getClass().getResource("/img/logout.png");
            if (logoutUrl != null) {
                btnLogout.setIcon(new ImageIcon(new ImageIcon(logoutUrl).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
            }
        } catch (Exception e) {}
        btnLogout.setContentAreaFilled(false);
        btnLogout.setBorderPainted(false);
        btnLogout.setFocusPainted(false);
        btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogout.addActionListener(this);

        pnlDaDangNhap.add(btnAvatar);
        pnlDaDangNhap.add(btnLogout);

        pnlRight.add(btnChuaDangNhap, "ChuaDangNhap");
        pnlRight.add(pnlDaDangNhap, "DaDangNhap");
        loginCardLayout.show(pnlRight, "ChuaDangNhap");

        pnlHeader.add(pnlRight, BorderLayout.EAST);


        btnChuaDangNhap.addActionListener(e -> {
            new FrmLogin(nv -> {
                String iconPath = nv.isGender() ? "/img/employee_male.png" : "/img/employee_female.png";
                java.net.URL avatarUrl = getClass().getResource(iconPath);
                if (avatarUrl != null) {
                    btnAvatar.setIcon(new ImageIcon(new ImageIcon(avatarUrl).getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH)));
                }
                
                String gioiTinh = nv.isGender() ? "Nam" : "Nữ";
                String tooltipText = "<html><b>Họ tên:</b> " + nv.getEmployeeName() + "<br><b>Mã NV:</b> " + nv.getEmployeeId() + "<br><b>Chức vụ:</b> " + nv.getPosition() + "<br><b>Giới tính:</b> " + gioiTinh + "</html>";
                btnAvatar.setToolTipText(tooltipText);
                
                loginCardLayout.show(pnlRight, "DaDangNhap");
            }).setVisible(true);
        });

        JPanel pnlMenu = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 12));
        pnlMenu.setBackground(bgLight);
        pnlMenu.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, new Color(230, 200, 240)));

        btnHome = createMenuButton("TRANG CHỦ");
        btnSupplier = createMenuButton("NHÀ CUNG CẤP");
        btnProduct = createMenuButton("SẢN PHẨM");
        btnEmployee = createMenuButton("NHÂN VIÊN");
        btnCustomer = createMenuButton("KHÁCH HÀNG");
        btnImport = createMenuButton("NHẬP HÀNG");
        btnPOS = createMenuButton("BÁN HÀNG");
        btnReport = createMenuButton("BÁO CÁO");

        btnHome.addActionListener(this);
        btnSupplier.addActionListener(this);
        btnProduct.addActionListener(this);
        btnEmployee.addActionListener(this);
        btnCustomer.addActionListener(this);
        btnImport.addActionListener(this);
        btnPOS.addActionListener(this);
        btnReport.addActionListener(this);

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

            
        pnlCenter.add(new Home(mainCardLayout, pnlCenter), "HOME");
        pnlCenter.add(new SupplierUI(), "SUPPLIER");
        pnlCenter.add(new EmployeeUI(), "EMPLOYEE");
        pnlCenter.add(new ProductUI(), "PRODUCT");

        pnlCenter.add(new ImportProductUI(), "IMPORT");
        pnlCenter.add(new SaleProductUI(), "POS");

        pnlCenter.add(new CustomerUI(), "CUSTOMER");
       //pnlCenter.add(createDummyPanel(Color.YELLOW, "Giao Diện Nhập Hàng"), "IMPORT");
       //pnlCenter.add(createDummyPanel(Color.GREEN, "Giao Diện Bán Hàng"), "POS");
        pnlCenter.add(new ReportUI(), "REPORT");

        

        add(pnlCenter, BorderLayout.CENTER);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource(); 

        if (o == btnHome) {
            mainCardLayout.show(pnlCenter, "HOME");
        } else if (o == btnSupplier) {
            mainCardLayout.show(pnlCenter, "SUPPLIER");
        } else if (o == btnProduct) {
            mainCardLayout.show(pnlCenter, "PRODUCT");
        } else if (o == btnEmployee) {
            mainCardLayout.show(pnlCenter, "EMPLOYEE");
        } else if (o == btnCustomer) {
            mainCardLayout.show(pnlCenter, "CUSTOMER");
        } else if (o == btnImport) {
            mainCardLayout.show(pnlCenter, "IMPORT");
        } else if (o == btnPOS) {
            mainCardLayout.show(pnlCenter, "POS");

        } else if (o == btnReport) {
            mainCardLayout.show(pnlCenter, "REPORT");
        }
        else if (o == btnChuaDangNhap) {
            new FrmLogin(nv -> {
                String iconPath = nv.isGender() ? "/img/employee_male.png" : "/img/employee_female.png";
                java.net.URL avatarUrl = getClass().getResource(iconPath);
                if (avatarUrl != null) {
                    btnAvatar.setIcon(new ImageIcon(new ImageIcon(avatarUrl).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
                }
                
                String gioiTinh = nv.isGender() ? "Nam" : "Nữ";
                btnAvatar.setText(nv.getEmployeeName());
                btnAvatar.setToolTipText("<html><b>Họ tên:</b> " + nv.getEmployeeName() + "<br><b>Chức vụ:</b> " + nv.getPosition() + "</html>");
                
                currentNhanVienInfo = "BẢNG THÔNG TIN \n\n"
                                    + "Họ và tên: " + nv.getEmployeeName() + "\n"
                                    + "Mã nhân viên: " + nv.getEmployeeId() + "\n"
                                    + "Chức vụ: " + nv.getPosition() + "\n"
                                    + "Giới tính: " + gioiTinh;
                
                loginCardLayout.show(pnlRight, "DaDangNhap");
            }).setVisible(true);
        }
        else if (o == btnLogout) {
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn muốn đăng xuất?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                loginCardLayout.show(pnlRight, "ChuaDangNhap");
                mainCardLayout.show(pnlCenter, "HOME");
                currentNhanVienInfo = ""; 
            }
        }
        else if (o == btnAvatar) {
            if (!currentNhanVienInfo.isEmpty()) {
                JOptionPane.showMessageDialog(this, currentNhanVienInfo, "Chi tiết nhân viên", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}
        SwingUtilities.invokeLater(() -> new GUI().setVisible(true));
    }
}