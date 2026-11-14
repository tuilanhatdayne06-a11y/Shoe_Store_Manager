package gui_Default;

import dao.ImportProductDAO;
import dao.EmployeeDao;
import dao.PurchaseOrderDAO;
import entity.Employee;
import entity.ProductDetail;
import entity.PurchaseOrder;
import entity.PurchaseOrderDetail;
import entity.Supplier;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

public class ImportProductUI extends JPanel {
    private final ImportProductDAO importDAO = new ImportProductDAO();
    private final PurchaseOrderDAO purchaseOrderDAO = new PurchaseOrderDAO();
    private final JComboBox<SupplierOption> cboSupplier = new JComboBox<>();
    private final JComboBox<ProductChoice> cboProduct = new JComboBox<>();
    private final JComboBox<EmployeeOption> cboEmployee = new JComboBox<>();
    private final JComboBox<String> cboSize = new JComboBox<>();
    private final JTextField txtPurchaseOrderId = new JTextField(12);
    private final JTextField txtUnitPrice = new JTextField(12);
    private final JTextField txtQuantity = new JTextField(8);
    private final JLabel lblTotal = new JLabel("0 VNĐ");
    private final List<ImportProductDAO.ProductOption> productOptions = new ArrayList<>();
    private boolean loadingSuppliers;
    private boolean loadingProducts;
    private JTable historyTable;

