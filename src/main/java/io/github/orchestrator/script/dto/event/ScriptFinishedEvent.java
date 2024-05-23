package io.github.orchestrator.script.dto.event;

import io.github.orchestrator.script.vo.ScriptExecutionId;
import io.github.orchestrator.script.vo.ScriptExecutionState;
import io.github.orchestrator.script.vo.ScriptSessionId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ScriptFinishedEvent {
    private ScriptExecutionId id;
    private ScriptSessionId scriptSessionId;
    private Instant finishedAt;
    private Integer exitCode;
    private ScriptExecutionState state;
}
