package io.github.orchestrator.script;

import io.github.orchestrator.script.vo.ScriptSessionId;

public interface ScriptExecutionsRestartRunner {
    void restartFailedScriptsExecutions(ScriptSessionId scriptSessionId);
}
