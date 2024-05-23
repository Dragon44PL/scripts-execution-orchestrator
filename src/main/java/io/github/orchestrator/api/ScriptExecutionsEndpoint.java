package io.github.orchestrator.api;

import io.github.orchestrator.script.ScriptExecutionsQueryRepository;
import io.github.orchestrator.script.ScriptExecutionsRestartRunner;
import io.github.orchestrator.script.ScriptExecutionsRunner;
import io.github.orchestrator.script.dto.ScriptExecutionDto;
import io.github.orchestrator.script.dto.ScriptsStartDto;
import io.github.orchestrator.script.vo.ScriptSessionId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
class ScriptExecutionsEndpoint {

    private final ScriptExecutionsRunner scriptExecutionsRunner;
    private final ScriptExecutionsRestartRunner scriptExecutionsRestartRunner;
    private final ScriptExecutionsQueryRepository scriptExecutionsQueryRepository;

    @PostMapping(value = "/api/scripts:start", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ScriptSessionId startScriptsExecutions(@RequestBody ScriptsStartDto scriptsStartDto) {
        return scriptExecutionsRunner.runScriptsExecutions(scriptsStartDto);
    }

    @PostMapping("/api/scripts:restart")
    void restartScriptsExecutions(@RequestParam String id) {
        scriptExecutionsRestartRunner.restartFailedScriptsExecutions(ScriptSessionId.create(id));
    }

    @GetMapping(value = "/api/scripts/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ScriptExecutionDto> getScriptExecutions(@PathVariable String id) {
        return scriptExecutionsQueryRepository.findCurrentExecutionsById(ScriptSessionId.create(id));
    }

    @GetMapping(value = "/api/scripts/failed/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ScriptExecutionDto> getCurrentFailedScriptExecutions(@PathVariable String id) {
        return scriptExecutionsQueryRepository.findCurrentFailedExecutions(ScriptSessionId.create(id));
    }
}
