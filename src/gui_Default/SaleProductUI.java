package gui_Default;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import dao.ProductDAO;
import dao.ProductDetailDAO;
import entity.ProductDetail;
import utils.AppUtils;


public class SaleProductUI extends JPanel{
	private JTextField txtFind;
	private JButton btnFind;
	private AppUtils appUtils = new AppUtils();
	private ProductDAO productDao = new ProductDAO();
	private JPanel pnlDisplayCard;
	private ProductDetailDAO productDetailDao = new ProductDetailDAO();
	private JButton btnResetFilter;
	
	private void triggerEvent()
	{
		txtFind.addActionListener(e -> {
			findByName(txtFind.getText().toString());
			txtFind.setText("");
		});
		
		btnFind.addActionListener(e -> {
			findByName(txtFind.getText().toString());
		});
		
		btnResetFilter.addActionListener(e -> {
			displayListProduct(null);
		});
	}
	
	private void findByName(String name)
	{
		ArrayList<ProductDetail> list = productDetailDao.findProduct(name);
		displayListProduct(list);
	}
	
	private JPanel generateProductCard(ProductDetail pd)
	{
		String PATH = "src/img/" + pd.getImgProductColor();
		JPanel pnlCard = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		pnlCard.setBackground(new Color(255, 255, 255));

		gbc.insets = new Insets(20, 40, 20, 40);
		gbc.gridx = 0; gbc.gridy = 0;gbc.weightx = 1.0;
		gbc.anchor = GridBagConstraints.CENTER;
		JLabel lblImg = new JLabel();
		lblImg.setPreferredSize(new Dimension(80, 80));	
		try {
			ImageIcon icon = new ImageIcon(PATH);
			Image scale = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
			lblImg.setIcon(new ImageIcon(scale));
		} catch (Exception e) {
			e.printStackTrace();
		}
		pnlCard.add(lblImg, gbc);
		
		gbc.gridy = 1;gbc.insets = new Insets(10, 0, 0, 0);
		JPanel pnlTitle = new JPanel();
		pnlTitle.setBackground(new Color(234, 230, 250));
		JLabel lblProductName = new JLabel(appUtils.truncateText(pd.getProduct().getProductName(), 10));
		lblProductName.setFont(new Font(lblProductName.getFont().getFontName(), Font.BOLD, 12));
		lblProductName.setForeground(new Color(107, 33, 168));
		
		pnlTitle.add(lblProductName);
		pnlCard.add(pnlTitle, gbc);
		
		gbc.gridy = 2;gbc.insets = new Insets(5, 0, 0, 0);
		JLabel lblSize = new JLabel("Size: " + pd.getSize());
		lblSize.setFont(new Font(lblSize.getFont().getFontName(), Font.BOLD, 12));
		pnlCard.add(lblSize, gbc);
		
		NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("vi-VN"));
		gbc.gridy = 3;gbc.insets = new Insets(5, 0, 0, 0);
		JLabel lblPrice = new JLabel("Giá: " + nf.format(pd.getProduct().getUnitPrice()));
		lblPrice.setFont(new Font(lblPrice.getFont().getFontName(), Font.BOLD, 12));
		pnlCard.add(lblPrice, gbc);
		
		
		gbc.gridy = 4;
		Box bAction = Box.createVerticalBox();
		JButton btnChoose = new JButton("Thêm vào giỏ");
		JButton btnDetail = new JButton("Xem chi tiết");
		
		btnChoose.addActionListener(e -> {
			
		});
		
		GridBagConstraints gbcDetail = new GridBagConstraints();
		btnDetail.addActionListener(e -> {
			JDialog diagDetailFrame = new JDialog();
			diagDetailFrame.setTitle("Chi tiết sản phẩm");
			diagDetailFrame.setLocationRelativeTo(null);
			diagDetailFrame.setSize(300, 200);
			
			
			JPanel pnlDetail = new JPanel(new GridBagLayout());
			
			gbcDetail.gridx = 0; gbcDetail.gridy = 0;
			gbcDetail.weightx = 1.0;gbcDetail.anchor = GridBagConstraints.WEST;
			gbcDetail.insets = new Insets(0, 20, 0, 0);

			Box bId = Box.createHorizontalBox();
			JLabel lblId = new JLabel("Mã sản phẩm: " + pd.getProductDetailId());
			lblId.setFont(new Font(lblId.getFont().getFontName(), Font.BOLD, 14));
			bId.add(lblId);
			pnlDetail.add(bId, gbcDetail);

			gbcDetail.gridy = 1;
			Box bFullName = Box.createHorizontalBox();
			JLabel lblFullName = new JLabel("Tên sản phẩm: " + pd.getProduct().getProductName());
			lblFullName.setFont(new Font(lblFullName.getFont().getFontName(), Font.BOLD, 14));
			bFullName.add(lblFullName);
			pnlDetail.add(bFullName, gbcDetail);

			gbcDetail.gridy = 2;
			Box bStock = Box.createHorizontalBox();
			JLabel lblStock = new JLabel("Số lượng tồn: " + pd.getStockQuantity());
			lblStock.setFont(new Font(lblStock.getFont().getFontName(), Font.BOLD, 14));
			bStock.add(lblStock);
			pnlDetail.add(bStock, gbcDetail);

			gbcDetail.gridy = 3;
			Box bColor = Box.createHorizontalBox();
			JLabel lblColor = new JLabel("Màu sản phẩm: " + pd.getColor());
			lblColor.setFont(new Font(lblColor.getFont().getFontName(), Font.BOLD, 14));
			bColor.add(lblColor);
			pnlDetail.add(bColor, gbcDetail);

			gbcDetail.gridy = 4;
			Box bCategory = Box.createHorizontalBox();
			JLabel lblCategory = new JLabel("Phân loại: " + pd.getProduct().getCategory().getCategoryName());
			lblCategory.setFont(new Font(lblCategory.getFont().getFontName(), Font.BOLD, 14));
			bCategory.add(lblCategory);
			pnlDetail.add(bCategory, gbcDetail);
			
			
			diagDetailFrame.add(pnlDetail);
			diagDetailFrame.setVisible(true);
		});
		
