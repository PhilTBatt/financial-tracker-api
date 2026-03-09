package io.github.philtbatt.financialtrackerapi.service;

import io.github.philtbatt.financialtrackerapi.memory.SessionStore;
import io.github.philtbatt.financialtrackerapi.model.AskResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient;
import software.amazon.awssdk.services.bedrockruntime.model.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class AiService {

    private final BedrockRuntimeClient bedrockClient;
    private final SessionStore sessionStore;
    private static final String MODEL_ID = "eu.anthropic.claude-3-haiku-20240307-v1:0";

    public AiService(BedrockRuntimeClient bedrockClient, SessionStore sessionStore) {
        this.bedrockClient = bedrockClient;
        this.sessionStore = sessionStore;
    }

    public AskResponse ask(String sessionId, String userMessage) {
        List<Message> messages = new ArrayList<>(sessionStore.load(sessionId));

        messages.add(Message.builder()
                .role(ConversationRole.USER)
                .content(ContentBlock.fromText(userMessage))
                .build());

        ConverseResponse response = bedrockClient.converse(
                ConverseRequest.builder()
                        .modelId(MODEL_ID)
                        .system(SystemContentBlock.builder()
                                .text("You are a personal finance assistant built by Phil Battersby. " +
                                        "You help the user of his app understand their spending and transactions. " +
                                        "Never guess or make up numbers — only state figures if they come from a tool result. " +
                                        "The user's currency is GBP (£). Always display amounts in pounds. " +
                                        "Keep answers concise and friendly. " +
                                        "If you don't have enough information to answer, ask a clarifying question.")
                                .build())
                        .messages(messages)
                        .inferenceConfig(InferenceConfiguration.builder()
                                .maxTokens(300)
                                .build())
                        .build()
        );

        messages.add(response.output().message());

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
}
