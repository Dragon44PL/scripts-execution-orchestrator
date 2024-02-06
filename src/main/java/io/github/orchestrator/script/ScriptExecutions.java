package io.github.orchestrator.script;

import io.github.orchestrator.script.vo.ScriptExecutionId;
import io.github.orchestrator.script.vo.ScriptExecutionState;
import io.github.orchestrator.script.vo.ScriptSessionId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Document
record ScriptExecutions(@Id ScriptSessionId id, Set<ScriptExecution> executions) {

    Set<ScriptExecution> getCurrentFailedExecutions() {
        return this.executions.stream()
             .filter(execution -> !execution.isHistorical())
             .filter(execution -> ScriptExecutionState.FAILED.equals(execution.getState()))
             .collect(Collectors.toSet());
    }

    Optional<ScriptExecution> getExecutionById(ScriptExecutionId id) {
        return this.executions.stream()
             .filter(execution -> Objects.equals(execution.getId(), id))
             .findAny();
    }

    void markFailedExecutionsAsHistorical() {
        this.getCurrentFailedExecutions().forEach(ScriptExecution::markAsHistorical);
    }

    void addExecutions(Collection<ScriptExecution> executions) {
        this.executions.addAll(executions);
    }
}
