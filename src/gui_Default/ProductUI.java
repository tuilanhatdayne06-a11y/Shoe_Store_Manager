package gui_Default;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import dao.CategoryDAO;
import dao.ProductDAO;

import dao.ProductDetailDAO;
import dao.SupplierDAO;
import entity.Category;
import entity.Product;
import entity.ProductDetail;
import entity.Supplier;
import utils.AppUtils;
import utils.ImageUtil;

@SuppressWarnings("unused")
public class ProductUI extends JPanel {
	private JTextField txtProductCode;
	private JTextField txtProductName;
	private JTextField txtProductSize;
	private JTextField txtProductColor;
	private JComboBox<String> cboProductCategory;
	private JTextField txtProductStock;
	private JTextField txtProductCost;
	private JComboBox<String> cboProductSupplier;
	private JButton btnAddProduct;
	private JButton btnRemoveProduct;
	private JButton btnEditProduct;
	private JTextField txtFind;
	private JButton btnFind;
	private DefaultTableModel modelTable;
	private JTable table;
	private JScrollPane pane;
	private boolean isSelectedEdit = false; //biến này có thể bỏ qua nếu thay đổi thiết kế

	private JScrollPane paneProductCard;
	private Box bCard;
	private JButton btnBrowseImg;
	private File selectedImg;
	private JLabel lblDefaultImg;
	private JPanel pnlImage;
	private Box bImage;
	private JLabel lblEmptyProduct;
	
	private CategoryDAO categoryDAO = new CategoryDAO();
	private SupplierDAO supplierDAO = new SupplierDAO();
	private AppUtils appUtils = new AppUtils();
	private ProductDAO productDAO = new ProductDAO();
	private ProductDetailDAO productDetailDAO = new ProductDetailDAO();

	private ImageUtil imgUtil = new ImageUtil();
	private JButton btnResetTable;
	private JButton btnClearForm;

	private void addToComboBox() {
		try {
			List<String> list = categoryDAO.getCategoryName();
			List<String> listSupplier = supplierDAO.getSupplierName();

			for (String item : list)
				cboProductCategory.addItem(item);
			for (String supplier : listSupplier)
				cboProductSupplier.addItem(supplier);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Không thể tải dữ liệu danh mục/nhà cung cấp: " + e.getMessage());
		}
	}

	
	private void addToModelTable(ArrayList<ProductDetail> l) {
		ArrayList<ProductDetail> list = (l == null)? productDAO.getAllProduct() : l;
		modelTable.setRowCount(0);
		for (ProductDetail ll : list) {
			modelTable.addRow(new Object[] {
					ll.getProductDetailId(),
					ll.getProduct().getProductName(),
					ll.getSize(),
					ll.getColor(),
					ll.getProduct().getCategory().getCategoryName(),
					ll.getStockQuantity(),
					ll.getProduct().getUnitPrice(),
					ll.getProduct().getSupplier().getSupplierName(),
					ll.getImgProductColor()
			});
		}
	}

	/**
	 * 
	 * @param value: truyền vào các giá trị đã có trong combobox để kiểm tra
	 * @return vị trí của phần tử tìm thấy trong combobox
	 */
	public int getSelectedComboboxIndex(JComboBox<String> cbo, String value) {
		int output = 0;
		for (int i = 0; i < cbo.getItemCount(); i++) {
			if (cbo.getItemAt(i).equals(value))
				output = i;
		}

		return output;
	}
	
	private void resetForm()
	{
		txtProductCode.setText("");
		txtProductName.setText("");
		txtProductSize.setText("");
		txtProductColor.setText("");
		cboProductCategory.setSelectedIndex(0);
		txtProductStock.setText("");
		txtProductCost.setText("");
		cboProductSupplier.setSelectedIndex(0);
		lblDefaultImg.setText("");
		lblDefaultImg.setIcon(null);
	}

