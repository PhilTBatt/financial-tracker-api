package io.github.philtbatt.financialtrackerapi.tools;

import software.amazon.awssdk.core.document.Document;
import software.amazon.awssdk.services.bedrockruntime.model.*;

public class ToolConfig {

    public static ToolConfiguration build() {
        return ToolConfiguration.builder()
                .tools(Tool.builder()
                        .toolSpec(ToolSpecification.builder()
                                .name("get_spend_summary")
                                .description("Gets the user's total spending, averages and category breakdown. " +
                                        "Use this when the user asks anything about their spending, transactions or budget.")
                                .inputSchema(ToolInputSchema.builder()
                                        .json(Document.mapBuilder()
                                                .putString("type", "object")
                                                .putDocument("properties", Document.mapBuilder().build())
                                                .build())
                                        .build())
                                .build())
                        .build())
                .toolChoice(ToolChoice.fromAuto(AutoToolChoice.builder().build()))
                .build();
    }
}