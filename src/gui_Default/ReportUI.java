
package gui_Default;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import dao.ReportDAO;
import entity.Report;

public class ReportUI extends JPanel implements ActionListener {

    private JComboBox<String> cboReportPeriod;
    private JButton btnViewReport;
    private JButton btnRefreshReport;
    private JLabel lblRevenue;
    private JLabel lblImportCost;
    private JLabel lblProfit;
    private JLabel lblReportStatus;
    private JTable table;
    private DefaultTableModel modelTable;
    private JPanel pRevenueChart;

    private ReportDAO reportDAO = new ReportDAO();

    private Color bgColor = new Color(248, 247, 255);
    private Color primaryColor = new Color(94, 53, 165);

    public ReportUI() {
        setLayout(new BorderLayout(10, 10));
        setBackground(bgColor);

        JPanel pNorth = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 12));
        pNorth.setBackground(new Color(234, 230, 250));

        JLabel lblTieuDe = new JLabel("BÁO CÁO & THỐNG KÊ");
        lblTieuDe.setFont(new Font("Arial", Font.BOLD, 24));
        lblTieuDe.setForeground(primaryColor);

        pNorth.add(lblTieuDe);
        add(pNorth, BorderLayout.NORTH);

        // Ba o thong ke
        JPanel pKPI = new JPanel(new GridLayout(1, 3, 12, 0));
        pKPI.setBackground(bgColor);

        lblRevenue = new JLabel("—", SwingConstants.CENTER);
        lblImportCost = new JLabel("—", SwingConstants.CENTER);
        lblProfit = new JLabel("—", SwingConstants.CENTER);

        pKPI.add(createSummaryCard("DOANH THU BÁN GIÀY", lblRevenue));
        pKPI.add(createSummaryCard("CHI PHÍ NHẬP KHO", lblImportCost));
        pKPI.add(createSummaryCard("LỢI NHUẬN TẠM TÍNH", lblProfit));

        JPanel pLoc = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 7));
        pLoc.setBackground(new Color(230, 216, 255));

        cboReportPeriod = new JComboBox<String>(new String[] {"Tuần", "Tháng"});
        btnViewReport = new JButton("Xem thống kê");
        btnRefreshReport = new JButton("Làm mới");

        pLoc.add(new JLabel("Xem theo:"));
        pLoc.add(cboReportPeriod);
        pLoc.add(btnViewReport);
        pLoc.add(btnRefreshReport);

        // Bieu do
        pRevenueChart = new JPanel(new BorderLayout());
        pRevenueChart.setBackground(Color.WHITE);

        JPanel pChart = new JPanel(new BorderLayout());
        pChart.setBackground(Color.WHITE);
        pChart.setBorder(BorderFactory.createTitledBorder(
                "Biểu đồ doanh thu và chi phí"));

        pChart.add(pLoc, BorderLayout.NORTH);
        pChart.add(pRevenueChart, BorderLayout.CENTER);

        JPanel pCenter = new JPanel(new BorderLayout(0, 12));
        pCenter.setBackground(bgColor);
        pCenter.setBorder(BorderFactory.createEmptyBorder(10, 15, 0, 15));

        pCenter.add(pKPI, BorderLayout.NORTH);
        pCenter.add(pChart, BorderLayout.CENTER);

        add(pCenter, BorderLayout.CENTER);

        // Bang top 5 giay
        String[] cols = {
            "STT", "Mã giày", "Tên mẫu giày", "Số lượng bán"
        };

        modelTable = new DefaultTableModel(cols, 0);
        table = new JTable(modelTable);
        table.setDefaultEditor(Object.class, null);
        table.setRowHeight(27);
        table.getTableHeader().setBackground(new Color(230, 216, 255));

        JPanel pTop = new JPanel(new BorderLayout());
        pTop.setBackground(Color.WHITE);
        pTop.setPreferredSize(new Dimension(0, 180));
        pTop.setBorder(BorderFactory.createTitledBorder(
                "Top 5 mẫu giày bán chạy"));

        pTop.add(new JScrollPane(table), BorderLayout.CENTER);

        lblReportStatus = new JLabel(
                "Chọn Tuần hoặc Tháng rồi nhấn Xem thống kê.");

        JPanel pSouth = new JPanel(new BorderLayout(0, 5));
        pSouth.setBackground(bgColor);
        pSouth.setBorder(BorderFactory.createEmptyBorder(0, 15, 10, 15));

        pSouth.add(pTop, BorderLayout.CENTER);
        pSouth.add(lblReportStatus, BorderLayout.SOUTH);

        add(pSouth, BorderLayout.SOUTH);

        btnViewReport.addActionListener(this);
        btnRefreshReport.addActionListener(this);
    }

    private JPanel createSummaryCard(String tieuDe, JLabel lblSoLieu) {
        JPanel p = new JPanel(new GridLayout(2, 1));
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));

        JLabel lblTen = new JLabel(tieuDe, SwingConstants.CENTER);
        lblTen.setFont(new Font("Arial", Font.BOLD, 12));
        lblTen.setForeground(primaryColor);

        lblSoLieu.setFont(new Font("Arial", Font.BOLD, 22));

        p.add(lblTen);
        p.add(lblSoLieu);

        return p;
    }

    private String formatMoney(BigDecimal tien) {
        NumberFormat format = NumberFormat.getNumberInstance(
                new Locale("vi", "VN"));
        format.setMaximumFractionDigits(0);

        return format.format(tien) + " đ";
    }

    // Lay du lieu thong ke tu ReportDAO
    private void loadReportData() {
        boolean theoTuan = cboReportPeriod.getSelectedItem().equals("Tuần");

        try {
            LocalDate tuNgay = reportDAO.getReportStartDate(theoTuan);
            LocalDate denNgay = reportDAO.getReportEndDate(theoTuan);

            Report tong = reportDAO.getRevenueAndImportSummary(tuNgay, denNgay);
            ArrayList<Object[]> ds = reportDAO.getTop5SellingShoes(tuNgay, denNgay);
            ArrayList<Object[]> cot = reportDAO.getRevenueAndImportChart(theoTuan);

            showSummary(tong);
            loadTop5ToTable(ds);
            showReportChart(cot);

            if (theoTuan) {
                lblReportStatus.setText("Thống kê tuần hiện tại");
            } else {
                lblReportStatus.setText("Thống kê tháng hiện tại");
            }

        } catch (SQLException e) {
            showMessage(
                    "Không lấy được báo cáo: " + e.getMessage());
        }
    }

    private void showSummary(Report report) {
        lblRevenue.setText(formatMoney(report.getTotalRevenue()));
        lblImportCost.setText(formatMoney(report.getTotalImportCost()));
        lblProfit.setText(formatMoney(report.getProfit()));
    }

    private void loadTop5ToTable(ArrayList<Object[]> list) {
        modelTable.setRowCount(0);
        int stt = 1;
        for (Object[] giay : list) {
            modelTable.addRow(new Object[] {
                stt, giay[0], giay[1], giay[2]
            });
            stt++;
        }
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    private void refreshReport() {
        loadReportData();
    }

    // Hien thi bieu do bang cac thanh JProgressBar
    private void showReportChart(ArrayList<Object[]> dsCot) {
        pRevenueChart.removeAll();

        BigDecimal giaTriLonNhat = BigDecimal.ZERO;
        for (Object[] cot : dsCot) {
            BigDecimal doanhThu = (BigDecimal) cot[1];
            BigDecimal chiPhi = (BigDecimal) cot[2];
            if (doanhThu.compareTo(giaTriLonNhat) > 0) {
                giaTriLonNhat = doanhThu;
            }
            if (chiPhi.compareTo(giaTriLonNhat) > 0) {
                giaTriLonNhat = chiPhi;
            }
        }

        if (dsCot.isEmpty() || giaTriLonNhat.signum() == 0) {
            JLabel lblTrong = new JLabel("Chưa có dữ liệu để hiển thị", SwingConstants.CENTER);
            lblTrong.setForeground(Color.GRAY);
            pRevenueChart.add(lblTrong, BorderLayout.CENTER);
            pRevenueChart.revalidate();
            pRevenueChart.repaint();
            return;
        }

        JPanel pChuThich = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        pChuThich.setBackground(Color.WHITE);
        JLabel lblBan = new JLabel("■ Doanh thu");
        lblBan.setForeground(new Color(139, 62, 246));
        JLabel lblNhap = new JLabel("■ Chi phí nhập");
        lblNhap.setForeground(new Color(242, 150, 170));
        pChuThich.add(lblBan);
        pChuThich.add(lblNhap);
        pRevenueChart.add(pChuThich, BorderLayout.NORTH);

        JPanel pCacCot = new JPanel(new GridLayout(1, dsCot.size(), 12, 0));
        pCacCot.setBackground(Color.WHITE);
        pCacCot.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        double max = giaTriLonNhat.doubleValue();

        for (Object[] cot : dsCot) {
            String nhan = cot[0].toString();
            BigDecimal doanhThu = (BigDecimal) cot[1];
            BigDecimal chiPhi = (BigDecimal) cot[2];

            int mucDoBan = (int) Math.round(doanhThu.doubleValue() / max * 1000);
            int mucDoNhap = (int) Math.round(chiPhi.doubleValue() / max * 1000);

            JProgressBar barBan = new JProgressBar(SwingConstants.VERTICAL, 0, 1000);
            barBan.setValue(mucDoBan);
            barBan.setForeground(new Color(139, 62, 246));
            barBan.setBackground(new Color(247, 247, 247));
            barBan.setBorderPainted(false);

            JProgressBar barNhap = new JProgressBar(SwingConstants.VERTICAL, 0, 1000);
            barNhap.setValue(mucDoNhap);
            barNhap.setForeground(new Color(242, 150, 170));
            barNhap.setBackground(new Color(247, 247, 247));
            barNhap.setBorderPainted(false);

            JPanel pHaiCot = new JPanel(new GridLayout(1, 2, 5, 0));
            pHaiCot.setBackground(Color.WHITE);
            pHaiCot.add(barBan);
            pHaiCot.add(barNhap);

            JPanel pNhom = new JPanel(new BorderLayout(0, 5));
            pNhom.setBackground(Color.WHITE);
            pNhom.add(pHaiCot, BorderLayout.CENTER);
            pNhom.add(new JLabel(nhan, SwingConstants.CENTER), BorderLayout.SOUTH);
            pCacCot.add(pNhom);
        }

        pRevenueChart.add(pCacCot, BorderLayout.CENTER);
        pRevenueChart.revalidate();
        pRevenueChart.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();

        if (o.equals(btnViewReport)) {
            loadReportData();
        } else if (o.equals(btnRefreshReport)) {
            refreshReport();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Báo cáo - Shoe Store");
        frame.setSize(1150, 680);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.add(new ReportUI());
        frame.setVisible(true);
    }
}
