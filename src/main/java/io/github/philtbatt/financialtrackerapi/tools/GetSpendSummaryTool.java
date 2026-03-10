package io.github.philtbatt.financialtrackerapi.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.philtbatt.financialtrackerapi.model.Metrics;
import io.github.philtbatt.financialtrackerapi.model.TransactionRecord;
import io.github.philtbatt.financialtrackerapi.service.DynamoDBService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class GetSpendSummaryTool {

    private final DynamoDBService dynamoDBService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public GetSpendSummaryTool(DynamoDBService dynamoDBService) {
        this.dynamoDBService = dynamoDBService;
    }

    public String execute(String uploadId) {
        TransactionRecord record = dynamoDBService.getById(uploadId);
        if (record == null) return "{\"error\": \"No data found\"}";

        Metrics metrics = record.getMetrics();

        try {
            Map<String, Object> result = new HashMap<>();
            result.put("totalTransactions", metrics.getTotalTransactions());
            result.put("dateRange", metrics.getDateRangeLabel());
            result.put("avgMonthlySpend", metrics.getAvgMonthlySpend());
            result.put("avgWeeklySpend", metrics.getAvgWeeklySpend());
            result.put("totalByCategory", metrics.getCategories().getOutTotalByCategory());
            result.put("avgByCategory", metrics.getCategories().getAvgOutByCategory());
            result.put("countByCategory", metrics.getCategories().getOutCountByCategory());

            return objectMapper.writeValueAsString(result);
        } catch (Exception e) {
            return "{\"error\": \"Failed to serialize\"}";
        }
    }
}