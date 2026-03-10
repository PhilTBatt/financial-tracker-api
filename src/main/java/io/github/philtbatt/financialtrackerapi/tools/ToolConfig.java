package io.github.philtbatt.financialtrackerapi.tools;

import software.amazon.awssdk.core.document.Document;
import software.amazon.awssdk.services.bedrockruntime.model.*;

public class ToolConfig {

    public static ToolConfiguration build() {
        return ToolConfiguration.builder()
                .tools(
                        Tool.builder().toolSpec(ToolSpecification.builder()
                                .name("get_spend_summary")
                                .description("Gets total spending, averages and category breakdown. Use when the user asks about overall spending, budgets or category totals.")
                                .inputSchema(emptySchema())
                                .build()).build(),

                        Tool.builder().toolSpec(ToolSpecification.builder()
                                .name("get_transaction_breakdown")
                                .description("Gets the user's biggest transactions and spending size distribution. Use when asked about top purchases or largest transactions.")
                                .inputSchema(emptySchema())
                                .build()).build(),

                        Tool.builder().toolSpec(ToolSpecification.builder()
                                .name("get_trends")
                                .description("Gets monthly, weekly, daily and rolling spending trends over time. Use when asked about spending changes, trends or breakdowns over time.")
                                .inputSchema(emptySchema())
                                .build()).build()
                )
                .toolChoice(ToolChoice.fromAuto(AutoToolChoice.builder().build()))
                .build();
    }

    private static ToolInputSchema emptySchema() {
        return ToolInputSchema.builder()
                .json(Document.mapBuilder()
                        .putString("type", "object")
                        .putDocument("properties", Document.mapBuilder().build())
                        .build())
                .build();
    }
}