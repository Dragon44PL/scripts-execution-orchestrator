package io.github.orchestrator.script.vo;

import java.util.UUID;

public record ScriptSessionId(UUID id) {

    public static ScriptSessionId create(String id) {
        return new ScriptSessionId(UUID.fromString(id));
    }

    public static ScriptSessionId create() {
        return new ScriptSessionId(UUID.randomUUID());
    }
}
