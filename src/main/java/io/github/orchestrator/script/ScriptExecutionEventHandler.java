package io.github.orchestrator.script;

public interface ScriptExecutionEventHandler<T> {
    void handle(T t);
}
