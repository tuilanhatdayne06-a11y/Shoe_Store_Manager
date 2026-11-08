package entity;

import java.math.BigDecimal;

public class Report {

    private BigDecimal totalRevenue;
    private BigDecimal totalImportCost;

    public Report(BigDecimal totalRevenue, BigDecimal totalImportCost) {
        this.totalRevenue = totalRevenue;
        this.totalImportCost = totalImportCost;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public BigDecimal getTotalImportCost() {
        return totalImportCost;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public void setTotalImportCost(BigDecimal totalImportCost) {
        this.totalImportCost = totalImportCost;
    }

    public BigDecimal getProfit() {
        return totalRevenue.subtract(totalImportCost);
    }

    @Override
    public String toString() {
        return "Report [totalRevenue=" + totalRevenue
                + ", totalImportCost=" + totalImportCost
                + ", profit=" + getProfit() + "]";
    }
}
