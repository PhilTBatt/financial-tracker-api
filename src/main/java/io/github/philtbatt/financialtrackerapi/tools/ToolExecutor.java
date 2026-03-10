package io.github.philtbatt.financialtrackerapi.tools;

import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.document.Document;

@Component
public class ToolExecutor {

    private final GetSpendSummaryTool spendSummaryTool;
    private final GetTransactionBreakdownTool transactionBreakdownTool;
    private final GetTrendsTool trendsTool;

    public ToolExecutor(GetSpendSummaryTool spendSummaryTool, GetTransactionBreakdownTool transactionBreakdownTool, GetTrendsTool trendsTool) {
        this.spendSummaryTool = spendSummaryTool;
        this.transactionBreakdownTool = transactionBreakdownTool;
        this.trendsTool = trendsTool;
    }

    public String execute(String toolName, Document input, String uploadId) {
        return switch (toolName) {
            case "get_spend_summary" -> spendSummaryTool.execute(uploadId);
            case "get_transaction_breakdown" -> transactionBreakdownTool.execute(uploadId);
            case "get_trends" -> trendsTool.execute(uploadId);
            default -> "{\"error\": \"Unknown tool\"}";
        };
    }
}