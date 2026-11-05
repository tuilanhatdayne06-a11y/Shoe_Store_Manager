package gui_Default;

import dao.NhaCungCapDao;
import entity.NhaCungCap;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
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
import javax.swing.table.DefaultTableModel;

public class NhaCungCapUI extends JPanel implements ActionListener, MouseListener {
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

    private final NhaCungCapDao supplierDao = new NhaCungCapDao();
    private DefaultTableModel supplierTableModel;
    private JTable supplierTable;
    private JComboBox<String> cboSupplierId;
    private boolean updatingSupplierComboBox;

    public NhaCungCapUI() {
        setLayout(new BorderLayout());
        setBackground(new java.awt.Color(242, 242, 242));

        JPanel titleBar = new JPanel(new BorderLayout());
        titleBar.setBackground(new java.awt.Color(235, 231, 250));
        titleBar.setBorder(BorderFactory.createEmptyBorder(14, 26, 14, 12));
        JLabel title = new JLabel("Quản lý nhà cung cấp");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(new java.awt.Color(91, 62, 145));
        titleBar.add(title, BorderLayout.CENTER);
        add(titleBar, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new BorderLayout(0, 12));
        mainPanel.setBackground(new java.awt.Color(242, 242, 242));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(14, 22, 12, 20));
        add(mainPanel, BorderLayout.CENTER);

        JPanel editorPanel = new JPanel(new BorderLayout(0, 8));
        editorPanel.setBackground(new java.awt.Color(242, 242, 242));
        editorPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new java.awt.Color(205, 205, 205)),
                BorderFactory.createEmptyBorder(10, 14, 10, 14)));
        mainPanel.add(editorPanel, BorderLayout.NORTH);

        txtSupplierId = new JTextField(25);
        txtSupplierName = new JTextField(25);
        txtAddress = new JTextField(25);
        txtPhoneNumber = new JTextField(25);
        txtEmail = new JTextField(25);
        txtLogo = new JTextField(25);

        Box fields = Box.createVerticalBox();
        JPanel firstRow = new JPanel(new GridLayout(1, 3, 12, 0));
        firstRow.setOpaque(false);
        addInputGroup(firstRow, "Mã nhà cung cấp *", txtSupplierId);
        addInputGroup(firstRow, "Tên nhà cung cấp *", txtSupplierName);
        addInputGroup(firstRow, "Địa chỉ", txtAddress);

        JPanel secondRow = new JPanel(new GridLayout(1, 3, 12, 0));
        secondRow.setOpaque(false);
        addInputGroup(secondRow, "Số điện thoại", txtPhoneNumber);
        addInputGroup(secondRow, "Email", txtEmail);
        addInputGroup(secondRow, "Đường dẫn logo", txtLogo);
        fields.add(firstRow);
        fields.add(Box.createVerticalStrut(8));
        fields.add(secondRow);
        editorPanel.add(fields, BorderLayout.CENTER);

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
    }

    // Tạo một nhóm gồm nhãn và ô nhập liệu.
    private void addInputGroup(JPanel row, String labelText, JTextField input) {
        Box group = Box.createVerticalBox();
        JLabel label = new JLabel(labelText);
        label.setFont(FONT);
        input.setFont(FONT);
        group.add(label);
        group.add(Box.createVerticalStrut(5));
        group.add(input);
        row.add(group);
    }

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
    private NhaCungCap readSupplierFromForm() {
        String[] values = readFormValues();
        return new NhaCungCap(values[0], values[1], values[2], values[3], values[4], values[5]);
    }

    // Nạp lại dữ liệu từ DAO lên bảng.
    private void updateTable() {
        supplierTableModel.setRowCount(0);
        for (NhaCungCap supplier : supplierDao.getAll()) {
            supplierTableModel.addRow(supplier.toRow());
        }
    }

    // Cập nhật danh sách mã nhà cung cấp trong combobox tìm kiếm.
    private void updateSupplierComboBox() {
        Object selectedId = cboSupplierId.getSelectedItem();
        List<NhaCungCap> suppliers = supplierDao.getAll();
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
    private void showSupplier(NhaCungCap supplier) {
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
        Object source = event.getSource();
        if (source == btnAdd) {
            if (!validData()) {
                return;
            }
            NhaCungCap supplier = readSupplierFromForm();
            if (!supplierDao.insert(supplier)) {
                showMessage("Mã nhà cung cấp đã tồn tại.");
                return;
            }
            updateTable();
            updateSupplierComboBox();
            clearForm();
        } else if (source == btnClear) {
            clearForm();
        } else if (source == btnDelete) {
            String supplierId = txtSupplierId.getText().trim();
            NhaCungCap supplier = supplierDao.findById(supplierId);
            if (supplier == null) {
                showMessage("Không tìm thấy mã nhà cung cấp cần xóa.");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc muốn xóa nhà cung cấp " + supplierId + "?",
                    "Xác nhận xóa", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                supplierDao.delete(supplierId);
                updateTable();
                updateSupplierComboBox();
                clearForm();
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
            NhaCungCap updatedSupplier = readSupplierFromForm();
            if (!supplierDao.update(oldId, updatedSupplier)) {
                if (supplierDao.findById(oldId) == null) {
                    showMessage("Không tìm thấy nhà cung cấp cần cập nhật.");
                } else {
                    showMessage("Mã nhà cung cấp đã tồn tại.");
                }
                return;
            }
            updateTable();
            updateSupplierComboBox();
            clearForm();
        } else if (source == cboSupplierId && !updatingSupplierComboBox) {
            Object selectedId = cboSupplierId.getSelectedItem();
            if (selectedId != null) {
                NhaCungCap supplier = supplierDao.findById(selectedId.toString());
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
            NhaCungCap supplier = supplierDao.findById(supplierId);
            if (supplier != null) {
                showSupplier(supplier);
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
