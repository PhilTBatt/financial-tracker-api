package io.github.philtbatt.financialtrackerapi.model;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;

import java.util.List;

@DynamoDbBean
public class Metrics {

    private Integer totalTransactions;

    private Double totalSpend;
    private Double avgMonthlySpend;

    private String topCategory;
    private Double topCategorySpend;

    private String dateRangeLabel;

    // For graphs: one point per month (or per day if you prefer)
    private List<SpendPoint> spendHistory;

    @DynamoDbAttribute("total_transactions")
    public Integer getTotalTransactions() { return totalTransactions; }
    public void setTotalTransactions(Integer totalTransactions) { this.totalTransactions = totalTransactions; }

    @DynamoDbAttribute("total_spend")
    public Double getTotalSpend() { return totalSpend; }
    public void setTotalSpend(Double totalSpend) { this.totalSpend = totalSpend; }

    @DynamoDbAttribute("avg_monthly_spend")
    public Double getAvgMonthlySpend() { return avgMonthlySpend; }
    public void setAvgMonthlySpend(Double avgMonthlySpend) { this.avgMonthlySpend = avgMonthlySpend; }

    @DynamoDbAttribute("top_category")
    public String getTopCategory() { return topCategory; }
    public void setTopCategory(String topCategory) { this.topCategory = topCategory; }

    @DynamoDbAttribute("top_category_spend")
    public Double getTopCategorySpend() { return topCategorySpend; }
    public void setTopCategorySpend(Double topCategorySpend) { this.topCategorySpend = topCategorySpend; }

    @DynamoDbAttribute("date_range_label")
    public String getDateRangeLabel() { return dateRangeLabel; }
    public void setDateRangeLabel(String dateRangeLabel) { this.dateRangeLabel = dateRangeLabel; }

    @DynamoDbAttribute("spend_history")
    public List<SpendPoint> getSpendHistory() { return spendHistory; }
    public void setSpendHistory(List<SpendPoint> spendHistory) { this.spendHistory = spendHistory; }

    @DynamoDbBean
    public static class SpendPoint {
        private String period;   // "2026-01" (monthly) or "2026-01-23" (daily)
        private Double amount;   // spend in that period

        @DynamoDbAttribute("period")
        public String getPeriod() { return period; }
        public void setPeriod(String period) { this.period = period; }

        @DynamoDbAttribute("amount")
        public Double getAmount() { return amount; }
        public void setAmount(Double amount) { this.amount = amount; }
    }
}