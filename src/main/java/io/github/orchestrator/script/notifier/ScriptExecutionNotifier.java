package io.github.orchestrator.script.notifier;

public interface ScriptExecutionNotifier {
    void sendNotification(String machine, ScriptStartEvent scriptStartEvent);
}
