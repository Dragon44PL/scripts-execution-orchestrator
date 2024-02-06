package io.github.orchestrator.script;

import io.github.orchestrator.script.vo.Command;
import io.github.orchestrator.script.vo.ScriptExecutionId;
import io.github.orchestrator.script.vo.ScriptExecutionState;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter
@Setter
@Document
@EqualsAndHashCode
@Builder(access = AccessLevel.PRIVATE)
class ScriptExecution {
    @Id
    private ScriptExecutionId id;
    private Integer exitCode;
    private String machine;
    private Command command;
    private Instant startedAt;
    private Instant finishedAt;
    private ScriptExecutionState state;
    private boolean historical;

    void markAsHistorical() {
        this.historical = true;
    }

    static ScriptExecution initialize(Command command, String machine) {
        return ScriptExecution.builder()
                .id(ScriptExecutionId.create())
                .state(ScriptExecutionState.IN_PROGRESS)
                .startedAt(Instant.now())
                .historical(false)
                .command(command)
                .machine(machine)
                .build();
    }

    ScriptExecution createNewExecution() {
        return ScriptExecution.builder()
                .id(ScriptExecutionId.create())
                .state(ScriptExecutionState.IN_PROGRESS)
                .startedAt(Instant.now())
                .historical(false)
                .machine(machine)
                .command(command)
                .build();
    }
}
