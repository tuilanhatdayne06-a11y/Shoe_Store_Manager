package gui_Default;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelBaoCao extends JPanel {

    private Color bgLight = new Color(253, 240, 255);
    private Color primaryColor = new Color(180, 60, 200);

    private JLabel[] lblKPI = new JLabel[3];
    private JLabel lblBieuDo = new JLabel("Chưa có dữ liệu để hiển thị biểu đồ", SwingConstants.CENTER);
    private JLabel lblTrangThai = new JLabel("Chưa có dữ liệu báo cáo.");
    private JComboBox<String> cboThoiGian = new JComboBox<>(new String[]{"Tuần", "Tháng"});

    private DefaultTableModel model = new DefaultTableModel(
        new String[]{"STT", "Mã giày", "Tên mẫu giày", "Số lượng bán"}, 0
    ) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public PanelBaoCao() {
        setLayout(new BorderLayout(15, 15));
        setBackground(bgLight);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitle = new JLabel("BÁO CÁO & THỐNG KÊ", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(primaryColor);
        add(lblTitle, BorderLayout.NORTH);

        JPanel pnlKPI = new JPanel(new GridLayout(1, 3, 15, 0));
        pnlKPI.setBackground(bgLight);
        String[] tenKPI = {"Doanh thu bán giày", "Chi phí nhập kho", "Lợi nhuận tạm tính"};

        for (int i = 0; i < tenKPI.length; i++) {
            JPanel pnlThe = new JPanel(new GridLayout(2, 1));
            pnlThe.setBackground(Color.WHITE);
            pnlThe.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
            JLabel lblTen = new JLabel(tenKPI[i], SwingConstants.CENTER);
            lblTen.setFont(new Font("Arial", Font.BOLD, 14));
            lblTen.setForeground(primaryColor);
            lblKPI[i] = new JLabel("—", SwingConstants.CENTER);
            lblKPI[i].setFont(new Font("Arial", Font.BOLD, 22));
            pnlThe.add(lblTen);
            pnlThe.add(lblKPI[i]);
            pnlKPI.add(pnlThe);
        }

        JPanel pnlBieuDo = new JPanel(new BorderLayout());
        pnlBieuDo.setBackground(Color.WHITE);
        pnlBieuDo.setBorder(BorderFactory.createTitledBorder(
            "Biểu đồ so sánh doanh thu và chi phí"
        ));

        JPanel pnlLoc = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        pnlLoc.setBackground(Color.WHITE);
        JButton btnXem = taoNut("Xem thống kê");
        JButton btnLamMoi = taoNut("Làm mới");
        pnlLoc.add(new JLabel("Thống kê theo:"));
        pnlLoc.add(cboThoiGian);
        pnlLoc.add(btnXem);
        pnlLoc.add(btnLamMoi);
        pnlBieuDo.add(pnlLoc, BorderLayout.NORTH);

        lblBieuDo.setForeground(Color.GRAY);
        pnlBieuDo.add(lblBieuDo, BorderLayout.CENTER);

        JPanel pnlNoiDung = new JPanel(new BorderLayout(0, 15));
        pnlNoiDung.setBackground(bgLight);
        pnlNoiDung.add(pnlKPI, BorderLayout.NORTH);
        pnlNoiDung.add(pnlBieuDo, BorderLayout.CENTER);
        add(pnlNoiDung, BorderLayout.CENTER);

        JPanel pnlTop = new JPanel(new BorderLayout());
        pnlTop.setBackground(Color.WHITE);
        pnlTop.setBorder(BorderFactory.createTitledBorder("Top mẫu giày bán chạy nhất"));
        pnlTop.setPreferredSize(new Dimension(0, 185));

        JTable tblTop = new JTable(model);
        tblTop.setRowHeight(27);
        tblTop.getTableHeader().setBackground(new Color(235, 210, 240));
        pnlTop.add(new JScrollPane(tblTop), BorderLayout.CENTER);

        JPanel pnlBottom = new JPanel(new BorderLayout(0, 8));
        pnlBottom.setOpaque(false);
        pnlBottom.add(pnlTop, BorderLayout.CENTER);
        lblTrangThai.setForeground(Color.DARK_GRAY);
        pnlBottom.add(lblTrangThai, BorderLayout.SOUTH);
        add(pnlBottom, BorderLayout.SOUTH);

        btnXem.addActionListener(e -> xemThongKe());
        cboThoiGian.addActionListener(e -> xemThongKe());
        btnLamMoi.addActionListener(e -> {
            cboThoiGian.setSelectedIndex(0);
            xoaSoLieu();
            lblTrangThai.setText("Đã làm mới báo cáo.");
        });
    }

    private JButton taoNut(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(primaryColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        return btn;
    }

    private void xoaSoLieu() {
        for (JLabel lbl : lblKPI) {
            lbl.setText("—");
        }
        model.setRowCount(0);
        lblBieuDo.setText("Chưa có dữ liệu để hiển thị biểu đồ");
    }

    private void xemThongKe() {
        String thoiGian = cboThoiGian.getSelectedItem().toString().toLowerCase();
        xoaSoLieu();
        lblBieuDo.setText("Chưa có dữ liệu thống kê theo " + thoiGian);
        lblTrangThai.setText("Đang xem thống kê theo " + thoiGian + ".");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Báo cáo - Shoe Store");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setContentPane(new PanelBaoCao());
            frame.setSize(1100, 650);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
