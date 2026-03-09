package io.github.philtbatt.financialtrackerapi.memory;

import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.bedrockruntime.model.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Component
public class SessionStore {

    private final Map<String, List<Message>> sessions = new HashMap<>();

    public List<Message> load(String sessionId) {
        return sessions.getOrDefault(sessionId, new ArrayList<>());
    }

    public void save(String sessionId, List<Message> messages) {
        sessions.put(sessionId, messages);
    }
}