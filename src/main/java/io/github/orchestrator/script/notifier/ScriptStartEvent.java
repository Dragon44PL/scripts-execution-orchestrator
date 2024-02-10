package io.github.orchestrator.script.notifier;

import io.github.orchestrator.script.vo.Command;
import io.github.orchestrator.script.vo.ScriptExecutionId;
import io.github.orchestrator.script.vo.ScriptSessionId;

public record ScriptStartEvent(ScriptExecutionId id, ScriptSessionId scriptSessionId, Command command) { }
