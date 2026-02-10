package io.github.philtbatt.financialtrackerapi.model;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

import java.util.List;
import java.util.Map;

@DynamoDbBean
public class Metrics {
    private Integer totalTransactions;
    private String dateRangeLabel;
    private PeriodMetrics monthly;
    private PeriodMetrics weekly;
    private Buckets buckets;
    private Long avgMonthlySpend;
    private Long avgWeeklySpend;
    private Categories categories;
    private DailySeries daily;
    private RollingSeries rollingOut7d;
    private List<RankedTransaction> topOutgoingTransactions;

    public Integer getTotalTransactions() { return totalTransactions; }
    public void setTotalTransactions(Integer totalTransactions) { this.totalTransactions = totalTransactions; }

    public String getDateRangeLabel() { return dateRangeLabel; }
    public void setDateRangeLabel(String dateRangeLabel) { this.dateRangeLabel = dateRangeLabel; }

    public PeriodMetrics getMonthly() { return monthly; }
    public void setMonthly(PeriodMetrics monthly) { this.monthly = monthly; }

    public PeriodMetrics getWeekly() { return weekly; }
    public void setWeekly(PeriodMetrics weekly) { this.weekly = weekly; }

    public Buckets getBuckets() { return buckets; }
    public void setBuckets(Buckets buckets) { this.buckets = buckets; }

    public Long getAvgMonthlySpend() { return avgMonthlySpend; }
    public void setAvgMonthlySpend(Long avgMonthlySpend) { this.avgMonthlySpend = avgMonthlySpend; }

    public Long getAvgWeeklySpend() { return avgWeeklySpend; }
    public void setAvgWeeklySpend(Long avgWeeklySpend) { this.avgWeeklySpend = avgWeeklySpend; }

    public Categories getCategories() { return categories; }
    public void setCategories(Categories categories) { this.categories = categories; }

    public DailySeries getDaily() { return daily; }
    public void setDaily(DailySeries daily) { this.daily = daily; }

    public RollingSeries getRollingOut7d() { return rollingOut7d; }
    public void setRollingOut7d(RollingSeries rollingOut7d) { this.rollingOut7d = rollingOut7d; }

    public List<RankedTransaction> getTopOutgoingTransactions() { return topOutgoingTransactions; }
    public void setTopOutgoingTransactions(List<RankedTransaction> topOutgoingTransactions) { this.topOutgoingTransactions = topOutgoingTransactions; }

    @DynamoDbBean
    public static class PeriodMetrics {
        private List<String> labels;
        private List<Long> in;
        private List<Long> out;
        private Long avgOut;
        private Map<String, List<Long>> byCategoryOut;

        public List<String> getLabels() { return labels; }
        public void setLabels(List<String> labels) { this.labels = labels; }

        public List<Long> getIn() { return in; }
        public void setIn(List<Long> in) { this.in = in; }

        public List<Long> getOut() { return out; }
        public void setOut(List<Long> out) { this.out = out; }

        public Long getAvgOut() { return avgOut; }
        public void setAvgOut(Long avgOut) { this.avgOut = avgOut; }

        public Map<String, List<Long>> getByCategoryOut() { return byCategoryOut; }
        public void setByCategoryOut(Map<String, List<Long>> byCategoryOut) { this.byCategoryOut = byCategoryOut; }
    }

    @DynamoDbBean
    public static class Categories {
        private Map<String, Long> outTotalByCategory;
        private Map<String, Integer> outCountByCategory;
        private Map<String, Long> avgOutByCategory;
        private BucketSeriesByCategory outSizeBucketsByCategory;

        public Map<String, Long> getOutTotalByCategory() { return outTotalByCategory; }
        public void setOutTotalByCategory(Map<String, Long> outTotalByCategory) { this.outTotalByCategory = outTotalByCategory; }

        public Map<String, Integer> getOutCountByCategory() { return outCountByCategory; }
        public void setOutCountByCategory(Map<String, Integer> outCountByCategory) { this.outCountByCategory = outCountByCategory; }

        public Map<String, Long> getAvgOutByCategory() { return avgOutByCategory; }
        public void setAvgOutByCategory(Map<String, Long> avgOutByCategory) { this.avgOutByCategory = avgOutByCategory; }

        public BucketSeriesByCategory getOutSizeBucketsByCategory() { return outSizeBucketsByCategory; }
        public void setOutSizeBucketsByCategory(BucketSeriesByCategory outSizeBucketsByCategory) { this.outSizeBucketsByCategory = outSizeBucketsByCategory; }
    }

    @DynamoDbBean
    public static class BucketSeriesByCategory {
        private List<String> labels;
        private Map<String, List<Integer>> counts;

        public List<String> getLabels() { return labels; }
        public void setLabels(List<String> labels) { this.labels = labels; }

        public Map<String, List<Integer>> getCounts() { return counts; }
        public void setCounts(Map<String, List<Integer>> counts) { this.counts = counts; }
    }

    @DynamoDbBean
    public static class Buckets {
        private BucketSeries outgoingSize;
        private BucketSeries incomingSize;

        public BucketSeries getOutgoingSize() { return outgoingSize; }
        public void setOutgoingSize(BucketSeries outgoingSize) { this.outgoingSize = outgoingSize; }

        public BucketSeries getIncomingSize() { return incomingSize; }
        public void setIncomingSize(BucketSeries incomingSize) { this.incomingSize = incomingSize; }
    }

    @DynamoDbBean
    public static class BucketSeries {
        private List<String> labels;
        private List<Integer> counts;

        public List<String> getLabels() { return labels; }
        public void setLabels(List<String> labels) { this.labels = labels; }

        public List<Integer> getCounts() { return counts; }
        public void setCounts(List<Integer> counts) { this.counts = counts; }
    }

    @DynamoDbBean
    public static class RollingSeries {
        private Integer window;
        private List<Long> values;

        public Integer getWindow() { return window; }
        public void setWindow(Integer window) { this.window = window; }

        public List<Long> getValues() { return values; }
        public void setValues(List<Long> values) { this.values = values; }
    }

    @DynamoDbBean
    public static class RankedTransaction {
        private String date;
        private Long amount;
        private String description;

        public String getDate() { return date; }
        public void setDate(String date) { this.date = date; }

        public Long getAmount() { return amount; }
        public void setAmount(Long amount) { this.amount = amount; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }
}