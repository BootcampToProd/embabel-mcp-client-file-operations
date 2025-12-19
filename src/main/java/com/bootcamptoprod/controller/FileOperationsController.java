package com.bootcamptoprod.controller;

import com.bootcamptoprod.dto.FileOperationRequest;
import com.bootcamptoprod.dto.FileOperationResponse;
import com.embabel.agent.api.invocation.AgentInvocation;
import com.embabel.agent.core.AgentPlatform;
import com.embabel.agent.core.ProcessOptions;
import com.embabel.agent.core.Verbosity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/files")
public class FileOperationsController {

    private static final Logger log = LoggerFactory.getLogger(FileOperationsController.class);

    private final AgentPlatform agentPlatform;
    private final ProcessOptions processOptions;

    public FileOperationsController(AgentPlatform agentPlatform) {
        this.agentPlatform = agentPlatform;

        Verbosity verbosity = Verbosity.DEFAULT
                .showLlmResponses()
                .showPrompts()
                .showPlanning();

        this.processOptions = ProcessOptions.DEFAULT.withVerbosity(verbosity);
    }

    @PostMapping
    public FileOperationResponse executeFileOperation(@RequestBody FileOperationRequest request) {
        log.info("Natural language file operation request received: {}", request.command());

        var agentInvocation = AgentInvocation
                .builder(agentPlatform)
                .options(processOptions)
                .build(FileOperationResponse.class);

        FileOperationResponse response = agentInvocation.invoke(request);

        log.info("File operation result - success: {}, message: {}",
                response.success(), response.message());

        return response;
    }
}