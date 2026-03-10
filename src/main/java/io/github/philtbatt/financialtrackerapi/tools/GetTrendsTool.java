package io.github.philtbatt.financialtrackerapi.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.philtbatt.financialtrackerapi.model.Metrics;
import io.github.philtbatt.financialtrackerapi.model.TransactionRecord;
import io.github.philtbatt.financialtrackerapi.service.DynamoDBService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class GetTrendsTool {

    private final DynamoDBService dynamoDBService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public GetTrendsTool(DynamoDBService dynamoDBService) {
        this.dynamoDBService = dynamoDBService;
    }

    public String execute(String uploadId) {
        TransactionRecord record = dynamoDBService.getById(uploadId);
        if (record == null) return "{\"error\": \"No data found\"}";

        Metrics metrics = record.getMetrics();

        try {
            Map<String, Object> result = new HashMap<>();
            result.put("monthly", metrics.getMonthly());
            result.put("weekly", metrics.getWeekly());
            result.put("daily", metrics.getDaily());
            result.put("rolling7d", metrics.getRollingOut7d());

            return objectMapper.writeValueAsString(result);
        } catch (Exception e) {
            return "{\"error\": \"Failed to serialize\"}";
        }
    }
}