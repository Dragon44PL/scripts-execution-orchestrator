package io.github.orchestrator.script.vo;

import java.util.List;

public record Command(String scriptName, List<CommandParameter> parameters) { }