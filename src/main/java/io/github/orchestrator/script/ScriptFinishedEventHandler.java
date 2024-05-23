package io.github.orchestrator.script;

import io.github.orchestrator.script.dto.event.ScriptFinishedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
class ScriptFinishedEventHandler implements ScriptExecutionEventHandler<ScriptFinishedEvent> {

    private final ScriptExecutionsRepository scriptExecutionsRepository;

    @Override
    @Transactional
    public void handle(ScriptFinishedEvent scriptFinishedEvent) {
        log.debug("Received script finished event : {}", scriptFinishedEvent);
        scriptExecutionsRepository.findById(scriptFinishedEvent.getScriptSessionId())
                .ifPresent(executions -> performScriptExecutionUpdate(scriptFinishedEvent, executions));
    }

    private void performScriptExecutionUpdate(ScriptFinishedEvent scriptFinishedEvent, ScriptExecutions scriptExecutions) {
        final var execution = scriptExecutions.getExecutionById(scriptFinishedEvent.getId());
        execution.ifPresent(foundExecution -> {
            log.debug("Found script execution with id = {}, performing execution update", scriptFinishedEvent.getId());
            foundExecution.setFinishedAt(scriptFinishedEvent.getFinishedAt());
            foundExecution.setExitCode(scriptFinishedEvent.getExitCode());
            foundExecution.setState(scriptFinishedEvent.getState());
            scriptExecutionsRepository.save(scriptExecutions);
        });
    }
}
