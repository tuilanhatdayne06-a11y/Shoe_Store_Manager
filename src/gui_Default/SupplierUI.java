package gui_Default;

import entity.Supplier;
import dao.SupplierDAO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
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

public class SupplierUI extends JPanel implements ActionListener, MouseListener {
    private static final Font FONT = new Font("Arial", Font.PLAIN, 13);

    private JTextField txtSupplierId;
    private JTextField txtSupplierName;
    private JTextField txtAddress;
    private JTextField txtPhoneNumber;
    private JTextField txtEmail;
    private JTextField txtLogo;

    private JButton btnAdd;
    private JButton btnClear;
    private JButton btnUpdate;
    private JButton btnDelete;

    private final SupplierDAO supplierDao = new SupplierDAO();
    private DefaultTableModel supplierTableModel;
    private JTable supplierTable;
    private JComboBox<String> cboSupplierId;
    private boolean updatingSupplierComboBox;

    public SupplierUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(242, 242, 242));

        JPanel titleBar = new JPanel(new BorderLayout());
        titleBar.setBackground(new Color(235, 231, 250));
        titleBar.setBorder(BorderFactory.createEmptyBorder(14, 26, 14, 12));
        JLabel title = new JLabel("Quản lý nhà cung cấp");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(new Color(91, 62, 145));
        titleBar.add(title, BorderLayout.CENTER);
        add(titleBar, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new BorderLayout(0, 12));
        mainPanel.setBackground(new Color(242, 242, 242));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(14, 22, 12, 20));
        add(mainPanel, BorderLayout.CENTER);

        JPanel editorPanel = new JPanel(new BorderLayout(0, 8));
        editorPanel.setBackground(new Color(242, 242, 242));
        editorPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(205, 205, 205)),
                BorderFactory.createEmptyBorder(10, 14, 10, 14)));
        mainPanel.add(editorPanel, BorderLayout.NORTH);

        txtSupplierId = new JTextField(25);
        txtSupplierName = new JTextField(25);
        txtAddress = new JTextField(25);
        txtPhoneNumber = new JTextField(25);
        txtEmail = new JTextField(25);
        txtLogo = new JTextField(25);
        txtSupplierId.setFont(FONT);
        txtSupplierName.setFont(FONT);
        txtAddress.setFont(FONT);
        txtPhoneNumber.setFont(FONT);
        txtEmail.setFont(FONT);
        txtLogo.setFont(FONT);

        Box b = Box.createVerticalBox();
        Box bTop = Box.createHorizontalBox();
        Box bBottom = Box.createHorizontalBox();
        Box b1 = Box.createVerticalBox();
        Box b2 = Box.createVerticalBox();
        Box b3 = Box.createVerticalBox();
        Box b4 = Box.createVerticalBox();
        Box b5 = Box.createVerticalBox();
        Box b6 = Box.createVerticalBox();
        editorPanel.add(b, BorderLayout.CENTER);

        JLabel lblSupplierId = new JLabel("Mã nhà cung cấp *");
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

        JLabel lblLogo = new JLabel("Đường dẫn logo");
        lblLogo.setFont(FONT);
        b6.add(lblLogo);
        b6.add(txtLogo);

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
        txtLogo.setAlignmentX(Component.LEFT_ALIGNMENT);

        bTop.add(b1);
        bTop.add(Box.createHorizontalStrut(10));
        bTop.add(b2);
        bTop.add(Box.createHorizontalStrut(10));
        bTop.add(b3);

        bBottom.add(b4);
        bBottom.add(Box.createHorizontalStrut(10));
        bBottom.add(b5);
        bBottom.add(Box.createHorizontalStrut(10));
        bBottom.add(b6);

        b.add(Box.createVerticalStrut(10));
        b.add(bTop);
        b.add(Box.createVerticalStrut(5));
        b.add(bBottom);
        b.add(Box.createVerticalStrut(10));

        Box actionPanel = Box.createHorizontalBox();
        actionPanel.add(Box.createHorizontalGlue());

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
        tablePanel.setBackground(java.awt.Color.WHITE);
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        String[] columnHeaders = {
                "Mã nhà cung cấp", "Tên nhà cung cấp", "Địa chỉ",
                "Số điện thoại", "Email", "Logo"
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
        cboSupplierId.addActionListener(this);
        supplierTable.addMouseListener(this);

        new SwingWorker<List<Supplier>, Void>() {
            @Override
            protected List<Supplier> doInBackground() {
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

    // Tạo một nhóm gồm nhãn và ô nhập liệu.
    // Kiểm tra dữ liệu bắt buộc, độ dài và định dạng email.
    private boolean validData() {
        String[] values = readFormValues();
        if (values[0].isEmpty() || values[1].isEmpty()) {
            showMessage("Vui lòng nhập mã và tên nhà cung cấp.");
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

    // Đọc nội dung các ô nhập và loại bỏ khoảng trắng thừa.
    private String[] readFormValues() {
        return new String[]{
                txtSupplierId.getText().trim(),
                txtSupplierName.getText().trim(),
                txtAddress.getText().trim(),
                txtPhoneNumber.getText().trim(),
                txtEmail.getText().trim(),
                txtLogo.getText().trim()
        };
    }

    // Tạo đối tượng nhà cung cấp từ dữ liệu trên form.
    private Supplier readSupplierFromForm() {
        String[] values = readFormValues();
        return new Supplier(values[0], values[1], values[2], values[3], values[4], values[5]);
    }

    // Nạp lại dữ liệu từ DAO lên bảng.
    private void updateTable(List<Supplier> suppliers) {
        supplierTableModel.setRowCount(0);
        for (Supplier supplier : suppliers) {
            supplierTableModel.addRow(supplier.toRow());
        }
    }

    // Cập nhật danh sách mã nhà cung cấp trong combobox tìm kiếm.
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
        List<Supplier> suppliers = supplierDao.getAll();
        updateTable(suppliers);
        updateSupplierComboBox(suppliers);
    }

    // Xóa form và bỏ lựa chọn hiện tại trong bảng, combobox.
    private void clearForm() {
        txtSupplierId.setText("");
        txtSupplierName.setText("");
        txtAddress.setText("");
        txtPhoneNumber.setText("");
        txtEmail.setText("");
        txtLogo.setText("");
        supplierTable.clearSelection();
        updatingSupplierComboBox = true;
        try {
            cboSupplierId.setSelectedIndex(-1);
        } finally {
            updatingSupplierComboBox = false;
        }
    }

    // Hiển thị thông tin một nhà cung cấp lên các ô nhập.
    private void showSupplier(Supplier supplier) {
        txtSupplierId.setText(supplier.getSupplierId());
        txtSupplierName.setText(supplier.getSupplierName());
        txtAddress.setText(supplier.getAddress());
        txtPhoneNumber.setText(supplier.getPhoneNumber());
        txtEmail.setText(supplier.getEmail());
        txtLogo.setText(supplier.getLogo());
    }

    // Hiển thị thông báo cho người dùng.
    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    // Xử lý thao tác thêm, sửa, xóa, làm mới và tìm mã.
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
            if (supplierDao.findById(supplier.getSupplierId()) != null) {
                showMessage("Mã nhà cung cấp đã tồn tại.");
                return;
            }
            if (!supplierDao.insert(supplier)) {
                showMessage("Mã nhà cung cấp đã tồn tại.");
                return;
            }
            refreshSupplierData();
            clearForm();
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
                if (supplierDao.delete(supplierId)) {
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
            if (!supplierDao.update(oldId, updatedSupplier)) {
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

    // Chọn dòng tương ứng với mã nhà cung cấp trong bảng.
    private void selectSupplierInTable(String supplierId) {
        for (int row = 0; row < supplierTableModel.getRowCount(); row++) {
            if (supplierId.equals(supplierTableModel.getValueAt(row, 0).toString())) {
                supplierTable.setRowSelectionInterval(row, row);
                supplierTable.scrollRectToVisible(supplierTable.getCellRect(row, 0, true));
                return;
            }
        }
    }

    // Nạp thông tin lên form khi người dùng nhấp vào một dòng trong bảng.
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

    public static void main(String[] args) {
        GUI.main(args);
    }

}
