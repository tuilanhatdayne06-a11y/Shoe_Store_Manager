package gui_Default;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class BaoCaoUI extends JPanel implements ActionListener {// Đặt tên class sai

    private JComboBox<String> cbThoiGian;
    private JButton btnXem, btnLamMoi;
    private JLabel lblDoanhThu, lblChiPhi, lblLoiNhuan, lblBieuDo, lblTrangThai;
    private JTable table;
    private DefaultTableModel modelTable;

    private Color mauNen = new Color(253, 240, 255);
    private Color mauChinh = new Color(180, 60, 200);

    public BaoCaoUI() {
        setLayout(new BorderLayout(12, 12));
        setBackground(mauNen);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // ===== TIEU DE =====
        JLabel lblTieuDe = new JLabel("BÁO CÁO & THỐNG KÊ", SwingConstants.CENTER);
        lblTieuDe.setFont(new Font("Arial", Font.BOLD, 24));
        lblTieuDe.setForeground(mauChinh);
        add(lblTieuDe, BorderLayout.NORTH);


        JPanel pKPI = new JPanel(new GridLayout(1, 3, 12, 0));
        pKPI.setBackground(mauNen);

        lblDoanhThu = new JLabel("—", SwingConstants.CENTER);
        lblChiPhi = new JLabel("—", SwingConstants.CENTER);
        lblLoiNhuan = new JLabel("—", SwingConstants.CENTER);

        pKPI.add(taoThe("DOANH THU BÁN GIÀY", lblDoanhThu));
        pKPI.add(taoThe("CHI PHÍ NHẬP KHO", lblChiPhi));
        pKPI.add(taoThe("LỢI NHUẬN TẠM TÍNH", lblLoiNhuan));

        JPanel pLoc = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 7));
        pLoc.setBackground(Color.WHITE);
        cbThoiGian = new JComboBox<String>(new String[] {"Tuần", "Tháng"});
        btnXem = taoNut("Xem thống kê");
        btnLamMoi = taoNut("Làm mới");
        pLoc.add(new JLabel("Thống kê theo:"));
        pLoc.add(cbThoiGian);
        pLoc.add(btnXem);
        pLoc.add(btnLamMoi);


        JPanel pBieuDo = new JPanel(new BorderLayout());
        pBieuDo.setBackground(Color.WHITE);
        pBieuDo.setBorder(BorderFactory.createTitledBorder(
                "Biểu đồ doanh thu và chi phí theo tuần/tháng"));
        pBieuDo.add(pLoc, BorderLayout.NORTH);

        lblBieuDo = new JLabel("Chưa có dữ liệu thống kê", SwingConstants.CENTER);
        lblBieuDo.setFont(new Font("Arial", Font.PLAIN, 16));
        lblBieuDo.setForeground(Color.GRAY);
        pBieuDo.add(lblBieuDo, BorderLayout.CENTER);

        JPanel pGiua = new JPanel(new BorderLayout(0, 12));
        pGiua.setBackground(mauNen);
        pGiua.add(pKPI, BorderLayout.NORTH);
        pGiua.add(pBieuDo, BorderLayout.CENTER);
        add(pGiua, BorderLayout.CENTER);

        String[] cot = {"STT", "Mã giày", "Tên mẫu giày", "Số lượng bán"};
        modelTable = new DefaultTableModel(cot, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(modelTable);
        table.setRowHeight(27);
        table.getTableHeader().setBackground(new Color(235, 210, 240));

        JPanel pTop = new JPanel(new BorderLayout());
        pTop.setBackground(Color.WHITE);
        pTop.setPreferredSize(new Dimension(0, 170));
        pTop.setBorder(BorderFactory.createTitledBorder("Top mẫu giày bán chạy nhất"));
        pTop.add(new JScrollPane(table), BorderLayout.CENTER);

        lblTrangThai = new JLabel("Chưa có dữ liệu báo cáo.");
        lblTrangThai.setForeground(Color.DARK_GRAY);

        JPanel pDuoi = new JPanel(new BorderLayout(0, 5));
        pDuoi.setOpaque(false);
        pDuoi.add(pTop, BorderLayout.CENTER);
        pDuoi.add(lblTrangThai, BorderLayout.SOUTH);
        add(pDuoi, BorderLayout.SOUTH);

        btnXem.addActionListener(this);
        btnLamMoi.addActionListener(this);
    }

    private JPanel taoThe(String ten, JLabel lblSoLieu) {
        JPanel p = new JPanel(new GridLayout(2, 1));
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblTen = new JLabel(ten, SwingConstants.CENTER);
        lblTen.setForeground(mauChinh);
        lblTen.setFont(new Font("Arial", Font.BOLD, 13));
        lblSoLieu.setFont(new Font("Arial", Font.BOLD, 22));

        p.add(lblTen);
        p.add(lblSoLieu);
        return p;
    }

    private JButton taoNut(String ten) {
        JButton btn = new JButton(ten);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setBackground(mauChinh);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        return btn;
    }

    private void xemThongKe() {
        String thoiGian = cbThoiGian.getSelectedItem().toString().toLowerCase();
        lblBieuDo.setText("Chưa có dữ liệu thống kê theo " + thoiGian);
        lblTrangThai.setText("Đang xem báo cáo theo " + thoiGian + ".");
    }

    private void lamMoi() {
        cbThoiGian.setSelectedIndex(0);
        lblDoanhThu.setText("—");
        lblChiPhi.setText("—");
        lblLoiNhuan.setText("—");
        lblBieuDo.setText("Chưa có dữ liệu thống kê");
        modelTable.setRowCount(0);
        lblTrangThai.setText("Đã làm mới báo cáo.");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnXem)) {
            xemThongKe();
        } else if (o.equals(btnLamMoi)) {
            lamMoi();
        }
    }


}