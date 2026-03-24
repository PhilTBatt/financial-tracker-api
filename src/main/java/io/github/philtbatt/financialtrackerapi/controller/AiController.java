package io.github.philtbatt.financialtrackerapi.controller;

import io.github.philtbatt.financialtrackerapi.model.AskRequest;
import io.github.philtbatt.financialtrackerapi.model.AskResponse;
import io.github.philtbatt.financialtrackerapi.service.AiService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"https://philtbatt.github.io", "http://localhost:4200"})
@RequestMapping("/api/ai")
@RestController
public class AiController {
    private final AiService aiService;

    public AiController(AiService aiService) { this.aiService = aiService; }

    @PostMapping("/ask")
    public AskResponse ask(@RequestBody AskRequest request) {
        return aiService.ask(request.sessionId(), request.message(), request.sessionId());
    }
}
