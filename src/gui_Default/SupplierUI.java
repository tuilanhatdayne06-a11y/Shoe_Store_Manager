package gui_Default;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.List;

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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

import dao.SupplierDAO;
import entity.Supplier;
import utils.ImageUtil;

public class SupplierUI extends JPanel implements ActionListener, MouseListener {
    private static final Font FONT = new Font("Arial", Font.PLAIN, 13);
    private static final Color BACKGROUND_COLOR = new Color(253, 240, 255);
    private static final Color ACCENT_COLOR = new Color(180, 60, 200);
    private static final int LOGO_SIZE = 80;

    private JTextField txtSupplierId;
    private JTextField txtSupplierName;
    private JTextField txtAddress;
    private JTextField txtPhoneNumber;
    private JTextField txtEmail;
    private String logoPath = "";
    private JLabel lblLogoPreview;
    private JButton btnBrowseLogo;

    private JButton btnAdd;
    private JButton btnClear;
    private JButton btnUpdate;
    private JButton btnDelete;

    private final SupplierDAO supplierDao = new SupplierDAO();
    private ImageUtil imgUtil = new ImageUtil();
    private DefaultTableModel supplierTableModel;
    private JTable supplierTable;
    private JComboBox<String> cboSupplierId;
    private boolean updatingSupplierComboBox;
	private File image;

    public SupplierUI() {
        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);

