package io.github.philtbatt.financialtrackerapi.service;

import io.github.philtbatt.financialtrackerapi.model.AskResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient;
import software.amazon.awssdk.services.bedrockruntime.model.*;

@Service
public class AiService {

    private final BedrockRuntimeClient bedrockClient;
    private static final String MODEL_ID = "eu.anthropic.claude-3-haiku-20240307-v1:0";

    public AiService(BedrockRuntimeClient bedrockClient) { this.bedrockClient = bedrockClient; }

    public AskResponse ask(String userMessage) {
        Message message = Message.builder()
                .role(ConversationRole.USER)
                .content(ContentBlock.fromText(userMessage))
                .build();

        ConverseResponse response = bedrockClient.converse(
                ConverseRequest.builder()
                        .modelId(MODEL_ID)
                        .messages(message)
                        .inferenceConfig(InferenceConfiguration.builder()
                                .maxTokens(300)
                                .build())
                        .build()
        );

        String answer = response.output().message().content().stream()
                .filter(cb -> cb.type() == ContentBlock.Type.TEXT)
                .map(ContentBlock::text)
                .reduce("", String::concat);

        return new AskResponse(answer);
    }
}
