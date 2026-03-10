package io.github.philtbatt.financialtrackerapi.service;

import io.github.philtbatt.financialtrackerapi.memory.SessionStore;
import io.github.philtbatt.financialtrackerapi.model.AskResponse;
import io.github.philtbatt.financialtrackerapi.tools.ToolExecutor;
import io.github.philtbatt.financialtrackerapi.tools.ToolConfig;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient;
import software.amazon.awssdk.services.bedrockruntime.model.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class AiService {

    private final BedrockRuntimeClient bedrockClient;
    private final SessionStore sessionStore;
    private final ToolExecutor toolExecutor;
    private static final String MODEL_ID = "eu.anthropic.claude-3-haiku-20240307-v1:0";
    private static final int MAX_TOOL_TURNS = 3;

    public AiService(BedrockRuntimeClient bedrockClient, SessionStore sessionStore, ToolExecutor toolExecutor) {
        this.bedrockClient = bedrockClient;
        this.sessionStore = sessionStore;
        this.toolExecutor = toolExecutor;
    }

    public AskResponse ask(String sessionId, String userMessage, String uploadId) {
        List<Message> messages = new ArrayList<>(sessionStore.load(sessionId));

        messages.add(Message.builder()
                .role(ConversationRole.USER)
                .content(ContentBlock.fromText(userMessage))
                .build());

        ToolConfiguration toolConfig = ToolConfig.build();
        ConverseResponse response = callBedrock(messages, toolConfig);

        int toolTurns = 0;
        while (response.stopReason() == StopReason.TOOL_USE && toolTurns < MAX_TOOL_TURNS) {
            toolTurns++;

            Message assistantMsg = response.output().message();
            messages.add(assistantMsg);

            List<ContentBlock> toolResults = new ArrayList<>();
            for (ContentBlock block : assistantMsg.content()) {
                if (block.type() == ContentBlock.Type.TOOL_USE) {
                    ToolUseBlock toolUse = block.toolUse();
                    String result = toolExecutor.execute(toolUse.name(), toolUse.input(), uploadId);

                    toolResults.add(ContentBlock.fromToolResult(
                            ToolResultBlock.builder()
                                    .toolUseId(toolUse.toolUseId())
                                    .content(ToolResultContentBlock.builder()
                                            .text(result)
                                            .build())
                                    .build()
                    ));
                }
            }

            messages.add(Message.builder()
                    .role(ConversationRole.USER)
                    .content(toolResults)
                    .build());

            response = callBedrock(messages, toolConfig);
        }


        String answer = response.output().message().content().stream()
                .filter(cb -> cb.type() == ContentBlock.Type.TEXT)
                .map(ContentBlock::text)
                .reduce("", String::concat);

        messages.add(response.output().message());

        if (messages.size() > 20) {
            messages = messages.subList(messages.size() - 20, messages.size());
        }
        sessionStore.save(sessionId, messages);

        return new AskResponse(answer, sessionId);
    }

    private ConverseResponse callBedrock(List<Message> messages, ToolConfiguration toolConfig) {
        return bedrockClient.converse(
                ConverseRequest.builder()
                        .modelId(MODEL_ID)
                        .system(SystemContentBlock.builder()
                                .text("You are a personal finance assistant built by Phil Battersby. " +
                                        "You help the user of his app understand their spending and transactions. " +
                                        "Never guess or make up numbers — only state figures if they come from a tool result. " +
                                        "All amounts in tool results are in pence. Always divide by 100 and display as pounds (£) in your answers." +
                                        "Keep answers concise and friendly. " +
                                        "If you don't have enough information to answer, ask a clarifying question." +
                                        "You have memory of the current conversation session and can refer back to previous messages. " +
                                        "The user's data covers their personal bank transactions. " +
                                        "When you use a tool, do not tell the user you are calling a tool or fetching data, just answer naturally. " +
                                        "Do not suggest the user contact a financial advisor unless they specifically ask for professional advice. " +
                                        "Do not repeat yourself or add unnecessary closing lines like 'let me know if you have any questions'. "
                                )
                                .build())
                        .messages(messages)
                        .toolConfig(toolConfig)
                        .inferenceConfig(InferenceConfiguration.builder()
                                .maxTokens(500)
                                .build())
                        .build()
        );
    }
}
