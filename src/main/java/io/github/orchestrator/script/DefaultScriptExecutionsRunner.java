package io.github.orchestrator.script;

import io.github.orchestrator.script.dto.ScriptsStartDto;
import io.github.orchestrator.script.notifier.ScriptExecutionNotifier;
import io.github.orchestrator.script.notifier.ScriptStartEvent;
import io.github.orchestrator.script.vo.ScriptExecutionState;
import io.github.orchestrator.script.vo.ScriptSessionId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
class DefaultScriptExecutionsRunner implements ScriptExecutionsRunner {

    private final ScriptExecutionsRepository scriptExecutionsRepository;
    private final ScriptExecutionNotifier scriptExecutionNotifier;

    @Override
    @Transactional
    public ScriptSessionId runScriptsExecutions(ScriptsStartDto scriptsStartDto) {
        final var executions = createScriptsExecution(scriptsStartDto);
        saveExecutions(executions);
        sendExecutions(executions);
        return executions.id();
    }

    private void saveExecutions(ScriptExecutions executions) {
        log.info("Persisting script executions : {}", executions);
        scriptExecutionsRepository.save(executions);
    }

    private void sendExecutions(ScriptExecutions executions) {
        executions.executions().forEach(execution -> {
            log.debug("Sending script execution notification to RabbitMQ : {}", execution);
            scriptExecutionNotifier.sendNotification(execution.getMachine(), createEvent(execution, executions.id()));
            changeExecutionState(executions, execution);
        });
    }

    private static ScriptStartEvent createEvent(ScriptExecution execution, ScriptSessionId id) {
        return new ScriptStartEvent(execution.getId(), id, execution.getCommand());
    }

    private void changeExecutionState(ScriptExecutions executions, ScriptExecution execution) {
        execution.setState(ScriptExecutionState.IN_PROGRESS);
        scriptExecutionsRepository.save(executions);
    }

    private static ScriptExecutions createScriptsExecution(ScriptsStartDto scriptsStartDto) {
        final var executions = scriptsStartDto.executions().stream()
                .map(execution -> ScriptExecution.initialize(execution.command(), execution.machine()))
                .collect(Collectors.toSet());
        return new ScriptExecutions(ScriptSessionId.create(), executions);
    }
}

