package gui_Default;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import dao.CustomerDAO;
import dao.EmployeeDao;
import dao.ProductDAO;
import dao.ProductDetailDAO;
import entity.Customer;
import entity.ProductDetail;
import utils.AppUtils;


public class SaleProductUI extends JPanel{
	private JTextField txtFind;
	private JButton btnFind;
	private AppUtils appUtils = new AppUtils();
	private ProductDAO productDao = new ProductDAO();
	private CustomerDAO customerDao = new CustomerDAO();
	private EmployeeDao employeeDao = new EmployeeDao();
	private JPanel pnlDisplayCard;
	private ProductDetailDAO productDetailDao = new ProductDetailDAO();
	private JButton btnResetFilter;
	private JButton btnPhone;
	private JTextField txtPhone;
	private JTextField txtName;
	private JTextField txtPoint;
	private DefaultTableModel modelTableCart;
	private JTable tableCart;
	private JDialog diagDetailFrame;
	private NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("vi-VN"));
	private JButton btnCancel;
	private JButton btnPayment;
	private JRadioButton radUsePoint;
	private JTextField txtAmount;
	private JTextField txtIsUsePoint;
	private JTextField txtDiscount;
	private JTextField txtPaid;
	private JTextField txtTake;
	private JTextField txtChange;
	private JComboBox<String> cboEmployee;
	
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
		
		btnPhone.addActionListener(e -> {
			String input = txtPhone.getText().trim();
			if(input == null || input.isEmpty())
				JOptionPane.showMessageDialog(this, "Chưa điền số điện thoại");
			else
			{
				Customer customer = findByPhone(input);
				txtName.setText(customer.getCustomerName());
				txtPoint.setText(Integer.toString(customer.getLoyaltyPoints()));
			}
		});
		
		txtPhone.addActionListener(e -> {
			String input = txtPhone.getText().trim();
			if(input == null || input.isEmpty())
				JOptionPane.showMessageDialog(this, "Chưa điền số điện thoại");
			else
			{
				Customer customer = findByPhone(input);
				txtName.setText(customer.getCustomerName());
				txtPoint.setText(Integer.toString(customer.getLoyaltyPoints()));
			}
		});
		
		radUsePoint.addActionListener(e -> {
			txtIsUsePoint.setText(radUsePoint.isSelected() ? txtPoint.getText() : "");
		});
		
	}
	
	private void findByName(String name)
	{
		ArrayList<ProductDetail> list = productDetailDao.findProduct(name);
		displayListProduct(list);
	}
	
	private Customer findByPhone(String phone)
	{
		try {
			ArrayList<Customer> list = customerDao.findCustomersByPhone(phone);
			Customer customer = list.getFirst();
			if(customer != null)
				return customer;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng này");
		}
		
		return null;
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
		
		gbc.gridy = 3;gbc.insets = new Insets(5, 0, 0, 0);
		JLabel lblPrice = new JLabel("Giá: " + nf.format(pd.getProduct().getUnitPrice()));
		lblPrice.setFont(new Font(lblPrice.getFont().getFontName(), Font.BOLD, 12));
		pnlCard.add(lblPrice, gbc);
		
		
		gbc.gridy = 4;
		Box bAction = Box.createVerticalBox();
		JButton btnChoose = new JButton("Thêm vào giỏ");
		JButton btnDetail = new JButton("Xem chi tiết");
		
		btnChoose.addActionListener(e -> {
			if(txtPhone.getText().isBlank())
			{
				JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng để tính tiền!");
				txtPhone.requestFocus();
			}
			else
			{
				addToCart(pd);
				double price = 0.0;
				double prePrice = 0.0;
				try {
					prePrice = txtAmount.getText().isBlank()? 0.0 : nf.parse(txtAmount.getText()).doubleValue();
				} catch (ParseException e1) {
					return;
				}
				
				price += prePrice + pd.getProduct().getUnitPrice();
				
				txtAmount.setText(nf.format(price));
			}
		});
		
		GridBagConstraints gbcDetail = new GridBagConstraints();
		btnDetail.addActionListener(e -> {
			showDialog(gbcDetail, pd);
		});
		
		bAction.add(btnChoose);
		bAction.add(Box.createVerticalStrut(2));
		bAction.add(btnDetail);
		bAction.add(Box.createVerticalStrut(5));
		
		pnlCard.add(bAction, gbc);
		
		return pnlCard;
	}
	
	private void addToCart(ProductDetail pd) {
		int totalRow = modelTableCart.getRowCount();
		boolean isExist = false;
		
		if(totalRow == 0)
		{
			modelTableCart.addRow(new Object[] {
					pd.getProduct().getProductName(),
					pd.getSize(),
					1,
					nf.format(pd.getProduct().getUnitPrice()), 
					nf.format(pd.getProduct().getUnitPrice())
			});
			return;
		}
		for(int i = 0;i < tableCart.getRowCount();i++)
		{
			if(
				modelTableCart.getValueAt(i, 0).equals(pd.getProduct().getProductName())
				&& modelTableCart.getValueAt(i, 1).equals(pd.getSize())
			)
			{
				modelTableCart.setValueAt(
						Integer.parseInt(modelTableCart.getValueAt(i, 2).toString()) + 1, i, 2
				);
				modelTableCart.setValueAt(
						nf.format(
							Double.parseDouble(modelTableCart.getValueAt(i, 2).toString()) 
							* pd.getProduct().getUnitPrice()
						)
						, i, 4
				);
				
				isExist = true;
			}
		}
		if(!isExist)
		{
			modelTableCart.addRow(new Object[] {
					pd.getProduct().getProductName(),
					pd.getSize(),
					1,
					nf.format(pd.getProduct().getUnitPrice()), 
					nf.format(pd.getProduct().getUnitPrice())
			});
			return;
		}
	}

	private void showDialog(GridBagConstraints gbcDetail, ProductDetail pd) {
		diagDetailFrame = new JDialog();
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
		
		gbcDetail.gridy = 5;
		gbcDetail.insets = new Insets(30, 0, 0, 0);
		gbcDetail.anchor = GridBagConstraints.CENTER;
		
		JButton btnCloseDiag = new JButton("OK");
		btnCloseDiag.addActionListener(e -> {
			diagDetailFrame.setVisible(false);
		});
		Box bClose = Box.createHorizontalBox();
		bClose.add(btnCloseDiag);
		
		pnlDetail.add(bClose, gbcDetail);
		
		diagDetailFrame.add(pnlDetail);
		diagDetailFrame.setVisible(true);
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

	private void addToEmployeeCombobox()
	{
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		for(Object[] obj : list)
		{
			String employee = (String) obj[0];
			System.out.println(employee);
			cboEmployee.addItem(employee);
		}
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
		pnlManager.setBackground(Color.white);
		
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
		gbc.gridx = 0;gbc.gridy= 0;gbc.weightx = 0.8;gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		pane.getVerticalScrollBar().setUnitIncrement(25);
		
		pnlLeft.add(pane, BorderLayout.CENTER);
		pnlManager.add(pnlLeft, gbc);
		
		JPanel pnlRight = new JPanel(new GridBagLayout());
		GridBagConstraints gbcRight = new GridBagConstraints();
		
		JPanel pnlTop = new JPanel(new BorderLayout());
		pnlTop.setBackground(new Color(245, 243, 250));
		
		
		pnlTop.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 10));
		JPanel pnlSearch = new JPanel(new BorderLayout());
		pnlSearch.setBackground(new Color(245, 243, 250));
		pnlSearch.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
		JLabel lblPhone = new JLabel("SĐT");
		txtPhone = new JTextField(12);
		btnPhone = new JButton("Tìm");

		JPanel pnlSearchPhone = new JPanel();
		pnlSearchPhone.setOpaque(false);
		pnlSearchPhone.add(lblPhone);
		pnlSearchPhone.add(txtPhone);
		pnlSearchPhone.add(btnPhone);
	
		pnlSearch.add(pnlSearchPhone, BorderLayout.WEST);
		
		cboEmployee = new JComboBox<String>();

		addToEmployeeCombobox();
		pnlSearch.add(cboEmployee, BorderLayout.EAST);


		pnlTop.add(pnlSearch, BorderLayout.NORTH);
		
		JPanel pnlInfo = new JPanel(new BorderLayout());

		
		pnlInfo.setOpaque(false);

		JLabel lblCustomerInfo = new JLabel("THÔNG TIN KHÁCH HÀNG");
		lblCustomerInfo.setForeground(new Color(94, 53, 165));
		lblCustomerInfo.setFont(new Font(lblCustomerInfo.getFont().getName(), Font.BOLD, 12));
		lblCustomerInfo.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
		
		pnlInfo.add(lblCustomerInfo, BorderLayout.NORTH);
		
		JPanel pnlName = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlName.setOpaque(false);
		JLabel lblName = new JLabel("Tên");
		txtName = new JTextField(12);
		txtName.setEnabled(false);
		pnlName.add(lblName);
		pnlName.add(txtName);
		
		pnlInfo.add(pnlName, BorderLayout.CENTER);
		
		JPanel pnlPoint = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlPoint.setOpaque(false);
		JLabel lblPoint = new JLabel("Điểm tích lũy");
		lblName.setPreferredSize(new Dimension(80, 13));
		lblPoint.setPreferredSize(new Dimension(80, 13));
		txtPoint = new JTextField(12);
		txtPoint.setEnabled(false);
		pnlPoint.add(lblPoint);
		pnlPoint.add(txtPoint);
		
		pnlInfo.add(pnlPoint, BorderLayout.SOUTH);
		
		pnlTop.add(pnlInfo, BorderLayout.CENTER);
		
		
		gbcRight.gridx = 0;
		gbcRight.gridy = 0;
		gbcRight.weightx = 1.0;
		gbcRight.weighty = 0.25;
		gbcRight.fill = GridBagConstraints.BOTH;
		
		
		pnlRight.add(pnlTop, gbcRight);
		
		JPanel pnlMiddle = new JPanel(new BorderLayout());
		pnlMiddle.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		pnlMiddle.setBackground(new Color(245, 243, 250));
		
		
		JLabel lblCart = new JLabel("GIỎ HÀNG", JLabel.LEFT);
		lblCart.setForeground(new Color(94, 53, 165));
		
		
		lblCart.setFont(new Font(lblCart.getFont().getName(), Font.BOLD, 12));
		
		lblCart.setBorder(BorderFactory.createEmptyBorder(0, 0, 7, 0));
		
		pnlMiddle.add(lblCart, BorderLayout.NORTH);
		
		String cols[] = {"Tên giày", "Size", "Số lượng", "Đơn giá", "Thành tiền"};
		modelTableCart = new DefaultTableModel(cols, 0);
		tableCart = new JTable(modelTableCart);
		JScrollPane paneCart = new JScrollPane(tableCart);
		paneCart.setMinimumSize(new Dimension(0, 100));
		pnlMiddle.add(paneCart, BorderLayout.CENTER);
		
		gbcRight.gridx = 0;
		gbcRight.gridy = 1;
		gbcRight.weightx = 1.0;
		gbcRight.weighty = 0.25;
		gbcRight.insets = new Insets(5, 0, 0, 0);
		
		pnlRight.add(pnlMiddle, gbcRight);
		
		JPanel pnlBottom = new JPanel(new BorderLayout());
		JPanel pnlTitle = new JPanel(new BorderLayout());
		JLabel lblDetailCheckout = new JLabel("CHI TIẾT THANH TOÁN");
		pnlTitle.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 0));
		radUsePoint = new JRadioButton("Dùng điểm tích lũy");
		
		lblDetailCheckout.setForeground(new Color(94, 53, 165));
		pnlTitle.setOpaque(false);
		radUsePoint.setBackground(new Color(245, 243, 250));
		radUsePoint.setForeground(new Color(157, 78, 221));
		radUsePoint.setFont(new Font(radUsePoint.getFont().getName(), Font.BOLD, 12));
		
		pnlBottom.setBackground(new Color(245, 243, 250));
		
		lblDetailCheckout.setFont(new Font(lblDetailCheckout.getFont().getName(), Font.BOLD, 12));
		lblDetailCheckout.setBorder(BorderFactory.createEmptyBorder(0, 0, 3, 0));
		
		pnlTitle.add(lblDetailCheckout, BorderLayout.WEST);
		pnlTitle.add(radUsePoint, BorderLayout.EAST);
		
		pnlBottom.add(pnlTitle, BorderLayout.NORTH);
		
		JPanel pnlInfoCheckout = new JPanel(new GridLayout(6, 2, 5, 5));
		
		pnlInfoCheckout.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		pnlInfoCheckout.setOpaque(false);
		
		JLabel lblAmount = new JLabel("Tổng tiền giày: ");
		JLabel lblIsUsePoint = new JLabel("Dùng điểm: ");
		JLabel lblDiscount = new JLabel("Giá đã giảm: ");
		JLabel lblPaid = new JLabel("Phải trả: ");
		JLabel lblTake = new JLabel("Khách đưa: ");
		JLabel lblChange = new JLabel("Tiền thừa: ");
	
		txtAmount = new JTextField(15);
		txtAmount.setEnabled(false);
		txtIsUsePoint = new JTextField(15);
		txtIsUsePoint.setEnabled(false);
		txtDiscount = new JTextField(15);
		txtDiscount.setEnabled(false);
		txtPaid = new JTextField(15);
		txtPaid.setEnabled(false);
		txtTake = new JTextField(15);
		txtChange = new JTextField(15);
		txtChange.setEnabled(false);
		
		JLabel[] jlFillColor = {
				lblPhone, lblName, lblPoint,
				lblAmount, lblIsUsePoint, lblDiscount, lblPaid, lblTake, lblChange
		};
		
		for(JLabel jl : jlFillColor)
		{
			jl.setForeground(Color.BLACK);
			jl.setFont(new Font(jl.getFont().getName(), Font.BOLD, 12));
		}
		
		pnlInfoCheckout.add(lblAmount);
		pnlInfoCheckout.add(txtAmount);

		pnlInfoCheckout.add(lblIsUsePoint);
		pnlInfoCheckout.add(txtIsUsePoint);

		pnlInfoCheckout.add(lblDiscount);
		pnlInfoCheckout.add(txtDiscount);

		pnlInfoCheckout.add(lblPaid);
		pnlInfoCheckout.add(txtPaid);

		pnlInfoCheckout.add(lblTake);
		pnlInfoCheckout.add(txtTake);

		pnlInfoCheckout.add(lblChange);
		pnlInfoCheckout.add(txtChange);
		
		pnlBottom.add(pnlInfoCheckout, BorderLayout.CENTER);
		
		JPanel pnlPrintBill = new JPanel(new FlowLayout());
		pnlPrintBill.setOpaque(false);
		btnPayment = new JButton("THANH TOÁN");
		btnPayment.setFont(new Font(btnPayment.getFont().getName(), Font.BOLD, 12));
		btnPayment.setForeground(new Color(157, 78, 221));
		
		btnCancel = new JButton("HỦY THANH TOÁN");
		btnCancel.setFont(new Font(btnCancel.getFont().getName(), Font.BOLD, 12));
		btnCancel.setOpaque(false);
		btnCancel.setForeground(new Color(192, 160, 224));
		
		pnlPrintBill.add(btnPayment);
		pnlPrintBill.add(btnCancel);
		
		pnlBottom.add(pnlPrintBill, BorderLayout.SOUTH);
		
		gbcRight.gridx = 0;
		gbcRight.gridy = 2;
		gbcRight.weightx = 1.0;
		gbcRight.weighty = 0.5;
		gbcRight.insets = new Insets(5, 0, 0, 0);
		
		pnlRight.add(pnlBottom, gbcRight);
		
		
		gbc.gridx = 1;gbc.gridy=0;gbc.weightx = 0.2;gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		pnlManager.add(pnlRight, gbc);
		this.add(pnlHeader, BorderLayout.NORTH);
		this.add(pnlManager, BorderLayout.CENTER);
		displayListProduct(null);
		triggerEvent();
	}
}