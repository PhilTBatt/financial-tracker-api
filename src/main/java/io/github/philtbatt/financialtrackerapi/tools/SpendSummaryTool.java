package io.github.philtbatt.financialtrackerapi.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.philtbatt.financialtrackerapi.model.Metrics;
import io.github.philtbatt.financialtrackerapi.model.TransactionRecord;
import io.github.philtbatt.financialtrackerapi.service.DynamoDBService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SpendSummaryTool {

    private final DynamoDBService dynamoDBService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public SpendSummaryTool(DynamoDBService dynamoDBService) {
        this.dynamoDBService = dynamoDBService;
    }

    public String execute(String uploadId) {
        TransactionRecord record = dynamoDBService.getById(uploadId);

        if (record == null) {
            return "{\"error\": \"No data found for this upload\"}";
        }

        Metrics metrics = record.getMetrics();

        try {
            Map<String, Long> categoriesInPounds = new HashMap<>();
            metrics.getCategories().getOutTotalByCategory()
                    .forEach((key, value) -> categoriesInPounds.put(key, value / 100));
            String categoriesJson = objectMapper.writeValueAsString(categoriesInPounds);

            return String.format(
                    "{\"totalTransactions\": %d, \"dateRange\": \"%s\", \"avgMonthlySpend\": %.2f, \"avgWeeklySpend\": %.2f, \"categories\": %s}",
                    metrics.getTotalTransactions(),
                    metrics.getDateRangeLabel(),
                    metrics.getAvgMonthlySpend() / 100.0,
                    metrics.getAvgWeeklySpend() / 100.0,
                    categoriesJson
            );
        } catch (Exception e) {
            return "{\"error\": \"Failed to serialize metrics\"}";
        }
    }
}