    private final DefaultTableModel modelHistory = new DefaultTableModel(
            new String[]{"Mã phiếu", "Nhà cung cấp", "Mẫu giày", "Size",
                "Đơn giá nhập", "Số lượng", "Thành tiền", "Ngày nhập",
                "Mã nhà cung cấp", "Mã nhân viên", "Mã chi tiết sản phẩm", "Đơn giá gốc"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public ImportProductUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(253, 240, 255));

        JPanel titleBar = new JPanel(new BorderLayout());
        titleBar.setBackground(new Color(234, 230, 250));
        titleBar.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        JLabel title = new JLabel("Nhập kho", SwingConstants.LEFT);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(new Color(94, 53, 165));

        try {
            java.net.URL iconUrl = getClass().getResource("/img/import.png");
            if (iconUrl != null) {
                ImageIcon icon = new ImageIcon(iconUrl);
                Image scaledIcon = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                title.setIcon(new ImageIcon(scaledIcon));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        title.setHorizontalTextPosition(SwingConstants.RIGHT);
        title.setVerticalTextPosition(SwingConstants.CENTER);
        title.setHorizontalAlignment(SwingConstants.LEFT);
        title.setVerticalAlignment(SwingConstants.CENTER);
        titleBar.add(title, BorderLayout.CENTER);
        add(titleBar, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new BorderLayout(0, 12));
        mainPanel.setBackground(new Color(253, 240, 255));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(14, 22, 12, 20));
        add(mainPanel, BorderLayout.CENTER);

        JPanel editorPanel = new JPanel(new BorderLayout(0, 8));
        editorPanel.setBackground(new Color(253, 240, 255));
        editorPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(205, 205, 205)),
                BorderFactory.createEmptyBorder(10, 14, 10, 14)));
        mainPanel.add(editorPanel, BorderLayout.NORTH);

        Dimension fieldSize = new Dimension(220, 28);
        Dimension fieldMaximumSize = new Dimension(10000, 28);
        cboSupplier.setPreferredSize(fieldSize);
        cboSupplier.setMaximumSize(fieldMaximumSize);
        cboProduct.setPreferredSize(fieldSize);
        cboProduct.setMaximumSize(fieldMaximumSize);
        cboEmployee.setPreferredSize(fieldSize);
        cboEmployee.setMaximumSize(fieldMaximumSize);
        cboSize.setPreferredSize(fieldSize);
        cboSize.setMaximumSize(fieldMaximumSize);
        txtPurchaseOrderId.setEditable(false);
        txtPurchaseOrderId.setBackground(Color.LIGHT_GRAY);
        txtPurchaseOrderId.setPreferredSize(fieldSize);
        txtPurchaseOrderId.setMaximumSize(fieldMaximumSize);
        txtUnitPrice.setPreferredSize(fieldSize);
        txtUnitPrice.setMaximumSize(fieldMaximumSize);
        txtQuantity.setPreferredSize(fieldSize);
        txtQuantity.setMaximumSize(fieldMaximumSize);

        JLabel lblPurchaseOrderId = new JLabel("Mã phiếu nhập (tự động)");
        JLabel lblSupplier = new JLabel("Nhà cung cấp");
        JLabel lblProduct = new JLabel("Mẫu giày");
        JLabel lblSize = new JLabel("Size");
        JLabel lblEmployee = new JLabel("Nhân viên phụ trách");
        JLabel lblUnitPrice = new JLabel("Đơn giá nhập (VNĐ)");
        JLabel lblQuantity = new JLabel("Số lượng");
        JLabel lblTotalTitle = new JLabel("Tổng tiền lô hàng");
        lblPurchaseOrderId.setFont(new Font("Arial", Font.PLAIN, 13));
        lblSupplier.setFont(new Font("Arial", Font.PLAIN, 13));
        lblProduct.setFont(new Font("Arial", Font.PLAIN, 13));
        lblSize.setFont(new Font("Arial", Font.PLAIN, 13));
        lblEmployee.setFont(new Font("Arial", Font.PLAIN, 13));
        lblUnitPrice.setFont(new Font("Arial", Font.PLAIN, 13));
        lblQuantity.setFont(new Font("Arial", Font.PLAIN, 13));
        lblPurchaseOrderId.setAlignmentX(LEFT_ALIGNMENT);
        lblSupplier.setAlignmentX(LEFT_ALIGNMENT);
        lblProduct.setAlignmentX(LEFT_ALIGNMENT);
        lblSize.setAlignmentX(LEFT_ALIGNMENT);
        lblEmployee.setAlignmentX(LEFT_ALIGNMENT);
        lblUnitPrice.setAlignmentX(LEFT_ALIGNMENT);
        lblQuantity.setAlignmentX(LEFT_ALIGNMENT);
        txtPurchaseOrderId.setFont(new Font("Arial", Font.PLAIN, 13));
        txtPurchaseOrderId.setAlignmentX(LEFT_ALIGNMENT);
        txtUnitPrice.setFont(new Font("Arial", Font.PLAIN, 13));
        txtUnitPrice.setAlignmentX(LEFT_ALIGNMENT);
        txtQuantity.setFont(new Font("Arial", Font.PLAIN, 13));
        txtQuantity.setAlignmentX(LEFT_ALIGNMENT);
        cboSupplier.setFont(new Font("Arial", Font.PLAIN, 13));
        cboSupplier.setAlignmentX(LEFT_ALIGNMENT);
        cboProduct.setFont(new Font("Arial", Font.PLAIN, 13));
        cboProduct.setAlignmentX(LEFT_ALIGNMENT);
        cboEmployee.setFont(new Font("Arial", Font.PLAIN, 13));
        cboEmployee.setAlignmentX(LEFT_ALIGNMENT);
        cboSize.setFont(new Font("Arial", Font.PLAIN, 13));
        cboSize.setAlignmentX(LEFT_ALIGNMENT);

        Box idField = Box.createVerticalBox();
        idField.add(lblPurchaseOrderId);
        idField.add(txtPurchaseOrderId);
        Box supplierField = Box.createVerticalBox();
        supplierField.add(lblSupplier);
        supplierField.add(cboSupplier);
        Box productField = Box.createVerticalBox();
        productField.add(lblProduct);
        productField.add(cboProduct);
        Box sizeField = Box.createVerticalBox();
        sizeField.add(lblSize);
        sizeField.add(cboSize);
        Box employeeField = Box.createVerticalBox();
        employeeField.add(lblEmployee);
        employeeField.add(cboEmployee);
        Box unitPriceField = Box.createVerticalBox();
        unitPriceField.add(lblUnitPrice);
        unitPriceField.add(txtUnitPrice);
        Box quantityField = Box.createVerticalBox();
        quantityField.add(lblQuantity);
        quantityField.add(txtQuantity);
        Box totalField = Box.createVerticalBox();
        lblTotalTitle.setFont(new Font("Arial", Font.PLAIN, 13));
        lblTotalTitle.setAlignmentX(LEFT_ALIGNMENT);
        lblTotal.setFont(new Font("Arial", Font.BOLD, 18));
        lblTotal.setAlignmentX(LEFT_ALIGNMENT);
        totalField.add(lblTotalTitle);
        totalField.add(lblTotal);

        Dimension columnSize = new Dimension(220, 49);
        Dimension columnMaximumSize = new Dimension(10000, 49);
        idField.setMinimumSize(columnSize);
        idField.setPreferredSize(columnSize);
        idField.setMaximumSize(columnMaximumSize);
        supplierField.setMinimumSize(columnSize);
        supplierField.setPreferredSize(columnSize);
        supplierField.setMaximumSize(columnMaximumSize);
        productField.setMinimumSize(columnSize);
        productField.setPreferredSize(columnSize);
        productField.setMaximumSize(columnMaximumSize);
        sizeField.setMinimumSize(columnSize);
        sizeField.setPreferredSize(columnSize);
        sizeField.setMaximumSize(columnMaximumSize);
        employeeField.setMinimumSize(columnSize);
        employeeField.setPreferredSize(columnSize);
        employeeField.setMaximumSize(columnMaximumSize);
        unitPriceField.setMinimumSize(columnSize);
        unitPriceField.setPreferredSize(columnSize);
        unitPriceField.setMaximumSize(columnMaximumSize);
        quantityField.setMinimumSize(columnSize);
        quantityField.setPreferredSize(columnSize);
        quantityField.setMaximumSize(columnMaximumSize);
        totalField.setMinimumSize(columnSize);
        totalField.setPreferredSize(columnSize);
        totalField.setMaximumSize(columnMaximumSize);

        Box fieldsTop = Box.createHorizontalBox();
        fieldsTop.add(idField);
        fieldsTop.add(Box.createHorizontalStrut(12));
        fieldsTop.add(supplierField);
        fieldsTop.add(Box.createHorizontalStrut(12));
        fieldsTop.add(productField);
        fieldsTop.add(Box.createHorizontalStrut(12));
        fieldsTop.add(sizeField);

        Box fieldsBottom = Box.createHorizontalBox();
        fieldsBottom.add(employeeField);
        fieldsBottom.add(Box.createHorizontalStrut(12));
        fieldsBottom.add(unitPriceField);
        fieldsBottom.add(Box.createHorizontalStrut(12));
        fieldsBottom.add(quantityField);
        fieldsBottom.add(Box.createHorizontalStrut(12));
        fieldsBottom.add(totalField);

        Box fields = Box.createVerticalBox();
        fields.add(fieldsTop);
        fields.add(Box.createVerticalStrut(10));
        fields.add(fieldsBottom);
        editorPanel.add(fields, BorderLayout.CENTER);

        JButton btnImport = new JButton("Nhập kho");
        JButton btnReset = new JButton("Làm mới");
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionPanel.setOpaque(false);
        btnImport.setFont(new Font("Arial", Font.PLAIN, 13));
        btnReset.setFont(new Font("Arial", Font.PLAIN, 13));
        actionPanel.add(btnImport);
        actionPanel.add(btnReset);
        btnImport.addActionListener(event -> addImport());
        btnReset.addActionListener(event -> resetForm());

        editorPanel.add(actionPanel, BorderLayout.SOUTH);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createTitledBorder("Lịch sử phiếu nhập"));
        historyTable = new JTable(modelHistory);
        historyTable.setRowHeight(25);
        historyTable.setFont(new Font("Arial", Font.PLAIN, 12));
        historyTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        historyTable.setFillsViewportHeight(true);
        historyTable.getSelectionModel().addListSelectionListener(this::loadSelectedImport);
        for (int column = modelHistory.getColumnCount() - 1; column >= 8; column--) {
            historyTable.removeColumn(historyTable.getColumnModel().getColumn(column));
        }
        tablePanel.add(new JScrollPane(historyTable), BorderLayout.CENTER);
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        cboSupplier.addActionListener(event -> {
            if (!loadingSuppliers) {
                loadProducts();
            }
        });
        cboProduct.addActionListener(event -> {
            if (!loadingProducts) {
                updateSizes();
            }
        });
        addPriceCalculationListeners();
        loadSuppliers();
        loadEmployees();
        resetForm();
        refreshHistory();
    }

    private void loadSuppliers() {
        loadingSuppliers = true;
        try {
            List<Supplier> suppliers = importDAO.getSuppliers();
            DefaultComboBoxModel<SupplierOption> model = new DefaultComboBoxModel<>();
            for (Supplier supplier : suppliers) {
                model.addElement(new SupplierOption(
                        supplier.getSupplierId(), supplier.getSupplierName()));
            }
            cboSupplier.setModel(model);
        } catch (SQLException e) {
            showError("Không thể tải danh sách nhà cung cấp.", e);
        } finally {
            loadingSuppliers = false;
        }
        loadProducts();
    }

    private void loadEmployees() {
        DefaultComboBoxModel<EmployeeOption> model = new DefaultComboBoxModel<>();
        for (Object[] employee : new EmployeeDao().getEmployeeByPosition("Nhân viên kho")) {
            model.addElement(new EmployeeOption(
                    (String) employee[0], (String) employee[1]));
        }
        cboEmployee.setModel(model);
    }

    private void loadProducts() {
        SupplierOption supplier = (SupplierOption) cboSupplier.getSelectedItem();
        loadingProducts = true;
        try {
            productOptions.clear();
            if (supplier != null) {
                productOptions.addAll(importDAO.getProductsBySupplier(supplier.supplierId));
            }
            LinkedHashMap<String, ProductChoice> choices = new LinkedHashMap<>();
            for (ImportProductDAO.ProductOption option : productOptions) {
                String key = option.getProductName() + "\u0000" + option.getColor();
                choices.putIfAbsent(key, new ProductChoice(option.getProductName(), option.getColor()));
            }
            DefaultComboBoxModel<ProductChoice> model = new DefaultComboBoxModel<>();
            for (ProductChoice choice : choices.values()) {
                model.addElement(choice);
            }
            cboProduct.setModel(model);
        } catch (SQLException e) {
            showError("Không thể tải danh sách mẫu giày.", e);
        } finally {
            loadingProducts = false;
        }
        updateSizes();
    }

    private void updateSizes() {
        ProductChoice choice = (ProductChoice) cboProduct.getSelectedItem();
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        if (choice != null) {
            for (ImportProductDAO.ProductOption option : productOptions) {
                if (choice.matches(option)) {
                    model.addElement(option.getSize());
                }
            }
        }
        cboSize.setModel(model);
    }

    private void refreshHistory() {
        try {
            modelHistory.setRowCount(0);
            NumberFormat currency = NumberFormat.getNumberInstance(Locale.forLanguageTag("vi-VN"));
            for (Object[] row : importDAO.getPurchaseHistory()) {
                modelHistory.addRow(new Object[]{
                        row[0],
                        row[1],
                        row[2],
                        row[3],
                        currency.format(row[4]),
                        row[5],
                        currency.format(row[6]),
                        row[7],
                        row[8],
                        row[9],
                        row[10],
                        row[11]
                });
            }
        } catch (SQLException e) {
            showError("Không thể tải lịch sử phiếu nhập.", e);
        }
    }

    private void loadSelectedImport(ListSelectionEvent event) {
        if (event.getValueIsAdjusting()) {
            return;
        }
        int selectedRow = historyTable.getSelectedRow();
        if (selectedRow < 0) {
            return;
        }

        int modelRow = historyTable.convertRowIndexToModel(selectedRow);
        String supplierId = (String) modelHistory.getValueAt(modelRow, 8);
        String employeeId = (String) modelHistory.getValueAt(modelRow, 9);
        String productDetailId = (String) modelHistory.getValueAt(modelRow, 10);
        String importPrice = String.valueOf(modelHistory.getValueAt(modelRow, 11));
        int quantity = (Integer) modelHistory.getValueAt(modelRow, 5);

        txtPurchaseOrderId.setText((String) modelHistory.getValueAt(modelRow, 0));
        selectSupplier(supplierId);
        selectEmployee(employeeId);
        selectProduct(productDetailId);
        txtUnitPrice.setText(importPrice);
        txtQuantity.setText(Integer.toString(quantity));
    }

    private void selectSupplier(String supplierId) {
        for (int i = 0; i < cboSupplier.getItemCount(); i++) {
            if (supplierId.equals(cboSupplier.getItemAt(i).supplierId)) {
                cboSupplier.setSelectedIndex(i);
                return;
            }
        }
    }

    private void selectEmployee(String employeeId) {
        for (int i = 0; i < cboEmployee.getItemCount(); i++) {
            if (employeeId != null
                    && employeeId.equals(cboEmployee.getItemAt(i).employeeId)) {
                cboEmployee.setSelectedIndex(i);
                return;
            }
        }
    }

    private void selectProduct(String productDetailId) {
        for (ImportProductDAO.ProductOption option : productOptions) {
            if (!productDetailId.equals(option.getProductDetailId())) {
                continue;
            }
            for (int i = 0; i < cboProduct.getItemCount(); i++) {
                if (cboProduct.getItemAt(i).matches(option)) {
                    cboProduct.setSelectedIndex(i);
                    cboSize.setSelectedItem(option.getSize());
                    return;
                }
            }
        }
    }

    private void addImport() {
        EmployeeOption employee = (EmployeeOption) cboEmployee.getSelectedItem();
        SupplierOption supplier = (SupplierOption) cboSupplier.getSelectedItem();
        ProductChoice choice = (ProductChoice) cboProduct.getSelectedItem();
        String size = (String) cboSize.getSelectedItem();
        ImportProductDAO.ProductOption product = null;
        if (choice != null && size != null) {
            for (ImportProductDAO.ProductOption option : productOptions) {
                if (choice.matches(option) && size.equals(option.getSize())) {
                    product = option;
                    break;
                }
            }
        }
        if (employee == null || supplier == null || product == null) {
            JOptionPane.showMessageDialog(this,
                    "Cần có nhân viên, nhà cung cấp và mẫu giày trong cơ sở dữ liệu trước khi nhập kho.");
            return;
        }

        try {
            double importPrice = Double.parseDouble(
                    txtUnitPrice.getText().trim().replace(",", ""));
            int quantity = Integer.parseInt(txtQuantity.getText().trim());
            if (Double.isNaN(importPrice) || Double.isInfinite(importPrice)
                    || importPrice <= 0 || quantity <= 0) {
                throw new IllegalArgumentException("Đơn giá và số lượng phải lớn hơn 0.");
            }

            PurchaseOrder order = new PurchaseOrder(
                    null, null, new Employee(employee.employeeId),
                    new Supplier(supplier.supplierId), "Da nhap");
            PurchaseOrderDetail detail = new PurchaseOrderDetail(
                    order, new ProductDetail(product.getProductDetailId()),
                    quantity, importPrice);
            String purchaseOrderId = purchaseOrderDAO.createImportOrder(order, detail);
            refreshHistory();
            resetForm();
            JOptionPane.showMessageDialog(this,
                    "Đã nhập kho thành công. Mã phiếu: " + purchaseOrderId);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Đơn giá và số lượng phải là số hợp lệ.");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        } catch (SQLException e) {
            showError("Không thể tạo phiếu nhập.", e);
        }
    }

    private void resetForm() {
        try {
            txtPurchaseOrderId.setText(purchaseOrderDAO.getNextPurchaseOrderId());
        } catch (SQLException e) {
            txtPurchaseOrderId.setText("");
            showError("Không thể tạo mã phiếu nhập tiếp theo.", e);
        }
        txtUnitPrice.setText("");
        txtQuantity.setText("");
        cboSize.setSelectedIndex(cboSize.getItemCount() > 0 ? 0 : -1);
        updateTotal();
    }

    private void addPriceCalculationListeners() {
        DocumentListener listener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent event) {
                updateTotal();
            }

            @Override
            public void removeUpdate(DocumentEvent event) {
                updateTotal();
            }

            @Override
            public void changedUpdate(DocumentEvent event) {
                updateTotal();
            }
        };
        txtUnitPrice.getDocument().addDocumentListener(listener);
        txtQuantity.getDocument().addDocumentListener(listener);
    }

    private void updateTotal() {
        try {
            double price = Double.parseDouble(txtUnitPrice.getText().trim().replace(",", ""));
            int quantity = Integer.parseInt(txtQuantity.getText().trim());
            lblTotal.setText(NumberFormat.getNumberInstance(Locale.forLanguageTag("vi-VN"))
                    .format(price * quantity) + " VNĐ");
        } catch (NumberFormatException e) {
            lblTotal.setText("0 VNĐ");
        }
    }

    private void showError(String message, Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, message + "\n" + e.getMessage(),
                "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    private static class SupplierOption {
        private final String supplierId;
        private final String supplierName;

        private SupplierOption(String supplierId, String supplierName) {
            this.supplierId = supplierId;
            this.supplierName = supplierName;
        }
        

        @Override
        public String toString() {
            return supplierId + " - " + supplierName;
        }
    }

    private static class EmployeeOption {
        private final String employeeId;
        private final String employeeName;

        private EmployeeOption(String employeeId, String employeeName) {
            this.employeeId = employeeId;
            this.employeeName = employeeName;
        }

        @Override
        public String toString() {
            return employeeId + " - " + employeeName;
        }
    }

    private static class ProductChoice {
        private final String productName;
        private final String color;

        private ProductChoice(String productName, String color) {
            this.productName = productName;
            this.color = color;
        }

        private boolean matches(ImportProductDAO.ProductOption option) {
            return productName.equals(option.getProductName())
                    && java.util.Objects.equals(color, option.getColor());
        }

        @Override
        public String toString() {
            return color == null || color.isEmpty() ? productName : productName + " - " + color;
        }
    }
}