	/**
	 * 
	 * @param i biến để xác định dòng nào trên modelTable
	 * @param isClickedModel nếu là true thì trả về dòng được chọn trên model,
	 * còn false thì sẽ lấy i làm dòng được chọn
	 */
	private void handleClicked(int i, boolean isClickedModel)
	{
		int sltR = 0;
		if(isClickedModel)
			sltR = table.getSelectedRow();
		else
			sltR = i;
		int foundElementCboCategory = getSelectedComboboxIndex(
				cboProductCategory,
				modelTable.getValueAt(sltR, 4).toString());
		int foundElementCboSupplier = getSelectedComboboxIndex(
				cboProductSupplier,
				modelTable.getValueAt(sltR, 7).toString());
		
		
		txtProductCode.setText(modelTable.getValueAt(sltR, 0).toString());
		txtProductName.setText(modelTable.getValueAt(sltR, 1).toString());
		txtProductSize.setText(modelTable.getValueAt(sltR, 2).toString());
		txtProductColor.setText(modelTable.getValueAt(sltR, 3).toString());
		cboProductCategory.setSelectedIndex(foundElementCboCategory);
		txtProductStock.setText(modelTable.getValueAt(sltR, 5).toString());
		txtProductCost.setText(modelTable.getValueAt(sltR, 6).toString());
		cboProductSupplier.setSelectedIndex(foundElementCboSupplier);
		txtProductCost.setEnabled(false);

		String imgName = modelTable.getValueAt(sltR, 8).toString();
		String path = "src/img/" + imgName;
		
		try {
			ImageIcon icon = new ImageIcon(path);
			selectedImg = new File(path);
			Image scale = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
			
			lblDefaultImg.setIcon(new ImageIcon(scale));
			lblDefaultImg.setText("");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void findByProductName()
	{
		String input = txtFind.getText().trim();
		if(input.equals(""))
		{
			JOptionPane.showMessageDialog(this, "Chưa chọn sản phẩm để tìm");
			return;
		}
		
		 ArrayList<ProductDetail> listFoundDetail = productDetailDAO.findProduct(input);
		 
		 if(listFoundDetail != null)
			 addToModelTable(listFoundDetail);
		 else
			 JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm tương tự");
	}

	
	private boolean validData()
	{
		String name = txtProductName.getText().trim();
		String color = txtProductColor.getText().trim();
		
		if(name.length() <= 0)
		{
			JOptionPane.showMessageDialog(this, "Chưa nhập tên giày/dép");
			txtProductName.requestFocus();
			return false;
		}
		
		if(txtProductSize.getText().trim().equals(""))
		{
			JOptionPane.showMessageDialog(this, "Chưa nhập kích cỡ");
			txtProductSize.requestFocus();
			return false;
		}
		try {
			int size = Integer.parseInt(txtProductSize.getText());
			if(!(size >= 36 && size <= 46))
			{
				JOptionPane.showMessageDialog(this, "Kích cỡ phải nằm trong khoảng 36 và 46");
				txtProductSize.requestFocus();
				return false;
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Kích cỡ phải là số");
			txtProductSize.requestFocus();
			return false;
		}
		
		if(color.length() <= 0)
		{
			JOptionPane.showMessageDialog(this, "Chưa nhập màu");
			txtProductColor.requestFocus();
			return false;
		}
		
		if(txtProductStock.getText().trim().equals(""))
		{
			JOptionPane.showMessageDialog(this, "Chưa nhập số lượng tồn");
			txtProductStock.requestFocus();
			return false;
		}
		try {
			int stock = Integer.parseInt(txtProductStock.getText());
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Số lượng tồn phải là số");
			return false;
		}
		
		if(txtProductCost.getText().trim().equals(""))
		{
			JOptionPane.showMessageDialog(this, "Chưa nhập giá bán");
			txtProductCost.requestFocus();
			return false;
		}
		try {
			double price = Double.parseDouble(txtProductCost.getText());
			if(!(price > 0))
			{
				JOptionPane.showMessageDialog(this, "Giá phải lớn hơn 0");
				txtProductCost.requestFocus();
				return false;
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Giá giày/dép phải là số");
			txtProductCost.requestFocus();
			return false;
		}
		
		if(selectedImg == null)
		{
			JOptionPane.showMessageDialog(this, "Chưa chọn ảnh");
			return false;
		}
		
		return true;
	}
	
	private void triggerEventProduct() {
		
		btnAddProduct.addActionListener(e -> {
			if(validData())
			{
				String name = txtProductName.getText().trim();
				String size = txtProductSize.getText().trim();
				String color = txtProductColor.getText().trim();
				String categoryName = (String) cboProductCategory.getSelectedItem();
				int stock = Integer.parseInt(txtProductStock.getText().trim());
				String cost = txtProductCost.getText().trim();
				String supplierName = (String) cboProductSupplier.getSelectedItem();
				String imgName = selectedImg.getName();
				boolean isDuplicated = false; // dùng để kiểm tra xem có bị trùng biến thể hay không
				boolean isExistProduct = false;
				ImageUtil util = new ImageUtil();
				util.saveImg(selectedImg);
				
				
				for(int i = 0;i < modelTable.getRowCount();i++)
				{
					if(table.getValueAt(i, 1).equals(name)
							&& table.getValueAt(i, 4).equals(categoryName))
					{
						isExistProduct = true;
						if(table.getValueAt(i, 2).toString().equals(size) && table.getValueAt(i, 3).equals(color))
						{
							table.setValueAt(Integer.parseInt(table.getValueAt(i, 5).toString()) + stock, i, 5);
							productDAO.updateStockQuantity(
									Integer.parseInt(table.getValueAt(i, 5).toString()),
									name, Integer.parseInt(size), color, categoryName);
							isDuplicated = true;
							break;
						}
					}
				}
				
				
				if(!isDuplicated)
				{
					Category category = categoryDAO.getCategoryByName(categoryName);
					Supplier supplier = supplierDAO.getSupplierByName(supplierName);
					Product product = null;
					
					if(!isExistProduct)
					{
						String productId = appUtils.autoGenerateId("SP");
						product = new Product(productId, name, Double.parseDouble(cost), 
								category, supplier);
						productDAO.addProduct(product);
					}
					else 
						product = productDAO.getProduct(name, categoryName, supplierName);
					String productDetailId = appUtils.autoGenerateId("PD");
					if(productDetailId == null) return;
					
					ProductDetail productDetail = new ProductDetail(
							productDetailId, Integer.parseInt(size), color, stock, product ,imgName
					);
					
					
					productDetailDAO.addProductDetail(product, productDetail);
					modelTable.addRow(new Object[] { productDetailId, name, size, color, categoryName, stock, cost, supplierName, imgName });
				}
				
				bCard.removeAll();
				displayProductCardList();
				
				bCard.revalidate();
				bCard.repaint();
				resetForm();
			}
		});
		
		
		btnEditProduct.addActionListener(e -> {
			Component setEditFalse[] = {
					txtProductCode,
					txtProductName,
					txtProductSize,
					txtProductColor,
					cboProductCategory,
					txtProductStock,
					cboProductSupplier
			};
			
			int sltR = table.getSelectedRow();
			if(sltR == -1)
			{
				JOptionPane.showMessageDialog(this, "Chưa có sản phẩm nào được chọn!");
				return;
			}
			if(!isSelectedEdit)
			{
				if(JOptionPane.showConfirmDialog(this, "Thông Báo", "Có muốn sửa giá sản phẩm này?"
						,JOptionPane.YES_NO_OPTION
				) == JOptionPane.YES_OPTION)
				{
					isSelectedEdit = true;
					txtProductCost.setEnabled(true);
					for(Component cpm : setEditFalse)
					{
						cpm.setEnabled(false);
					}
				}
			}
			
			String productName = txtProductName.getText().trim();
			Category category = categoryDAO
					.getCategoryByName(cboProductCategory.getSelectedItem().toString());
			Supplier supplier = supplierDAO
					.getSupplierByName(cboProductSupplier.getSelectedItem().toString());
			double price = Double.parseDouble(txtProductCost.getText().trim());
			for(int i = 0;i < modelTable.getRowCount();i++)
			{
				if(table.getValueAt(i, 1).toString().equals(productName)
					&& table.getValueAt(i, 4).toString().equals(category.getCategoryName())
					&& table.getValueAt(i, 7).toString().equals(supplier.getSupplierName())
				)
				{
					table.setValueAt(price, i, 6);
				}
			}
			bCard.removeAll();
			displayProductCardList();
			
			bCard.revalidate();
			bCard.repaint();

			productDAO.updateUnitPrice(productName, 
					category.getCategoryId(), supplier.getSupplierId(), price);

		});

		btnRemoveProduct.addActionListener(e -> {
			int[] sltR = table.getSelectedRows();
			
			if (sltR.length == 0)
				JOptionPane.showMessageDialog(this, "Chưa có sản phẩm nào được chọn để xóa, vui lòng chọn!");
			else if (JOptionPane.showConfirmDialog(this, "Cảnh báo",
					(sltR.length == 1) ? "Có chắc muốn xóa sản phẩm này không?"
							:
							"Có chắc muốn xóa" + " " + sltR.length + " sản phẩm này?",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				for (int i = sltR.length - 1; i >= 0; i--) {
					String removeId = table.getValueAt(sltR[i], 0).toString();

					if(productDetailDAO.removeProductDetail(removeId))	
						modelTable.removeRow(sltR[i]);
				}
			}
			
			bCard.removeAll();
			displayProductCardList();
			
			bCard.revalidate();
			bCard.repaint();
		});
		
		txtFind.addActionListener(e -> {
			findByProductName();
		});
		
		btnFind.addActionListener(e -> {
			findByProductName();
		});
		
		btnResetTable.addActionListener(e -> {
			txtFind.setText("");
			addToModelTable(null);
		});
		
		btnClearForm.addActionListener(e -> {
			resetForm();
		});

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtProductName.setEnabled(true);
				txtProductSize.setEnabled(true);
				txtProductColor.setEnabled(true);
				cboProductCategory.setEnabled(true);
				txtProductStock.setEnabled(true);
				cboProductSupplier.setEnabled(true);
				
				handleClicked(0, true);
			}
		});
		
		btnBrowseImg.addActionListener(e -> {
			selectedImg = imgUtil.browseImg();
			if(selectedImg != null)
			{
				ImageIcon originalImg = new ImageIcon(selectedImg.getAbsolutePath());
				
				Image img = originalImg.getImage().getScaledInstance(pnlImage.getPreferredSize().width,
						pnlImage.getPreferredSize().height, Image.SCALE_SMOOTH);
				lblDefaultImg.setIcon(new ImageIcon(img));
				lblDefaultImg.setText("");
			}
		});
	}
	
	
	// PHẦN UI Ở DƯỚI, CÁC PHẦN XỬ LÝ LOGIC Ở TRÊN
	

	
	private JPanel generateProductCard(String pdId, String imgName, String productName, String stock, String price)
	{
		JPanel pnlCard = new JPanel(new GridBagLayout());
		pnlCard.setPreferredSize(new Dimension(140, 150));
		pnlCard.setBackground(new Color(245, 243, 250));
		final String PATH = "src/img/" + imgName;

		
		JPanel pnlImgCard = new JPanel();
		JLabel lblImg = new JLabel();
		
		pnlImgCard.setPreferredSize(new Dimension(70, 70));
		try {
			ImageIcon icon = new ImageIcon(PATH);
			Image scale = icon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
			lblImg.setIcon(new ImageIcon(scale));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		lblImg.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				for(int i = 0;i < modelTable.getRowCount();i++)
				{
					if(table.getValueAt(i, 0).equals(pdId))
					{
						handleClicked(i, false);
					}
				}
			}
		});
		
		pnlImgCard.add(lblImg);
		
		JPanel pnlProductName = new JPanel();
		pnlProductName.setBackground(new Color(230, 216, 255));
		
	
		JLabel lblName = new JLabel(appUtils.truncateText(productName, 15));
		lblName.setForeground(new Color(107, 33, 168));
		lblName.setFont(new Font(lblName.getName(), Font.BOLD, 11));
		pnlProductName.add(lblName);
		
		JPanel pnlStock = new JPanel();
		pnlStock.setOpaque(false);
		JLabel lblStock = new JLabel("Kho: " + stock);
		lblStock.setForeground(new Color(139, 62, 246));
		lblStock.setFont(new Font(lblStock.getName(), Font.BOLD, lblStock.getFont().getSize()));
		pnlStock.add(lblStock);
		
		
		JPanel pnlCost = new JPanel();
		pnlCost.setOpaque(false);
		JLabel lblCost = new JLabel("Giá: " + price + "đ");
		lblCost.setFont(new Font(lblCost.getName(), Font.BOLD, lblCost.getFont().getSize()));
		lblCost.setForeground(new Color(30, 27, 75));
		pnlCost.add(lblCost);
		
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.insets = new Insets(0, 0, 10, 0);
		
		gbc.gridx = 0;gbc.gridy = 0;
		pnlCard.add(pnlImgCard, gbc);
		
		gbc.gridx = 0;gbc.gridy = 1;
		pnlCard.add(pnlProductName, gbc);
		
		gbc.insets = new Insets(0, 0, 0, 0);
		
		gbc.gridx = 0;gbc.gridy = 2;
		pnlCard.add(pnlStock, gbc);
		
		gbc.insets = new Insets(0, 0, 10, 0);
		
		gbc.gridx = 0;gbc.gridy = 3;
		pnlCard.add(pnlCost, gbc);
		
		return pnlCard;
	}
	
