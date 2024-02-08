package io.github.orchestrator.script;

import io.github.orchestrator.script.vo.ScriptSessionId;
import org.springframework.data.mongodb.repository.MongoRepository;

interface ScriptExecutionsDocumentRepository extends MongoRepository<ScriptExecutions, ScriptSessionId>, ScriptExecutionsRepository {}
