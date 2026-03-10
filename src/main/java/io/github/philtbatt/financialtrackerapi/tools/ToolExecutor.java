package io.github.philtbatt.financialtrackerapi.tools;

import io.github.philtbatt.financialtrackerapi.tools.SpendSummaryTool;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.document.Document;

@Component
public class ToolExecutor {

    private final SpendSummaryTool spendSummaryTool;

    public ToolExecutor(SpendSummaryTool spendSummaryTool) {
        this.spendSummaryTool = spendSummaryTool;
    }

    public String execute(String toolName, Document input, String uploadId) {
        if ("get_spend_summary".equals(toolName)) {
            return spendSummaryTool.execute(uploadId);
        }
        return "{\"error\": \"Unknown tool\"}";
    }
}