package io.github.orchestrator.script.vo;

import java.util.UUID;

public record ScriptExecutionId(UUID id) {
    public static ScriptExecutionId create() {
        return new ScriptExecutionId(UUID.randomUUID());
    }
}
