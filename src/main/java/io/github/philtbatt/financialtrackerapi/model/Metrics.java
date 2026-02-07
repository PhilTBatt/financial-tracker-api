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

    @DynamoDbAttribute("total_transactions")
    public Integer getTotalTransactions() { return totalTransactions; }
    public void setTotalTransactions(Integer totalTransactions) { this.totalTransactions = totalTransactions; }

    @DynamoDbAttribute("date_range_label")
    public String getDateRangeLabel() { return dateRangeLabel; }
    public void setDateRangeLabel(String dateRangeLabel) { this.dateRangeLabel = dateRangeLabel; }

    @DynamoDbAttribute("monthly")
    public PeriodMetrics getMonthly() { return monthly; }
    public void setMonthly(PeriodMetrics monthly) { this.monthly = monthly; }

    @DynamoDbAttribute("weekly")
    public PeriodMetrics getWeekly() { return weekly; }
    public void setWeekly(PeriodMetrics weekly) { this.weekly = weekly; }

    @DynamoDbAttribute("buckets")
    public Buckets getBuckets() { return buckets; }
    public void setBuckets(Buckets buckets) { this.buckets = buckets; }

    @DynamoDbBean
    public static class PeriodMetrics {
        private List<String> labels;
        private List<Long> in;
        private List<Long> out;
        private Long avgOut;
        private Map<String, List<Long>> byCategoryOut;

        @DynamoDbAttribute("labels")
        public List<String> getLabels() { return labels; }
        public void setLabels(List<String> labels) { this.labels = labels; }

        @DynamoDbAttribute("in")
        public List<Long> getIn() { return in; }
        public void setIn(List<Long> in) { this.in = in; }

        @DynamoDbAttribute("out")
        public List<Long> getOut() { return out; }
        public void setOut(List<Long> out) { this.out = out; }

        @DynamoDbAttribute("avgOut")
        public Long getAvgOut() { return avgOut; }
        public void setAvgOut(Long avgOut) { this.avgOut = avgOut; }

        @DynamoDbAttribute("byCategoryOut")
        public Map<String, List<Long>> getByCategoryOut() { return byCategoryOut; }
        public void setByCategoryOut(Map<String, List<Long>> byCategoryOut) { this.byCategoryOut = byCategoryOut; }
    }

    @DynamoDbBean
    public static class Buckets {
        private BucketSeries outgoingSize;
        private BucketSeries incomingSize;

        @DynamoDbAttribute("outgoingSize")
        public BucketSeries getOutgoingSize() { return outgoingSize; }
        public void setOutgoingSize(BucketSeries outgoingSize) { this.outgoingSize = outgoingSize; }

        @DynamoDbAttribute("incomingSize")
        public BucketSeries getIncomingSize() { return incomingSize; }
        public void setIncomingSize(BucketSeries incomingSize) { this.incomingSize = incomingSize; }
    }

    @DynamoDbBean
    public static class BucketSeries {
        private List<String> labels;
        private List<Integer> counts;

        @DynamoDbAttribute("labels")
        public List<String> getLabels() { return labels; }
        public void setLabels(List<String> labels) { this.labels = labels; }

        @DynamoDbAttribute("counts")
        public List<Integer> getCounts() { return counts; }
        public void setCounts(List<Integer> counts) { this.counts = counts; }
    }
}