		bAction.add(btnChoose);
		bAction.add(Box.createVerticalStrut(2));
		bAction.add(btnDetail);
		bAction.add(Box.createVerticalStrut(5));
		
		pnlCard.add(bAction, gbc);
		
		return pnlCard;
	}
	
	/**
	 * 
	 * @param gbc: điều chỉnh tham số cho GridBag
	 * @param list: danh sách các sản phẩm đã được lọc, 
	 * nếu biến này null, mặc định sẽ lấy toàn bộ sản phẩm
	 */
	private void displayListProduct(ArrayList<ProductDetail> list)
	{
		pnlDisplayCard.removeAll();
		int row = 0;
		int col = 0;
		GridBagConstraints gbc = new GridBagConstraints();
		ArrayList<ProductDetail> listProduct = new ArrayList<ProductDetail>();
		listProduct = (list != null)? list : productDao.getAllProduct();
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.weightx = 0.0;
		gbc.weighty = 1.0;
		gbc.insets = new Insets(10, 10, 10, 10);
		for(ProductDetail pd : listProduct)
		{
			gbc.gridx = col;gbc.gridy = row;
			JPanel pnlCard = generateProductCard(pd);
			
			pnlDisplayCard.add(pnlCard, gbc);
			
			col++;
			
			if(col == 4)
			{
				col = 0;
				row += 1;
			}
		}
		pnlDisplayCard.revalidate();
		pnlDisplayCard.repaint();
	}

	public SaleProductUI() {
		this.setLayout(new BorderLayout());
		
		JPanel pnlHeader = new JPanel(new GridBagLayout());
		pnlHeader.setBackground(new Color(234, 230, 250));
		JLabel lblTitle = new JLabel("GIAO DIỆN BÁN HÀNG");
		lblTitle.setFont(new Font(lblTitle.getName(), Font.BOLD, 24));
		lblTitle.setForeground(new Color(94, 53, 165));
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.anchor = GridBagConstraints.WEST;
		gbc.weightx = 1;
		gbc.insets = new Insets(10, 10, 10, 10);
		pnlHeader.add(lblTitle, gbc);
		
		JPanel pnlManager = new JPanel(new GridBagLayout());
		
		JPanel pnlLeft = new JPanel(new BorderLayout());
		
		JPanel pnlFind = new JPanel();
		txtFind = new JTextField(40);
		btnFind = new JButton("Tìm theo tên");
		btnFind.setFont(new Font(btnFind.getFont().getFontName(), Font.BOLD, 12));
		btnResetFilter = new JButton("Làm mới toàn bộ");
		btnResetFilter.setFont(new Font(btnResetFilter.getFont().getFontName(), Font.BOLD, 12));
		pnlFind.add(txtFind);
		pnlFind.add(btnFind);
		pnlFind.add(btnResetFilter);
		
		pnlLeft.add(pnlFind, BorderLayout.NORTH);
		pnlDisplayCard = new JPanel(new GridBagLayout());
		
		JScrollPane pane = new JScrollPane(pnlDisplayCard);
		gbc.gridx = 0;gbc.gridy= 0;gbc.weightx = 0.6;gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		pnlLeft.add(pane, BorderLayout.CENTER);
		pnlManager.add(pnlLeft, gbc);
		
		JPanel pnlRight = new JPanel();
		JLabel lblRight = new JLabel("ben phai");
		pnlRight.add(lblRight);
		
		gbc.gridx = 1;gbc.gridy=0;gbc.weightx = 0.4;gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		pnlManager.add(pnlRight, gbc);
		this.add(pnlHeader, BorderLayout.NORTH);
		this.add(pnlManager, BorderLayout.CENTER);
		displayListProduct(null);
		triggerEvent();
	}
}