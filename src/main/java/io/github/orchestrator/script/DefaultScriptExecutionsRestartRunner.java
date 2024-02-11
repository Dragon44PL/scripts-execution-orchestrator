package io.github.orchestrator.script;

import io.github.orchestrator.script.notifier.ScriptExecutionNotifier;
import io.github.orchestrator.script.notifier.ScriptStartEvent;
import io.github.orchestrator.script.vo.ScriptSessionId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
class DefaultScriptExecutionsRestartRunner implements ScriptExecutionsRestartRunner {

    private final ScriptExecutionsRepository scriptExecutionsRepository;
    private final ScriptExecutionNotifier scriptExecutionNotifier;

    @Override
    @Transactional
    public void restartFailedScriptsExecutions(ScriptSessionId scriptSessionId) {
        final var executions = scriptExecutionsRepository.findById(scriptSessionId)
                .orElseThrow(() -> { throw new IllegalStateException("Could not find script executions with sessionId = %s".formatted(scriptSessionId.id())); });
        final var newExecutions = createScriptExecutionsBasedOn(executions);
        persistExecutions(newExecutions, executions);
        sendExecutions(newExecutions, executions.id());
    }

    private void persistExecutions(List<ScriptExecution> newExecutions, ScriptExecutions executions) {
        log.debug("Persisting restarted script executions : {}", executions);
        executions.markFailedExecutionsAsHistorical();
        executions.addExecutions(newExecutions);
        scriptExecutionsRepository.save(executions);
    }

    private void sendExecutions(List<ScriptExecution> newExecutions, ScriptSessionId scriptSessionId) {
        newExecutions.forEach(execution -> {
            log.debug("Sending script execution notification : {}", execution);
            scriptExecutionNotifier.sendNotification(execution.getMachine(), new ScriptStartEvent(execution.getId(), scriptSessionId, execution.getCommand()));
        });
    }

    private static List<ScriptExecution> createScriptExecutionsBasedOn(ScriptExecutions scriptExecutions) {
        final var currentFailedExecutions = scriptExecutions.getCurrentFailedExecutions();
        return currentFailedExecutions.stream()
                .map(ScriptExecution::createNewExecution)
                .collect(Collectors.toList());
    }
}
