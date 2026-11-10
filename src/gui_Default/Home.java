package gui_Default;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Home extends JScrollPane implements ActionListener, MouseListener {
    
    private static final long serialVersionUID = 1L;
    
    private ArrayList<ImageIcon> banners;
    private int currentBannerIndex = 0;
    private Timer slideTimer;
    private JLabel lblBanner;
    private JPanel pnlContent;


	private CardLayout mainCardLayout;

	private JPanel pnlProductVisible;

    public Home(CardLayout cardLayout, JPanel pnlCenter) {
    	this.mainCardLayout = cardLayout;
    	this.pnlProductVisible = pnlCenter;
    	
        pnlContent = new JPanel();
        pnlContent.setLayout(new BoxLayout(pnlContent, BoxLayout.Y_AXIS));
        pnlContent.setBackground(Color.WHITE);
        pnlContent.setBorder(BorderFactory.createEmptyBorder(15, 30, 30, 30));
        

        setViewportView(pnlContent);
        setBorder(null);
        getVerticalScrollBar().setUnitIncrement(16);
        
        lblBanner = new JLabel();
        lblBanner.setHorizontalAlignment(SwingConstants.CENTER);
        lblBanner.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlContent.add(lblBanner);
        
        banners = new ArrayList<>();
        loadBanner("/img/designer.png");
        loadBanner("/img/beyono.png"); 
        loadBanner("/img/cabeto.jpg"); 
        
        if (!banners.isEmpty()) {
            lblBanner.setIcon(banners.get(0));
        } else {
            lblBanner.setText("WELCOME TO SHOE STORE SYSTEM");
            lblBanner.setFont(new Font("Arial", Font.BOLD, 40));
            lblBanner.setForeground(new Color(220, 180, 230));
        }
        
        if (banners.size() > 1) {
            slideTimer = new Timer(3000, this);
            slideTimer.start();
        }

        pnlContent.add(Box.createVerticalStrut(30));

        JLabel lblTitle = new JLabel("SẢN PHẨM MỚI");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setForeground(new Color(180, 60, 200));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlContent.add(lblTitle);

        pnlContent.add(Box.createVerticalStrut(20));

        JPanel pnlGrid = new JPanel(new GridLayout(2, 3, 20, 20));
        pnlGrid.setBackground(Color.WHITE);
        pnlGrid.setMaximumSize(new Dimension(1100, 500));

        pnlGrid.add(createSportCard("GIÀY BÓNG ĐÁ", "Sân cỏ tự nhiên & nhân tạo", "Nike, Adidas, Puma", "/img/soccer.png"));
        pnlGrid.add(createSportCard("GIÀY BÓNG CHUYỀN", "Đệm giảm chấn, bám sân cực tốt", "Mizuno, Asics, VHD", "/img/volleyball.png"));
        pnlGrid.add(createSportCard("GIÀY CẦU LÔNG", "Đế kếp dẻo, chống lật cổ chân", "Yonex, Lining, Victor", "/img/badminton.png"));
        pnlGrid.add(createSportCard("GIÀY THỜI TRANG", "Phong cách trẻ trung, đi hàng ngày", "Vans, Converse, MLB", "/img/fashion.png"));
        pnlGrid.add(createSportCard("GIÀY BÓNG RỔ", "Cổ cao bảo vệ khớp, bám sân", "Nike Jordan, Peak", "/img/basketball.png"));
        pnlGrid.add(createSportCard("GIÀY CHẠY BỘ", "Siêu nhẹ, êm ái, tối ưu lực đẩy", "Adidas, Hoka, Puma", "/img/running.png"));

        pnlContent.add(pnlGrid);
        
        pnlContent.add(Box.createVerticalStrut(40)); 

        JLabel lblPartner = new JLabel("ĐỐI TÁC CUNG CẤP CHÍNH");
        lblPartner.setFont(new Font("Arial", Font.BOLD, 22));
        lblPartner.setForeground(new Color(180, 60, 200));
        lblPartner.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlContent.add(lblPartner);

        pnlContent.add(Box.createVerticalStrut(20));

        
        JPanel pnlPartners = new JPanel(new FlowLayout(FlowLayout.CENTER, 60, 10));
        pnlPartners.setBackground(Color.WHITE);
        pnlPartners.setAlignmentX(Component.CENTER_ALIGNMENT);

       
        pnlPartners.add(createPartnerLogo("/img/logo_vhd.png", "VHD"));
        pnlPartners.add(createPartnerLogo("/img/logo_beyono.png", "BEYONO"));
        pnlPartners.add(createPartnerLogo("/img/adidas.png", "ADIDAS"));

        pnlContent.add(pnlPartners);
        pnlContent.add(Box.createVerticalStrut(40));
        
        JPanel pnlWrapper = new JPanel(new BorderLayout());
        pnlWrapper.setBackground(Color.WHITE);
        pnlWrapper.add(pnlContent, BorderLayout.CENTER);
        pnlWrapper.add(createFooterPanel(), BorderLayout.SOUTH);
        
        setViewportView(pnlWrapper);
    }
    
    private Component createPartnerLogo(String imgPath, String altText) {
    	JLabel lbl = new JLabel();
        lbl.setHorizontalAlignment(SwingConstants.CENTER);
        try {
            java.net.URL url = getClass().getResource(imgPath);
            if (url != null) {
                Image img = new ImageIcon(url).getImage();
                Image scaledImg = img.getScaledInstance(150, 80, Image.SCALE_SMOOTH);
                lbl.setIcon(new ImageIcon(scaledImg));
            } else {

                lbl.setText("[ Logo " + altText + " ]");
                lbl.setFont(new Font("Arial", Font.BOLD, 18));
                lbl.setForeground(Color.GRAY);
                lbl.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
                lbl.setPreferredSize(new Dimension(150, 80));
            }
        } catch (Exception e) {}
        return lbl;
	}
    private JPanel createFooterPanel() {
       
        JPanel footer = new JPanel(new GridLayout(1, 4, 20, 20));
        footer.setBackground(new Color(90, 90, 90)); 
        footer.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));
        
        
        footer.add(createFooterColumn("SHOE STORE NHÓM 10", new String[]{
            "Được phát triển bởi Nhóm 10",
            "Trương Minh Nhật (Khóa K20)",
            "Đại học Công nghiệp TP.HCM (IUH)",
            "MSSV: 24663581"
        }));
        
        
        footer.add(createFooterColumn("HỖ TRỢ KHÁCH HÀNG", new String[]{
            "Hướng Dẫn Đặt Hàng",
            "Hướng Dẫn Thanh Toán",
            "Chính sách bảo hành",
            "Hỏi đáp vấn đề",
            "Tuyển dụng"
        }));
        
        
        footer.add(createFooterColumn("THÔNG TIN LIÊN HỆ", new String[]{
            "Địa chỉ: 12 Nguyễn Văn Bảo, Gò Vấp",
            "Thành phố Hồ Chí Minh",
            "Hotline: 0369.656.631",
            "Email: nhom10@iuh.edu.vn",
            "Zalo: 0989.202.998"
        }));
        
        
        JPanel pnlSocial = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        pnlSocial.setOpaque(false);
        pnlSocial.add(createSocialIcon("/img/logo-facebook.jpg", "FB"));
        pnlSocial.add(createSocialIcon("/img/tiktok.jpg", "Tik"));
        pnlSocial.add(createSocialIcon("/img/email.png", "Mail"));
        pnlSocial.add(createSocialIcon("/img/Youtube_logo.png", "YT"));
        
        JPanel pnlCol4 = new JPanel(new BorderLayout());
        pnlCol4.setOpaque(false);
        pnlCol4.add(pnlSocial, BorderLayout.NORTH); 
        footer.add(pnlCol4);
        
        return footer;
    }
    private JLabel createSocialIcon(String path, String altText) {
        JLabel lbl = new JLabel();
        try {
            java.net.URL url = getClass().getResource(path);
            if (url != null) {
                Image img = new ImageIcon(url).getImage().getScaledInstance(26, 26, Image.SCALE_SMOOTH);
                lbl.setIcon(new ImageIcon(img));
            } else {
                lbl.setText(altText);
                lbl.setForeground(Color.WHITE);
                lbl.setFont(new Font("Arial", Font.BOLD, 13));
            }
        } catch (Exception e) {}
        return lbl;
    }

	private JPanel createFooterColumn(String title, String[] items) {
        JPanel pnl = new JPanel();
        pnl.setLayout(new BoxLayout(pnl, BoxLayout.Y_AXIS));
        pnl.setOpaque(false); 
        
        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 15));
        lblTitle.setForeground(Color.WHITE);
        pnl.add(lblTitle);
        pnl.add(Box.createVerticalStrut(15));
        
        for (String item : items) {
            JLabel lblItem = new JLabel(item);
            lblItem.setFont(new Font("Arial", Font.PLAIN, 13));
            lblItem.setForeground(new Color(220, 220, 220));
            pnl.add(lblItem);
            pnl.add(Box.createVerticalStrut(10));
        }
        return pnl;
    }

	private void loadBanner(String path) {
        try {
            java.net.URL url = getClass().getResource(path);
            if (url != null) {
                Image img = new ImageIcon(url).getImage();
                Image scaledImg = img.getScaledInstance(1050, 420, Image.SCALE_SMOOTH);
                banners.add(new ImageIcon(scaledImg));
            }
        } catch (Exception e) {
            System.out.println("Không tìm thấy banner tại: " + path);
        }
    }

    private JPanel createSportCard(String title, String desc, String brands, String imgPath) {
        JPanel card = new JPanel(new BorderLayout(5, 5));
        card.setBackground(new Color(253, 240, 255));
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(230, 200, 240), 1, true),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));

        JLabel lblImg = new JLabel();
        lblImg.setHorizontalAlignment(SwingConstants.CENTER);
        try {
            java.net.URL imgUrl = getClass().getResource(imgPath);
            if (imgUrl != null) {
                Image img = new ImageIcon(imgUrl).getImage();
                Image scaledImg = img.getScaledInstance(350, 130, Image.SCALE_SMOOTH);
                lblImg.setIcon(new ImageIcon(scaledImg));
            } else {
                lblImg.setText("[ " + title + " ]");
                lblImg.setFont(new Font("Arial", Font.BOLD, 12));
                lblImg.setForeground(Color.GRAY);
            }
        } catch (Exception e) {}

        lblImg.addMouseListener(this);

        card.add(lblImg, BorderLayout.CENTER);

        JPanel pnlInfo = new JPanel(new GridLayout(3, 1, 2, 2));
        pnlInfo.setOpaque(false);

        JLabel lblName = new JLabel(title, SwingConstants.CENTER);
        lblName.setFont(new Font("Arial", Font.BOLD, 14));
        lblName.setForeground(new Color(150, 40, 170));

        JLabel lblDesc = new JLabel(desc, SwingConstants.CENTER);
        lblDesc.setFont(new Font("Arial", Font.ITALIC, 11));
        lblDesc.setForeground(Color.DARK_GRAY);

        JLabel lblBrands = new JLabel(brands, SwingConstants.CENTER);
        lblBrands.setFont(new Font("Arial", Font.PLAIN, 12));
        lblBrands.setForeground(new Color(180, 60, 200));

        pnlInfo.add(lblName);
        pnlInfo.add(lblDesc);
        pnlInfo.add(lblBrands);

        card.add(pnlInfo, BorderLayout.SOUTH);

        JLabel lblTag = new JLabel("Hàng mới về", SwingConstants.CENTER);
        lblTag.setFont(new Font("Arial", Font.BOLD, 9));
        lblTag.setForeground(Color.WHITE);
        lblTag.setOpaque(true);
        lblTag.setBackground(new Color(180, 60, 200));
        lblTag.setBorder(BorderFactory.createEmptyBorder(2, 6, 2, 6));
        
        JPanel pnlTop = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        pnlTop.setOpaque(false);
        pnlTop.add(lblTag);
        card.add(pnlTop, BorderLayout.NORTH);

        return card;
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!banners.isEmpty()) {
            currentBannerIndex = (currentBannerIndex + 1) % banners.size();
            lblBanner.setIcon(banners.get(currentBannerIndex));
        }
    }
	@Override
	public void mouseClicked(MouseEvent e) {
		mainCardLayout.show(pnlProductVisible, "PRODUCT");
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}