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
        sessionStore.save(sessionId, messages);

        return new AskResponse(answer);
    }
}
