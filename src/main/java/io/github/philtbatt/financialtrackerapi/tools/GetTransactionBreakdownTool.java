package io.github.philtbatt.financialtrackerapi.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.philtbatt.financialtrackerapi.model.Metrics;
import io.github.philtbatt.financialtrackerapi.model.TransactionRecord;
import io.github.philtbatt.financialtrackerapi.service.DynamoDBService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class GetTransactionBreakdownTool {

    private final DynamoDBService dynamoDBService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public GetTransactionBreakdownTool(DynamoDBService dynamoDBService) {
        this.dynamoDBService = dynamoDBService;
    }

    public String execute(String uploadId) {
        TransactionRecord record = dynamoDBService.getById(uploadId);
        if (record == null) return "{\"error\": \"No data found\"}";

        Metrics metrics = record.getMetrics();

        try {
            Map<String, Object> result = new HashMap<>();
            result.put("topOutgoingTransactions", metrics.getTopOutgoingTransactions());
            result.put("outgoingSizeBuckets", metrics.getBuckets().getOutgoingSize());
            result.put("incomingSizeBuckets", metrics.getBuckets().getIncomingSize());

            return objectMapper.writeValueAsString(result);
        } catch (Exception e) {
            return "{\"error\": \"Failed to serialize\"}";
        }
    }
}