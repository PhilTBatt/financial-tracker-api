package io.github.philtbatt.financialtrackerapi.model;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;

import java.util.List;

@DynamoDbBean
public class Metrics {

    private Integer totalTransactions;

    private Long totalSpent;
    private Long avgMonthlySpend;

    private String topCategory;
    private Long topCategorySpent;

    private String dateRangeLabel;

    private List<SpendPoint> monthlySpendHistory;

    @DynamoDbAttribute("total_transactions")
    public Integer getTotalTransactions() { return totalTransactions; }
    public void setTotalTransactions(Integer totalTransactions) { this.totalTransactions = totalTransactions; }

    @DynamoDbAttribute("total_spent")
    public Long getTotalSpent() { return totalSpent; }
    public void setTotalSpent(Long totalSpent) { this.totalSpent = totalSpent; }

    @DynamoDbAttribute("avg_monthly_spend")
    public Long getAvgMonthlySpend() { return avgMonthlySpend; }
    public void setAvgMonthlySpend(Long avgMonthlySpend) { this.avgMonthlySpend = avgMonthlySpend; }

    @DynamoDbAttribute("top_category")
    public String getTopCategory() { return topCategory; }
    public void setTopCategory(String topCategory) { this.topCategory = topCategory; }

    @DynamoDbAttribute("top_category_spent")
    public Long getTopCategorySpent() { return topCategorySpent; }
    public void setTopCategorySpent(Long topCategorySpent) { this.topCategorySpent = topCategorySpent; }

    @DynamoDbAttribute("date_range_label")
    public String getDateRangeLabel() { return dateRangeLabel; }
    public void setDateRangeLabel(String dateRangeLabel) { this.dateRangeLabel = dateRangeLabel; }

    @DynamoDbAttribute("monthly_spend_history")
    public List<SpendPoint> getMonthlySpendHistory() { return monthlySpendHistory; }
    public void setMonthlySpendHistory(List<SpendPoint> monthlySpendHistory) { this.monthlySpendHistory = monthlySpendHistory; }

    @DynamoDbBean
    public static class SpendPoint {
        private String period;
        private Long amount;

        @DynamoDbAttribute("period")
        public String getPeriod() { return period; }
        public void setPeriod(String period) { this.period = period; }

        @DynamoDbAttribute("amount")
        public Long getAmount() { return amount; }
        public void setAmount(Long amount) { this.amount = amount; }
    }
}