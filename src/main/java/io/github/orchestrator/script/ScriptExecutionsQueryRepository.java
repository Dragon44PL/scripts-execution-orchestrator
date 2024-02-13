package io.github.orchestrator.script;

import io.github.orchestrator.script.dto.ScriptExecutionDto;
import io.github.orchestrator.script.vo.ScriptSessionId;

import java.util.List;

public interface ScriptExecutionsQueryRepository {
    List<ScriptExecutionDto> findCurrentExecutionsById(ScriptSessionId scriptSessionId);
    List<ScriptExecutionDto> findCurrentFailedExecutions(ScriptSessionId scriptSessionId);
}
