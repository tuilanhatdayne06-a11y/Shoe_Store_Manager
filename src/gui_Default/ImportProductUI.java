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
import java.awt.FlowLayout;
import java.awt.Font;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
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
import javax.swing.table.DefaultTableModel;

public class ImportProductUI extends JPanel {
    private final ImportProductDAO importDAO = new ImportProductDAO();
    private final PurchaseOrderDAO purchaseOrderDAO = new PurchaseOrderDAO();
    private final JComboBox<SupplierOption> cboSupplier = new JComboBox<>();
    private final JComboBox<ProductChoice> cboProduct = new JComboBox<>();
    private final JComboBox<EmployeeOption> cboEmployee = new JComboBox<>();
    private final JComboBox<String> cboSize = new JComboBox<>();
    private final JTextField txtUnitPrice = new JTextField(12);
    private final JTextField txtQuantity = new JTextField(8);
    private final JLabel lblTotal = new JLabel("0 VNĐ");
    private final List<ImportProductDAO.ProductOption> productOptions = new ArrayList<>();
    private boolean loadingSuppliers;
    private boolean loadingProducts;

    private final DefaultTableModel modelHistory = new DefaultTableModel(
            new String[]{"Mã phiếu", "Nhà cung cấp", "Mẫu giày", "Size",
                "Đơn giá nhập", "Số lượng", "Thành tiền", "Ngày nhập"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public ImportProductUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(253, 240, 255));
        setBorder(BorderFactory.createEmptyBorder(14, 0, 12, 0));

        add(createImportTab(), BorderLayout.CENTER);

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
        refreshHistory();
    }

    private JPanel createImportTab() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(253, 240, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(12, 0, 0, 0));

        JPanel sectionHeader = new JPanel(new BorderLayout());
        sectionHeader.setBackground(new Color(253, 240, 255));
        sectionHeader.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        JLabel sectionTitle = new JLabel("NHẬP KHO", SwingConstants.CENTER);
        sectionTitle.setFont(new Font("Arial", Font.BOLD, 24));
        sectionTitle.setForeground(new Color(180, 60, 200));
        sectionHeader.add(sectionTitle, BorderLayout.CENTER);

        cboSupplier.setPreferredSize(new java.awt.Dimension(190, 28));
        cboProduct.setPreferredSize(new java.awt.Dimension(220, 28));
        cboEmployee.setPreferredSize(new java.awt.Dimension(190, 28));
        cboSize.setPreferredSize(new java.awt.Dimension(90, 28));
        txtUnitPrice.setPreferredSize(new java.awt.Dimension(150, 28));
        txtQuantity.setPreferredSize(new java.awt.Dimension(100, 28));

        Box fieldsTop = Box.createHorizontalBox();
        fieldsTop.add(createField("Nhà cung cấp", cboSupplier));
        fieldsTop.add(Box.createHorizontalStrut(12));
        fieldsTop.add(createField("Mẫu giày", cboProduct));
        fieldsTop.add(Box.createHorizontalStrut(12));
        fieldsTop.add(createField("Size", cboSize));

        Box fieldsBottom = Box.createHorizontalBox();
        fieldsBottom.add(createField("Nhân viên phụ trách", cboEmployee));
        fieldsBottom.add(Box.createHorizontalStrut(12));
        fieldsBottom.add(createField("Đơn giá nhập (VNĐ)", txtUnitPrice));
        fieldsBottom.add(Box.createHorizontalStrut(12));
        fieldsBottom.add(createField("Số lượng", txtQuantity));

        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBackground(new Color(253, 240, 255));
        form.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(205, 205, 205)),
                BorderFactory.createEmptyBorder(10, 14, 10, 14)));
        form.add(fieldsTop);
        form.add(Box.createVerticalStrut(10));
        form.add(fieldsBottom);

        JButton btnImport = new JButton("Nhập kho");
        JButton btnReset = new JButton("Làm mới");
        btnImport.addActionListener(event -> addImport());
        btnReset.addActionListener(event -> resetForm());

        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(new Color(253, 240, 255));
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        totalPanel.setOpaque(false);
        JLabel totalLabel = new JLabel("TỔNG TIỀN LÔ HÀNG:");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        lblTotal.setFont(new Font("Arial", Font.BOLD, 18));
        totalPanel.add(totalLabel);
        totalPanel.add(lblTotal);

        Box actions = Box.createHorizontalBox();
        actions.add(Box.createHorizontalGlue());
        actions.add(btnImport);
        actions.add(Box.createHorizontalStrut(10));
        actions.add(btnReset);
        footer.add(totalPanel, BorderLayout.WEST);
        footer.add(actions, BorderLayout.EAST);

        JPanel editor = new JPanel(new BorderLayout(0, 10));
        editor.setBackground(new Color(253, 240, 255));
        editor.add(form, BorderLayout.CENTER);
        editor.add(footer, BorderLayout.SOUTH);

        JPanel content = new JPanel(new BorderLayout(0, 10));
        content.setBackground(new Color(253, 240, 255));
        content.setBorder(BorderFactory.createEmptyBorder(8, 22, 0, 20));
        content.add(editor, BorderLayout.NORTH);
        content.add(createHistoryTablePanel(), BorderLayout.CENTER);

        panel.add(sectionHeader, BorderLayout.NORTH);
        panel.add(content, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createField(String labelText, java.awt.Component input) {
        JPanel field = new JPanel(new BorderLayout(0, 4));
        field.setOpaque(false);
        field.setPreferredSize(new java.awt.Dimension(100, 60));
        field.setMinimumSize(new java.awt.Dimension(0, 60));
        field.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 60));
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 13));
        input.setPreferredSize(new java.awt.Dimension(input.getPreferredSize().width, 34));
        field.add(label, BorderLayout.NORTH);
        field.add(input, BorderLayout.CENTER);
        return field;
    }

    private JPanel createHistoryTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder("Lịch sử phiếu nhập"));

        JTable table = new JTable(modelHistory);
        table.setRowHeight(25);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.setFillsViewportHeight(true);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
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
                        row[7]
                });
            }
        } catch (SQLException e) {
            showError("Không thể tải lịch sử phiếu nhập.", e);
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
