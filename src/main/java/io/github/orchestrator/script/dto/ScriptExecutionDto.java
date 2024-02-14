package io.github.orchestrator.script.dto;

import io.github.orchestrator.script.vo.Command;
import io.github.orchestrator.script.vo.ScriptExecutionState;

import java.time.Instant;

public record ScriptExecutionDto(String machine, Command command, Instant startedAt, Instant finishedAt, ScriptExecutionState state) { }
