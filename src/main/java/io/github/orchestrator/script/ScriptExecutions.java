package io.github.orchestrator.script;

import io.github.orchestrator.script.vo.ScriptExecutionId;
import io.github.orchestrator.script.vo.ScriptExecutionState;
import io.github.orchestrator.script.vo.ScriptSessionId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Document
record ScriptExecutions(@Id ScriptSessionId id, List<ScriptExecution> executions) {

    List<ScriptExecution> getCurrentFailedExecutions() {
        return this.executions.stream()
             .filter(execution -> !execution.isHistorical())
             .filter(execution -> ScriptExecutionState.FAILED.equals(execution.getState()))
             .collect(Collectors.toList());
    }

    Optional<ScriptExecution> getExecutionById(ScriptExecutionId id) {
        return this.executions.stream()
             .filter(execution -> Objects.equals(execution.getId(), id))
             .findAny();
    }

    void markFailedExecutionsAsHistorical() {
        this.getCurrentFailedExecutions().forEach(ScriptExecution::markAsHistorical);
    }

    void addExecutions(List<ScriptExecution> executions) {
        this.executions.addAll(executions);
    }
}
