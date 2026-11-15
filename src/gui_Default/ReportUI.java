package gui_Default;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import dao.ReportDAO;

public class ReportUI extends JPanel implements ActionListener {

    private JButton btnWeek;
    private JButton btnMonth;
    private JButton btnPrevious;
    private JButton btnNext;

    private JLabel lblRevenue;
    private JLabel lblImportCost;
    private JLabel lblProfit;
    private JLabel lblReportStatus;
    private JLabel lblPeriod;

    private JTable tblTop5;
    private DefaultTableModel modelTop5;
    private JTable tblImport;
    private DefaultTableModel modelImport;
    private JPanel pRevenueChart;

    private ReportDAO reportDAO = new ReportDAO();
    private LocalDate reportDate = LocalDate.now();
    private boolean theoTuan = false;
    private boolean daTaiNgay = false;

    private Color bgColor = new Color(248, 247, 255);
    private Color primaryColor = new Color(94, 53, 165);

    public ReportUI() {
        setLayout(new BorderLayout(10, 10));
        setBackground(bgColor);

        JPanel pNorth = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 12));
        pNorth.setBackground(new Color(234, 230, 250));
        JLabel lblTitle = new JLabel("BÁO CÁO & THỐNG KÊ");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(primaryColor);
        java.net.URL iconURL = getClass().getResource("/img/report.jpg");
        if (iconURL != null) {
            ImageIcon icon = new ImageIcon(iconURL);
            Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            lblTitle.setIcon(new ImageIcon(img));
        }
        pNorth.add(lblTitle);
        add(pNorth, BorderLayout.NORTH);

        JPanel pKPI = new JPanel(new GridLayout(1, 3, 12, 0));
        pKPI.setBackground(bgColor);

        lblRevenue = new JLabel("—", SwingConstants.CENTER);
        lblImportCost = new JLabel("—", SwingConstants.CENTER);
        lblProfit = new JLabel("—", SwingConstants.CENTER);

        pKPI.add(createSummaryCard("DOANH THU BÁN GIÀY", lblRevenue));
        pKPI.add(createSummaryCard("CHI PHÍ NHẬP KHO", lblImportCost));
        pKPI.add(createSummaryCard("LỢI NHUẬN TẠM TÍNH", lblProfit));

        JPanel pFilter = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 7));
        pFilter.setBackground(new Color(230, 216, 255));

        btnWeek = new JButton("Theo tuần");
        btnMonth = new JButton("Theo tháng");
        btnPrevious = new JButton("Kỳ trước");
        btnNext = new JButton("Kỳ sau");
        lblPeriod = new JLabel("");

        btnWeek.setPreferredSize(new Dimension(105, 32));
        btnMonth.setPreferredSize(new Dimension(110, 32));

        pFilter.add(new JLabel("Thống kê:") );
        pFilter.add(btnWeek);
        pFilter.add(btnMonth);
        pFilter.add(btnPrevious);
        pFilter.add(btnNext);
        pFilter.add(lblPeriod);

        pRevenueChart = new JPanel(new BorderLayout());
        pRevenueChart.setBackground(Color.WHITE);

        JPanel pChart = new JPanel(new BorderLayout());
        pChart.setBorder(BorderFactory.createTitledBorder(
                "Biểu đồ doanh thu và chi phí nhập kho"));
        pChart.add(pFilter, BorderLayout.NORTH);
        pChart.add(pRevenueChart, BorderLayout.CENTER);

        JPanel pCenter = new JPanel(new BorderLayout(0, 12));
        pCenter.setBackground(bgColor);
        pCenter.setBorder(BorderFactory.createEmptyBorder(10, 15, 0, 15));
        pCenter.add(pKPI, BorderLayout.NORTH);
        pCenter.add(pChart, BorderLayout.CENTER);
        add(pCenter, BorderLayout.CENTER);

        String[] colsTop = {"STT", "Mã giày", "Tên mẫu giày", "Số lượng bán"};
        modelTop5 = new DefaultTableModel(colsTop, 0);
        tblTop5 = new JTable(modelTop5);
        tblTop5.setDefaultEditor(Object.class, null);
        tblTop5.setRowHeight(27);

        String[] colsImport = {
            "Mã phiếu", "Ngày lập", "Nhà cung cấp", "Số lượng", "Tổng chi phí"
        };
        modelImport = new DefaultTableModel(colsImport, 0);
        tblImport = new JTable(modelImport);
        tblImport.setDefaultEditor(Object.class, null);
        tblImport.setRowHeight(27);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Top 5 giày bán chạy", new JScrollPane(tblTop5));
        tabs.addTab("Chi tiết nhập kho", new JScrollPane(tblImport));
        tabs.setPreferredSize(new Dimension(0, 205));

        lblReportStatus = new JLabel("Đang tải thống kê...");
        JPanel pSouth = new JPanel(new BorderLayout(0, 5));
        pSouth.setBackground(bgColor);
        pSouth.setBorder(BorderFactory.createEmptyBorder(0, 15, 10, 15));
        pSouth.add(tabs, BorderLayout.CENTER);
        pSouth.add(lblReportStatus, BorderLayout.SOUTH);
        add(pSouth, BorderLayout.SOUTH);

        btnWeek.addActionListener(this);
        btnMonth.addActionListener(this);
        btnPrevious.addActionListener(this);
        btnNext.addActionListener(this);

        updateButtonStyle();
        loadReportData();
    }

    private JPanel createSummaryCard(String title, JLabel lblValue) {
        JPanel p = new JPanel(new GridLayout(2, 1));
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));

        JLabel lblTitle = new JLabel(title, SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 12));
        lblTitle.setForeground(primaryColor);
        lblValue.setFont(new Font("Arial", Font.BOLD, 21));

        p.add(lblTitle);
        p.add(lblValue);
        return p;
    }

    private String formatMoney(BigDecimal money) {
        NumberFormat format = NumberFormat.getNumberInstance(new Locale("vi", "VN"));
        format.setMaximumFractionDigits(0);
        return format.format(money) + " đ";
    }

    private void updateButtonStyle() {
        if (theoTuan) {
            btnWeek.setBackground(primaryColor);
            btnWeek.setForeground(Color.WHITE);
            btnMonth.setBackground(Color.WHITE);
            btnMonth.setForeground(primaryColor);
        } else {
            btnMonth.setBackground(primaryColor);
            btnMonth.setForeground(Color.WHITE);
            btnWeek.setBackground(Color.WHITE);
            btnWeek.setForeground(primaryColor);
        }
    }

    private void loadReportData() {
        try {
            if (!daTaiNgay) {
                reportDate = reportDAO.getLatestTransactionDate();
                daTaiNgay = true;
            }

            LocalDate fromDate = reportDAO.getStartDate(reportDate, theoTuan);
            LocalDate toDate = reportDAO.getEndDate(reportDate, theoTuan);

            BigDecimal revenue = reportDAO.getTotalRevenue(fromDate, toDate);
            BigDecimal importCost = reportDAO.getTotalImportCost(fromDate, toDate);
            ArrayList<Object[]> top5 = reportDAO.getTop5SellingShoes(fromDate, toDate);
            ArrayList<Object[]> importOrders = reportDAO.getImportOrders(fromDate, toDate);
            ArrayList<Object[]> chart = reportDAO.getChartData(reportDate, theoTuan);

            showSummary(revenue, importCost);
            loadTop5ToTable(top5);
            loadImportToTable(importOrders);
            showReportChart(chart);

            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            if (theoTuan) {
                String period = fromDate.format(format) + " - "
                        + toDate.minusDays(1).format(format);
                lblPeriod.setText(period);
                lblReportStatus.setText("Thống kê tuần " + period
                        + " | " + importOrders.size() + " phiếu đã nhập");
            } else {
                String period = fromDate.getMonthValue() + "/" + fromDate.getYear();
                lblPeriod.setText("Tháng " + period);
                lblReportStatus.setText("Thống kê tháng " + period
                        + " | " + importOrders.size() + " phiếu đã nhập");
            }

        } catch (SQLException e) {
            lblRevenue.setText("—");
            lblImportCost.setText("—");
            lblProfit.setText("—");
            modelTop5.setRowCount(0);
            modelImport.setRowCount(0);
            pRevenueChart.removeAll();
            pRevenueChart.revalidate();
            pRevenueChart.repaint();
            lblPeriod.setText("");
            lblReportStatus.setText("Không lấy được dữ liệu: " + e.getMessage());
        }
    }

    private void showSummary(BigDecimal revenue, BigDecimal importCost) {
        lblRevenue.setText(formatMoney(revenue));
        lblImportCost.setText(formatMoney(importCost));
        lblProfit.setText(formatMoney(revenue.subtract(importCost)));
    }

    private void loadTop5ToTable(ArrayList<Object[]> list) {
        modelTop5.setRowCount(0);
        int stt = 1;
        for (Object[] shoe : list) {
            modelTop5.addRow(new Object[] {stt, shoe[0], shoe[1], shoe[2]});
            stt++;
        }
    }

    private void loadImportToTable(ArrayList<Object[]> list) {
        modelImport.setRowCount(0);
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (Object[] order : list) {
            LocalDate date = (LocalDate) order[1];
            BigDecimal cost = (BigDecimal) order[4];
            modelImport.addRow(new Object[] {
                order[0],
                date == null ? "" : date.format(format),
                order[2],
                order[3],
                formatMoney(cost)
            });
        }
    }

    private void showReportChart(ArrayList<Object[]> chart) {
        pRevenueChart.removeAll();

        BigDecimal maxValue = BigDecimal.ZERO;
        for (Object[] bar : chart) {
            BigDecimal revenue = (BigDecimal) bar[1];
            BigDecimal cost = (BigDecimal) bar[2];
            if (revenue.compareTo(maxValue) > 0) {
                maxValue = revenue;
            }
            if (cost.compareTo(maxValue) > 0) {
                maxValue = cost;
            }
        }

        if (maxValue.signum() == 0) {
            JLabel lblEmpty = new JLabel("Chưa có giao dịch trong kỳ này",
                    SwingConstants.CENTER);
            lblEmpty.setForeground(Color.GRAY);
            pRevenueChart.add(lblEmpty, BorderLayout.CENTER);
            pRevenueChart.revalidate();
            pRevenueChart.repaint();
            return;
        }

        JPanel pLegend = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        pLegend.setBackground(Color.WHITE);
        JLabel lblSale = new JLabel("■ Doanh thu");
        lblSale.setForeground(new Color(139, 62, 246));
        JLabel lblCost = new JLabel("■ Chi phí nhập kho");
        lblCost.setForeground(new Color(242, 150, 170));
        pLegend.add(lblSale);
        pLegend.add(lblCost);
        pRevenueChart.add(pLegend, BorderLayout.NORTH);

        JPanel pBars = new JPanel(new GridLayout(1, chart.size(), 12, 0));
        pBars.setBackground(Color.WHITE);
        pBars.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        double max = maxValue.doubleValue();

        for (Object[] bar : chart) {
            String name = bar[0].toString();
            BigDecimal revenue = (BigDecimal) bar[1];
            BigDecimal cost = (BigDecimal) bar[2];

            int saleLevel = (int) Math.round(revenue.doubleValue() / max * 1000);
            int costLevel = (int) Math.round(cost.doubleValue() / max * 1000);

            JProgressBar barSale = new JProgressBar(SwingConstants.VERTICAL, 0, 1000);
            barSale.setValue(saleLevel);
            barSale.setForeground(new Color(139, 62, 246));
            barSale.setBorderPainted(false);
            barSale.setToolTipText(name + " - Doanh thu: " + formatMoney(revenue));

            JProgressBar barCost = new JProgressBar(SwingConstants.VERTICAL, 0, 1000);
            barCost.setValue(costLevel);
            barCost.setForeground(new Color(242, 150, 170));
            barCost.setBorderPainted(false);
            barCost.setToolTipText(name + " - Chi phí: " + formatMoney(cost));

            JPanel pDoubleBar = new JPanel(new GridLayout(1, 2, 5, 0));
            pDoubleBar.setBackground(Color.WHITE);
            pDoubleBar.add(barSale);
            pDoubleBar.add(barCost);

            JPanel pGroup = new JPanel(new BorderLayout(0, 5));
            pGroup.setBackground(Color.WHITE);
            pGroup.add(pDoubleBar, BorderLayout.CENTER);
            pGroup.add(new JLabel(name, SwingConstants.CENTER), BorderLayout.SOUTH);
            pBars.add(pGroup);
        }

        pRevenueChart.add(pBars, BorderLayout.CENTER);
        pRevenueChart.revalidate();
        pRevenueChart.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();

        if (o.equals(btnWeek)) {
            theoTuan = true;
            updateButtonStyle();
            loadReportData();
        } else if (o.equals(btnMonth)) {
            theoTuan = false;
            updateButtonStyle();
            loadReportData();
        } else if (o.equals(btnPrevious)) {
            reportDate = theoTuan ? reportDate.minusWeeks(1) : reportDate.minusMonths(1);
            loadReportData();
        } else if (o.equals(btnNext)) {
            reportDate = theoTuan ? reportDate.plusWeeks(1) : reportDate.plusMonths(1);
            loadReportData();
        }
    }

}
