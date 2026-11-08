
package gui_Default;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
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
    private RevenueChartPanel pRevenueChart;

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

        pKPI.add(taoKhungThongKe("DOANH THU BÁN GIÀY", lblRevenue));
        pKPI.add(taoKhungThongKe("CHI PHÍ NHẬP KHO", lblImportCost));
        pKPI.add(taoKhungThongKe("LỢI NHUẬN TẠM TÍNH", lblProfit));

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
        pRevenueChart = new RevenueChartPanel();
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

    private JPanel taoKhungThongKe(String tieuDe, JLabel lblSoLieu) {
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

    private String dinhDangTien(BigDecimal tien) {
        NumberFormat format = NumberFormat.getNumberInstance(
                new Locale("vi", "VN"));
        format.setMaximumFractionDigits(0);

        return format.format(tien) + " đ";
    }

    // Lay du lieu thong ke tu ReportDAO
    private void xemThongKe() {
        boolean theoTuan = cboReportPeriod.getSelectedItem().equals("Tuần");

        try {
            LocalDate tuNgay = reportDAO.getNgayBatDau(theoTuan);
            LocalDate denNgay = reportDAO.getNgayKetThuc(theoTuan);

            Report tong = reportDAO.getSummary(tuNgay, denNgay);
            ArrayList<ReportDAO.TopShoe> ds = reportDAO.getTop5(tuNgay, denNgay);
            ArrayList<ReportDAO.ChartBar> cot = reportDAO.getBieuDo(theoTuan);

            lblRevenue.setText(dinhDangTien(tong.getTotalRevenue()));
            lblImportCost.setText(dinhDangTien(tong.getTotalImportCost()));
            lblProfit.setText(dinhDangTien(tong.getProfit()));

            // Hien thi top 5 giay ban chay
            modelTable.setRowCount(0);

            int stt = 1;
            for (ReportDAO.TopShoe giay : ds) {
                modelTable.addRow(new Object[] {
                    stt, giay.ma, giay.ten, giay.soLuong
                });
                stt++;
            }

            // Hien thi bieu do
            pRevenueChart.setDanhSach(cot);

            if (theoTuan) {
                lblReportStatus.setText("Thống kê tuần hiện tại");
            } else {
                lblReportStatus.setText("Thống kê tháng hiện tại");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Không lấy được báo cáo: " + e.getMessage());
        }
    }

    private void lamMoi() {
        xemThongKe();
    }

    // Ve bieu do doanh thu va chi phi
    private class RevenueChartPanel extends JPanel {

        private ArrayList<ReportDAO.ChartBar> chartBars =
                new ArrayList<ReportDAO.ChartBar>();

        public void setDanhSach(ArrayList<ReportDAO.ChartBar> ds) {
            chartBars = ds;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            int rong = getWidth();
            int cao = getHeight();
            int trucX = cao - 35;
            int trucY = 40;

            BigDecimal giaTriLonNhat = BigDecimal.ZERO;

            for (ReportDAO.ChartBar cot : chartBars) {
                if (cot.doanhThu.compareTo(giaTriLonNhat) > 0) {
                    giaTriLonNhat = cot.doanhThu;
                }

                if (cot.chiPhi.compareTo(giaTriLonNhat) > 0) {
                    giaTriLonNhat = cot.chiPhi;
                }
            }

            if (chartBars.isEmpty() || giaTriLonNhat.signum() == 0) {
                g.setColor(Color.GRAY);
                g.drawString("Chưa có dữ liệu để hiển thị",
                        rong / 2 - 85, cao / 2);
                return;
            }

            g.setColor(Color.LIGHT_GRAY);
            g.drawLine(trucY, trucX, rong - 15, trucX);

            int khoang = Math.max(1, (rong - 70) / chartBars.size());
            int rongCot = Math.max(6, Math.min(20, khoang / 4));
            int chieuCao = Math.max(1, cao - 85);

            double max = giaTriLonNhat.doubleValue();

            for (int i = 0; i < chartBars.size(); i++) {
                ReportDAO.ChartBar cot = chartBars.get(i);

                int x = 45 + i * khoang + khoang / 2 - rongCot;

                int cotBan = (int) (
                    cot.doanhThu.doubleValue() / max * chieuCao
                );

                int cotNhap = (int) (
                    cot.chiPhi.doubleValue() / max * chieuCao
                );

                g.setColor(new Color(139, 62, 246));
                g.fillRect(x, trucX - cotBan, rongCot, cotBan);

                g.setColor(new Color(242, 150, 170));
                g.fillRect(x + rongCot + 2,
                        trucX - cotNhap, rongCot, cotNhap);

                g.setColor(Color.DARK_GRAY);
                g.drawString(cot.nhan, x - 4, trucX + 18);
            }

            // Chu thich bieu do
            g.setColor(new Color(139, 62, 246));
            g.fillRect(20, 12, 12, 12);

            g.setColor(Color.DARK_GRAY);
            g.drawString("Doanh thu", 37, 22);

            g.setColor(new Color(242, 150, 170));
            g.fillRect(122, 12, 12, 12);

            g.setColor(Color.DARK_GRAY);
            g.drawString("Chi phí nhập", 140, 22);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();

        if (o.equals(btnViewReport)) {
            xemThongKe();
        } else if (o.equals(btnRefreshReport)) {
            lamMoi();
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
