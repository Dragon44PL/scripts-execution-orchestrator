package io.github.orchestrator.script;

import io.github.orchestrator.script.vo.ScriptSessionId;

import java.util.Optional;

interface ScriptExecutionsRepository {
    ScriptExecutions save(ScriptExecutions scriptExecution);
    Optional<ScriptExecutions> findById(ScriptSessionId scriptSessionId);
}