	private void displayProductCardList()
	{
		if (modelTable.getRowCount() == 0) 
			lblEmptyProduct.setText("Không còn sản phẩm nào để hiển thị");
		else
			for(int i = 0;i < modelTable.getRowCount();i++)
			{
				bCard.add(generateProductCard(
						table.getValueAt(i, 0).toString(),
						table.getValueAt(i, 8).toString(),
						table.getValueAt(i, 1).toString(),
						table.getValueAt(i, 5).toString(),
						table.getValueAt(i, 6).toString()
				));
				bCard.add(Box.createHorizontalStrut(15));
			}
	}
	
	public ProductUI() {
		this.setLayout(new BorderLayout());

		JPanel pnlManagerProduct = new JPanel();
		pnlManagerProduct.setLayout(new BorderLayout());

		JPanel pnlHeader = new JPanel();
		pnlHeader.setBackground(new Color(234, 230, 250));
		pnlHeader.setLayout(new BorderLayout());

		JLabel lblTitle = new JLabel("Quản lý sản phẩm", JLabel.LEFT);
		lblTitle.setFont(new Font(lblTitle.getName(), Font.BOLD, 24));
		lblTitle.setForeground(new Color(94, 53, 165));

		try {
			ImageIcon icon = new ImageIcon(getClass().getResource("/img/beyono.png"));//Thay ảnh sao cho phù hợp 
			Image scaleIcon = icon.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
			lblTitle.setIcon(new ImageIcon(scaleIcon));
		} catch (Exception e) {
			e.printStackTrace();
		}

		lblTitle.setHorizontalTextPosition(SwingConstants.RIGHT);
		lblTitle.setVerticalTextPosition(SwingConstants.CENTER);
		lblTitle.setHorizontalAlignment(SwingConstants.LEFT);
		lblTitle.setVerticalAlignment(SwingConstants.CENTER);

		pnlHeader.add(lblTitle);

		JPanel pnlDisplayProduct = new JPanel();
		pnlDisplayProduct.setBackground(new Color(248, 247, 255));
		pnlDisplayProduct.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;

		JPanel pnlProductInformation = new JPanel();
		pnlProductInformation.setLayout(new BorderLayout());
		pnlProductInformation.setPreferredSize(new Dimension(400, 400));

		JPanel pnlFindProduct = new JPanel();
		pnlFindProduct.setLayout(new FlowLayout());
		txtFind = new JTextField(30);
		btnFind = new JButton("Tìm theo tên sản phẩm");
		btnResetTable = new JButton("Làm mới bảng");

		txtFind.setFont(new Font(txtFind.getName(), Font.BOLD, 12));
		btnFind.setFont(new Font(btnFind.getName(), Font.BOLD, 12));
		btnResetTable.setFont(new Font(btnResetTable.getName(), Font.BOLD, 12));

		pnlFindProduct.add(txtFind);
		pnlFindProduct.add(btnFind);
		pnlFindProduct.add(btnResetTable);

		String[] cols = { "Mã giày", "Tên giày", "Kích cỡ", "Màu sắc", "Phân Loại", "Số lượng tồn", "Đơn giá bán",
				"Nhà cung cấp", "Ảnh" };
		modelTable = new DefaultTableModel(cols, 0);
		table = new JTable(modelTable);
		table.getTableHeader().setFont(new Font(table.getName(), Font.PLAIN, 11));
		table.setFont(new Font(table.getName(), Font.PLAIN, 11));
		pane = new JScrollPane(table);
		addToModelTable(null);
		
		bCard = Box.createHorizontalBox();
		JPanel pnlEmpty = new JPanel(new GridBagLayout());
		
		gbc.anchor = GridBagConstraints.CENTER;
		lblEmptyProduct = new JLabel();
		lblEmptyProduct.setForeground(Color.red);
		lblEmptyProduct.setFont(new Font(lblEmptyProduct.getName(), Font.BOLD, 30));
		pnlEmpty.add(lblEmptyProduct, gbc);
		
		bCard.add(pnlEmpty);
		displayProductCardList();
		paneProductCard = new JScrollPane(bCard);
		
		paneProductCard.setPreferredSize(new Dimension(200, 200));

		pnlProductInformation.add(pnlFindProduct, BorderLayout.NORTH);
		pnlProductInformation.add(pane, BorderLayout.CENTER);
		// tạo margin-top
		paneProductCard.setBorder(
			   BorderFactory.createEmptyBorder(10, 0, 0, 0)
		);
		pnlProductInformation.add(paneProductCard, BorderLayout.SOUTH);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0.9;
		gbc.insets = new Insets(0, 20, 0, 0);
		pnlDisplayProduct.add(pnlProductInformation, gbc);

		JPanel pnlProductInput = new JPanel();
		gbc.gridx = 1;
		gbc.weightx = 0.1;
		pnlDisplayProduct.add(pnlProductInput, gbc);

		pnlProductInput.setLayout(new GridBagLayout());
		pnlProductInput.setBackground(new Color(230,216,255));
		
		JLabel lblProductCode = new JLabel("Mã giày");
		lblProductCode.setFont(new Font(lblProductCode.getName(), Font.BOLD, 12));
		txtProductCode = new JTextField(12);
		txtProductCode.setFont(new Font(txtProductCode.getName(), Font.BOLD, 12));
		txtProductCode.setEnabled(false);

		JLabel lblProductName = new JLabel("Tên giày");
		lblProductName.setFont(new Font(lblProductName.getName(), Font.BOLD, 12));
		txtProductName = new JTextField(12);
		txtProductName.setFont(new Font(txtProductName.getName(), Font.BOLD, 12));

		JLabel lblProductSize = new JLabel("Kích cỡ");
		lblProductSize.setFont(new Font(lblProductSize.getName(), Font.BOLD, 12));
		txtProductSize = new JTextField(12);
		txtProductSize.setFont(new Font(txtProductSize.getName(), Font.BOLD, 12));

		JLabel lblProductColor = new JLabel("Màu sắc");
		lblProductColor.setFont(new Font(lblProductColor.getName(), Font.BOLD, 12));
		txtProductColor = new JTextField(12);
		txtProductColor.setFont(new Font(txtProductColor.getName(), Font.BOLD, 12));

		JLabel lblProductCategory = new JLabel("Phân loại");
		lblProductCategory.setFont(new Font(lblProductCategory.getName(), Font.BOLD, 12));
		cboProductCategory = new JComboBox<String>();
		cboProductCategory.setFont(new Font(cboProductCategory.getName(), Font.BOLD, 12));
		cboProductCategory.setMaximumRowCount(3);

		JLabel lblProductStock = new JLabel("Số lượng tồn");
		lblProductStock.setFont(new Font(lblProductStock.getName(), Font.BOLD, 12));
		txtProductStock = new JTextField(12);
		txtProductStock.setFont(new Font(txtProductStock.getName(), Font.BOLD, 12));

		JLabel lblProductCost = new JLabel("Đơn giá bán");
		lblProductCost.setFont(new Font(lblProductCost.getName(), Font.BOLD, 12));
		txtProductCost = new JTextField(12);
		txtProductCost.setFont(new Font(txtProductCost.getName(), Font.BOLD, 12));

		JLabel lblProductSupplier = new JLabel("Tên nhà cung cấp");
		lblProductSupplier.setFont(new Font(lblProductName.getName(), Font.BOLD, 12));
		cboProductSupplier = new JComboBox<String>();
		cboProductSupplier.setFont(new Font(cboProductSupplier.getName(), Font.BOLD, 12));
		cboProductSupplier.setMaximumRowCount(3);
		
		JLabel lblImgChoose = new JLabel("Ảnh sản phẩm");
		lblImgChoose.setFont(new Font(lblImgChoose.getName(), Font.BOLD, 12));
		

		lblDefaultImg = new JLabel("Chưa có ảnh", JLabel.CENTER);
		pnlImage = new JPanel(new BorderLayout());
		pnlImage.setBackground(new Color(229, 237, 255));
		pnlImage.setPreferredSize(new Dimension(80, 80));
		
		pnlImage.add(lblDefaultImg, BorderLayout.CENTER);
		
		btnBrowseImg = new JButton("Chọn ảnh");
		
		bImage = Box.createHorizontalBox();
		bImage.add(pnlImage);
		bImage.add(Box.createHorizontalStrut(30));
		bImage.add(btnBrowseImg);
		
		gbc.insets = new Insets(8, 15, 8, 15);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0.3;
		pnlProductInput.add(lblProductCode, gbc);
		gbc.gridx = 1;
		gbc.weightx = 0.7;
		pnlProductInput.add(txtProductCode, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 0.3;
		pnlProductInput.add(lblProductName, gbc);
		gbc.gridx = 1;
		gbc.weightx = 0.7;
		pnlProductInput.add(txtProductName, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = 0.3;
		pnlProductInput.add(lblProductSize, gbc);
		gbc.gridx = 1;
		gbc.weightx = 0.7;
		pnlProductInput.add(txtProductSize, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.weightx = 0.3;
		pnlProductInput.add(lblProductColor, gbc);
		gbc.gridx = 1;
		gbc.weightx = 0.7;
		pnlProductInput.add(txtProductColor, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.weightx = 0.3;
		pnlProductInput.add(lblProductCategory, gbc);
		gbc.gridx = 1;
		gbc.weightx = 0.7;
		pnlProductInput.add(cboProductCategory, gbc);

		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.weightx = 0.3;
		pnlProductInput.add(lblProductStock, gbc);
		gbc.gridx = 1;
		gbc.weightx = 0.7;
		pnlProductInput.add(txtProductStock, gbc);

		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.weightx = 0.3;
		pnlProductInput.add(lblProductCost, gbc);
		gbc.gridx = 1;
		gbc.weightx = 0.7;
		pnlProductInput.add(txtProductCost, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 7;
		pnlProductInput.add(lblImgChoose, gbc);
		
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridx = 1;
		pnlProductInput.add(bImage, gbc);

		gbc.gridx = 0;
		gbc.gridy = 8;
		gbc.weightx = 0.3;
		gbc.fill = GridBagConstraints.BOTH;
		pnlProductInput.add(lblProductSupplier, gbc);
		gbc.gridx = 1;
		gbc.weightx = 0.7;
		pnlProductInput.add(cboProductSupplier, gbc);
		

		Box bButtonAbove = Box.createHorizontalBox();
		Box bButtonBelow = Box.createHorizontalBox();

		btnAddProduct = new JButton("Thêm Sản Phẩm");
		btnEditProduct = new JButton("Sửa Giá");
		btnRemoveProduct = new JButton("Xóa SP");
		btnClearForm = new JButton("Reset form");

		btnAddProduct.setFont(new Font(btnAddProduct.getName(), Font.BOLD, 12));
		btnEditProduct.setFont(new Font(btnEditProduct.getName(), Font.BOLD, 12));
		btnRemoveProduct.setFont(new Font(btnRemoveProduct.getName(), Font.BOLD, 12));
		btnClearForm.setFont(new Font(btnClearForm.getName(), Font.BOLD, 12));

		bButtonAbove.add(btnAddProduct);
		bButtonAbove.add(Box.createHorizontalStrut(15));
		bButtonAbove.add(btnEditProduct);
		gbc.gridx = 1;
		gbc.gridy = 9;
		bButtonAbove.add(Box.createVerticalStrut(15));
		pnlProductInput.add(bButtonAbove, gbc);
		
		bButtonBelow.add(btnRemoveProduct);
		bButtonBelow.add(Box.createHorizontalStrut(15));
		bButtonBelow.add(btnClearForm);

		gbc.gridx = 1;
		gbc.gridy = 10;
		pnlProductInput.add(bButtonBelow, gbc);

		pnlManagerProduct.add(pnlHeader, BorderLayout.NORTH);
		pnlManagerProduct.add(pnlDisplayProduct, BorderLayout.CENTER);
		this.add(pnlManagerProduct, BorderLayout.CENTER);
		triggerEventProduct();
		addToComboBox();
	}
}
