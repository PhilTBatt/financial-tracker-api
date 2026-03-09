package io.github.philtbatt.financialtrackerapi.controller;

import io.github.philtbatt.financialtrackerapi.model.AskRequest;
import io.github.philtbatt.financialtrackerapi.model.AskResponse;
import io.github.philtbatt.financialtrackerapi.service.AiService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
public class AiController {
    private final AiService aiService;

    public AiController(AiService aiService) { this.aiService = aiService; }

    @PostMapping("/ask")
    public AskResponse ask(@RequestBody AskRequest request) {
        return aiService.ask(request.sessionId(), request.message());
    }
}
