package io.github.orchestrator.script;

import io.github.orchestrator.script.dto.ScriptExecutionDto;
import io.github.orchestrator.script.vo.ScriptSessionId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class DefaultScriptExecutionsQueryRepository implements ScriptExecutionsQueryRepository {

    private final ScriptExecutionsRepository scriptExecutionsRepository;

    @Override
    public List<ScriptExecutionDto> findCurrentExecutionsById(ScriptSessionId scriptSessionId) {
        final var executions = scriptExecutionsRepository.findById(scriptSessionId)
                .map(ScriptExecutions::executions).orElse(Collections.emptySet());
        final var currentExecutions = executions.stream()
                .filter(execution -> !execution.isHistorical())
                .collect(Collectors.toList());
        return createScriptExecutionDto(currentExecutions);
    }

    @Override
    public List<ScriptExecutionDto> findCurrentFailedExecutions(ScriptSessionId scriptSessionId) {
        final var executions = scriptExecutionsRepository.findById(scriptSessionId);
        return executions.map(ScriptExecutions::getCurrentFailedExecutions)
                .map(DefaultScriptExecutionsQueryRepository::createScriptExecutionDto)
                .orElse(Collections.emptyList());
    }

    private static List<ScriptExecutionDto> createScriptExecutionDto(Collection<ScriptExecution> scriptExecution) {
        return scriptExecution.stream()
                .map(execution -> new ScriptExecutionDto(execution.getMachine(), execution.getCommand(), execution.getStartedAt(), execution.getFinishedAt(), execution.getState()))
                .sorted(Comparator.comparing(ScriptExecutionDto::startedAt))
                .collect(Collectors.toList());
    }
}
