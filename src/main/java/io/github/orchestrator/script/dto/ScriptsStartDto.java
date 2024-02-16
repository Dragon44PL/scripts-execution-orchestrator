package io.github.orchestrator.script.dto;

import io.github.orchestrator.script.vo.Command;

import java.util.List;

public record ScriptsStartDto(List<ScriptStartDto> executions) {
    public record ScriptStartDto(Command command, String machine) {}
}