        JPanel titleBar = new JPanel(new BorderLayout());
        titleBar.setBackground(new Color(234, 230, 250));
        titleBar.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        JLabel title = new JLabel("Quản lý nhà cung cấp", JLabel.LEFT);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(new Color(94, 53, 165));

        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/img/supplier.png"));
            Image scaleIcon = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            title.setIcon(new ImageIcon(scaleIcon));
        } catch (Exception e) {
            e.printStackTrace();
        }

        title.setHorizontalTextPosition(JLabel.RIGHT);
        title.setVerticalTextPosition(JLabel.CENTER);
        title.setHorizontalAlignment(JLabel.LEFT);
        title.setVerticalAlignment(JLabel.CENTER);
        titleBar.add(title, BorderLayout.CENTER);
        add(titleBar, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new BorderLayout(0, 12));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(14, 22, 12, 20));
        add(mainPanel, BorderLayout.CENTER);

        JPanel editorPanel = new JPanel(new BorderLayout(0, 8));
        editorPanel.setBackground(BACKGROUND_COLOR);
        editorPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(205, 205, 205)),
                BorderFactory.createEmptyBorder(10, 14, 10, 14)));
        mainPanel.add(editorPanel, BorderLayout.NORTH);

        txtSupplierId = new JTextField(25);
        txtSupplierName = new JTextField(25);
        txtAddress = new JTextField(25);
        txtPhoneNumber = new JTextField(25);
        txtEmail = new JTextField(25);
        txtSupplierId.setFont(FONT);
        txtSupplierId.setEditable(false);
        txtSupplierId.setBackground(Color.LIGHT_GRAY);
        txtSupplierName.setFont(FONT);
        txtAddress.setFont(FONT);
        txtPhoneNumber.setFont(FONT);
        txtEmail.setFont(FONT);

        Box b = Box.createVerticalBox();
        Box bFields = Box.createVerticalBox();
        Box bContent = Box.createHorizontalBox();
        Box bTop = Box.createHorizontalBox();
        Box bBottom = Box.createHorizontalBox();
        Box bLogo = Box.createVerticalBox();
        Box b1 = Box.createVerticalBox();
        Box b2 = Box.createVerticalBox();
        Box b3 = Box.createVerticalBox();
        Box b4 = Box.createVerticalBox();
        Box b5 = Box.createVerticalBox();
        Box b6 = Box.createVerticalBox();
        editorPanel.add(b, BorderLayout.CENTER);

        JLabel lblSupplierId = new JLabel("Mã nhà cung cấp (tự động)");
        lblSupplierId.setFont(FONT);
        b1.add(lblSupplierId);
        b1.add(txtSupplierId);

        JLabel lblSupplierName = new JLabel("Tên nhà cung cấp *");
        lblSupplierName.setFont(FONT);
        b2.add(lblSupplierName);
        b2.add(txtSupplierName);

        JLabel lblAddress = new JLabel("Địa chỉ");
        lblAddress.setFont(FONT);
        b3.add(lblAddress);
        b3.add(txtAddress);

        JLabel lblPhoneNumber = new JLabel("Số điện thoại");
        lblPhoneNumber.setFont(FONT);
        b4.add(lblPhoneNumber);
        b4.add(txtPhoneNumber);

        JLabel lblEmail = new JLabel("Email");
        lblEmail.setFont(FONT);
        b5.add(lblEmail);
        b5.add(txtEmail);

        JLabel lblLogo = new JLabel("Logo");
        lblLogo.setFont(FONT);
        b6.add(lblLogo);
        Box logoInput = Box.createHorizontalBox();
        btnBrowseLogo = new JButton("Chọn ảnh");
        btnBrowseLogo.setFont(FONT);
        JPanel logoPreview = new JPanel(new BorderLayout());
        logoPreview.setBackground(new Color(229, 237, 255));
        logoPreview.setPreferredSize(new Dimension(LOGO_SIZE, LOGO_SIZE));
        logoPreview.setMinimumSize(new Dimension(LOGO_SIZE, LOGO_SIZE));
        logoPreview.setMaximumSize(new Dimension(LOGO_SIZE, LOGO_SIZE));
        lblLogoPreview = new JLabel("Chưa có ảnh", JLabel.CENTER);
        logoPreview.add(lblLogoPreview, BorderLayout.CENTER);
        logoInput.add(logoPreview);
        logoInput.add(Box.createHorizontalStrut(30));
        logoInput.add(btnBrowseLogo);
        b6.add(logoInput);

        lblSupplierId.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtSupplierId.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblSupplierName.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtSupplierName.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblAddress.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtAddress.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblPhoneNumber.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtPhoneNumber.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblEmail.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtEmail.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblLogo.setAlignmentX(Component.LEFT_ALIGNMENT);
        logoInput.setAlignmentX(Component.LEFT_ALIGNMENT);

        bTop.add(b1);
        bTop.add(Box.createHorizontalStrut(10));
        bTop.add(b2);
        bTop.add(Box.createHorizontalStrut(10));
        bTop.add(b3);

        bBottom.add(b4);
        bBottom.add(Box.createHorizontalStrut(10));
        bBottom.add(b5);
        bLogo.add(b6);

        b.add(Box.createVerticalStrut(10));
        bFields.add(bTop);
        bFields.add(Box.createVerticalStrut(5));
        bFields.add(bBottom);
        bContent.add(bFields);
        bContent.add(Box.createHorizontalStrut(16));
        bContent.add(bLogo);
        b.add(bContent);
        b.add(Box.createVerticalStrut(5));
        b.add(Box.createVerticalStrut(10));

        Box actionPanel = Box.createHorizontalBox();

        btnAdd = new JButton("Thêm");
        btnUpdate = new JButton("Cập nhật");
        btnDelete = new JButton("Xóa");
        btnClear = new JButton("Làm mới");
        btnAdd.setFont(FONT);
        btnUpdate.setFont(FONT);
        btnDelete.setFont(FONT);
        btnClear.setFont(FONT);
        
        actionPanel.add(btnAdd);
        actionPanel.add(Box.createHorizontalStrut(10));
        actionPanel.add(btnUpdate);
        actionPanel.add(Box.createHorizontalStrut(10));
        actionPanel.add(btnDelete);
        actionPanel.add(Box.createHorizontalStrut(10));
        actionPanel.add(btnClear);
        actionPanel.add(Box.createHorizontalStrut(24));
        
        actionPanel.add(new JLabel("Tìm theo mã nhà cung cấp:"));
        cboSupplierId = new JComboBox<>();
        cboSupplierId.setPreferredSize(new Dimension(150, 28));
        cboSupplierId.setMaximumSize(new Dimension(150, 28));
        cboSupplierId.setFont(FONT);
        cboSupplierId.setToolTipText("Chọn mã để tìm nhà cung cấp");
        actionPanel.add(cboSupplierId);
        actionPanel.add(Box.createHorizontalGlue());
        editorPanel.add(actionPanel, BorderLayout.SOUTH);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(BACKGROUND_COLOR);
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        String[] columnHeaders = {
                "Mã nhà cung cấp", "Tên nhà cung cấp", "Địa chỉ",
                "Số điện thoại", "Email"
        };
        supplierTableModel = new DefaultTableModel(columnHeaders, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        supplierTable = new JTable(supplierTableModel);
        supplierTable.setRowHeight(25);
        supplierTable.setFont(FONT);
        supplierTable.getTableHeader().setFont(FONT);
        supplierTable.setFillsViewportHeight(true);
        tablePanel.add(new JScrollPane(supplierTable), BorderLayout.CENTER);

        btnAdd.addActionListener(this);
        btnClear.addActionListener(this);
        btnUpdate.addActionListener(this);
        btnDelete.addActionListener(this);
        btnBrowseLogo.addActionListener(event -> {
            image = imgUtil.browseImg();
            if (image != null) {
                imgUtil.saveImg(image);
                setLogoPreview(getLogoProjectPath(image.getName()));
            }
        });
        cboSupplierId.addActionListener(this);
        supplierTable.addMouseListener(this);

        new SwingWorker<List<Supplier>, Void>() {
            @Override
            protected List<Supplier> doInBackground() throws Exception {
                return supplierDao.getAll();
            }

            @Override
            protected void done() {
                try {
                    List<Supplier> suppliers = get();
                    updateTable(suppliers);
                    updateSupplierComboBox(suppliers);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    showMessage("Đã bị gián đoạn khi tải danh sách nhà cung cấp.");
                } catch (java.util.concurrent.ExecutionException e) {
                    Throwable cause = e.getCause();
                    showMessage("Không thể tải danh sách nhà cung cấp: "
                            + cause.getMessage());
                }
            }
        }.execute();
    }

    private boolean validData() {
        String[] values = readFormValues();
        if (values[1].isEmpty()) {
            showMessage("Vui lòng nhập tên nhà cung cấp.");
            return false;
        }

        int[] maxLengths = {20, 150, 200, 15, 100, 255};
        String[] fieldNames = {
                "Mã nhà cung cấp", "Tên nhà cung cấp", "Địa chỉ",
                "Số điện thoại", "Email", "Logo"
        };
        for (int i = 0; i < values.length; i++) {
            if (values[i].length() > maxLengths[i]) {
                showMessage(fieldNames[i] + " không được vượt quá " + maxLengths[i] + " ký tự.");
                return false;
            }
        }
        if (!values[4].isEmpty()
                && !values[4].matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            showMessage("Email không đúng định dạng.");
            return false;
        }
        return true;
    }

    private String[] readFormValues() {
        return new String[]{
                txtSupplierId.getText().trim(),
                txtSupplierName.getText().trim(),
                txtAddress.getText().trim(),
                txtPhoneNumber.getText().trim(),
                txtEmail.getText().trim(),
                image == null ? getLogoFileName(logoPath) : image.getName()
        };
    }

    private Supplier readSupplierFromForm() {
        String[] values = readFormValues();
        return new Supplier(values[0], values[1], values[2], values[3], values[4], values[5]);
    }

    private void updateTable(List<Supplier> suppliers) {
        supplierTableModel.setRowCount(0);
        for (Supplier supplier : suppliers) {
            Object[] row = supplier.toRow();
            row[5] = getLogoProjectPath(supplier.getLogo());
            supplierTableModel.addRow(new Object[]{row[0], row[1], row[2], row[3], row[4]});
        }
    }

    private void updateSupplierComboBox(List<Supplier> suppliers) {
        Object selectedId = cboSupplierId.getSelectedItem();
        String[] supplierIds = new String[suppliers.size()];
        for (int i = 0; i < suppliers.size(); i++) {
            supplierIds[i] = suppliers.get(i).getSupplierId();
        }

        updatingSupplierComboBox = true;
        try {
            cboSupplierId.setModel(new DefaultComboBoxModel<>(supplierIds));
            if (selectedId != null) {
                cboSupplierId.setSelectedItem(selectedId);
            } else {
                cboSupplierId.setSelectedIndex(-1);
            }
        } finally {
            updatingSupplierComboBox = false;
        }
    }

    private void refreshSupplierData() {
        try {
            List<Supplier> suppliers = supplierDao.getAll();
            updateTable(suppliers);
            updateSupplierComboBox(suppliers);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            showMessage("Không thể tải danh sách nhà cung cấp: " + e.getMessage());
        }
    }

    private void clearForm() {
        try {
            txtSupplierId.setText(supplierDao.getNextSupplierId());
        } catch (java.sql.SQLException e) {
            txtSupplierId.setText("");
            showMessage("Không thể tạo mã nhà cung cấp tiếp theo: " + e.getMessage());
        }
        txtSupplierName.setText("");
        txtAddress.setText("");
        txtPhoneNumber.setText("");
        txtEmail.setText("");
        image = null;
        setLogoPreview(null);
        supplierTable.clearSelection();
        updatingSupplierComboBox = true;
        try {
            cboSupplierId.setSelectedIndex(-1);
        } finally {
            updatingSupplierComboBox = false;
        }
    }

    private void showSupplier(Supplier supplier) {
        image = null;
        String name = getLogoFileName(supplier.getLogo());
        txtSupplierId.setText(supplier.getSupplierId());
        txtSupplierName.setText(supplier.getSupplierName());
        txtAddress.setText(supplier.getAddress());
        txtPhoneNumber.setText(supplier.getPhoneNumber());
        txtEmail.setText(supplier.getEmail());
        setLogoPreview("src/img/" + name);
    }

    private String getLogoProjectPath(String fileName) {
        String logoFileName = getLogoFileName(fileName);
        return logoFileName.isEmpty() ? "" : new File("src/img", logoFileName).getName();
    }

    private String getLogoFileName(String path) {
        return path == null || path.trim().isEmpty() ? "" : new File(path.trim()).getName();
    }

    private void setLogoPreview(String path) {
        logoPath = path == null ? "" : path.trim();
        lblLogoPreview.setIcon(null);
        lblLogoPreview.setText("Chưa có ảnh");
        if (logoPath.isEmpty()) {
            return;
        }

        ImageIcon original = new ImageIcon(logoPath);
        if (original.getIconWidth() <= 0 || original.getIconHeight() <= 0) {
            return;
        }

        Image scaled = original.getImage().getScaledInstance(
                LOGO_SIZE, LOGO_SIZE, Image.SCALE_SMOOTH);
        lblLogoPreview.setText("");
        lblLogoPreview.setIcon(new ImageIcon(scaled));
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        handleAction(event);
    }

    private void handleAction(ActionEvent event) {
        Object source = event.getSource();
        if (source == btnAdd) {
            if (!validData()) {
                return;
            }
            Supplier supplier = readSupplierFromForm();
            if (!supplierDao.insertSupplier(supplier)) {
                showMessage("Không thể thêm nhà cung cấp.");
                return;
            }
            refreshSupplierData();
            clearForm();
            showMessage("Thêm nhà cung cấp thành công. Mã: " + supplier.getSupplierId());
        } else if (source == btnClear) {
            clearForm();
        } else if (source == btnDelete) {
            String supplierId = txtSupplierId.getText().trim();
            Supplier supplier = supplierDao.findById(supplierId);
            if (supplier == null) {
                showMessage("Không tìm thấy mã nhà cung cấp cần xóa.");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc muốn xóa nhà cung cấp " + supplierId + "?",
                    "Xác nhận xóa", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                if (supplierDao.deleteSupplier(supplierId)) {
                    refreshSupplierData();
                    clearForm();
                } else {
                    showMessage("Nhà cung cấp không còn tồn tại hoặc không thể xóa.");
                }
            }
        } else if (source == btnUpdate) {
            int row = supplierTable.getSelectedRow();
            if (row < 0) {
                showMessage("Vui lòng chọn nhà cung cấp cần sửa trong bảng.");
                return;
            }
            if (!validData()) {
                return;
            }

            String oldId = supplierTableModel.getValueAt(row, 0).toString();
            Supplier updatedSupplier = readSupplierFromForm();
            if (!oldId.equalsIgnoreCase(updatedSupplier.getSupplierId())
                    && supplierDao.findById(updatedSupplier.getSupplierId()) != null) {
                showMessage("Mã nhà cung cấp đã tồn tại.");
                return;
            }

            if (!supplierDao.updateSupplier(oldId, updatedSupplier)) {

                if (supplierDao.findById(oldId) == null) {
                    showMessage("Không tìm thấy nhà cung cấp cần cập nhật.");
                } else {
                    showMessage("Mã nhà cung cấp đã tồn tại.");
                }
                return;
            }
            refreshSupplierData();
            clearForm();
        } else if (source == cboSupplierId && !updatingSupplierComboBox) {
            Object selectedId = cboSupplierId.getSelectedItem();
            if (selectedId != null) {
                Supplier supplier = supplierDao.findById(selectedId.toString());
                if (supplier != null) {
                    showSupplier(supplier);
                    selectSupplierInTable(supplier.getSupplierId());
                }
            }
        }
    }

    private void selectSupplierInTable(String supplierId) {
        for (int row = 0; row < supplierTableModel.getRowCount(); row++) {
            if (supplierId.equals(supplierTableModel.getValueAt(row, 0).toString())) {
                supplierTable.setRowSelectionInterval(row, row);
                supplierTable.scrollRectToVisible(supplierTable.getCellRect(row, 0, true));
                return;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent event) {
        int row = supplierTable.getSelectedRow();
        if (row >= 0) {
            String supplierId = supplierTableModel.getValueAt(row, 0).toString();
            try {
                Supplier supplier = supplierDao.findById(supplierId);
                if (supplier != null) {
                    showSupplier(supplier);
                }
            } catch (Exception e) {
                e.printStackTrace();
                showMessage("Không thể tải thông tin nhà cung cấp: " + e.getMessage());
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent event) {
    }

    @Override
    public void mouseReleased(MouseEvent event) {
    }

    @Override
    public void mouseEntered(MouseEvent event) {
    }

    @Override
    public void mouseExited(MouseEvent event) {
    }
}
