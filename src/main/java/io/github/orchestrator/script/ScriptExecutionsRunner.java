package io.github.orchestrator.script;

import io.github.orchestrator.script.dto.ScriptsStartDto;
import io.github.orchestrator.script.vo.ScriptSessionId;

public interface ScriptExecutionsRunner {
    ScriptSessionId runScriptsExecutions(ScriptsStartDto scriptsStartDto);
}
