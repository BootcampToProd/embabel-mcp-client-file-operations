package com.bootcamptoprod.agents;

import com.bootcamptoprod.dto.FileOperationRequest;
import com.bootcamptoprod.dto.FileOperationResponse;
import com.embabel.agent.api.annotation.AchievesGoal;
import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.Agent;
import com.embabel.agent.api.common.OperationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Agent(
        name = "smart-file-operations-agent",
        description = "Intelligent agent that understands natural language commands and performs file operations",
        version = "1.0.0"
)
public class SmartFileOperationsAgent {

    private static final Logger log = LoggerFactory.getLogger(SmartFileOperationsAgent.class);

    @Action(
            description = "Process natural language file operation requests and execute appropriate file operations",
            toolGroups = {"file-operations-mcp-tool-group"}
    )
    @AchievesGoal(description = "Understand user's natural language command and execute the appropriate file operation")
    public FileOperationResponse processFileOperation(FileOperationRequest request, OperationContext context) {
        log.info("[ACTION] processFileOperation START - user command: {}", request.command());

        String prompt = String.format("""
                You are a file operations assistant. The user has given you a command in natural language.
                Analyze the command and perform the appropriate file operation using the available tools.
                
                User Command: "%s"
                
                Based on this command:
                1. Determine if the user wants to CREATE, READ, EDIT, or DELETE a file
                2. Extract the file name from the command
                3. If creating or editing, extract the file content
                4. Use the appropriate file operation tool (createFile, readFile, editFile, or deleteFile)
                5. Return the result with proper metadata
                
                Execute the file operation now.
                """, request.command());

        FileOperationResponse response = context.ai()
                .withDefaultLlm()
                .createObjectIfPossible(prompt, FileOperationResponse.class);

        log.info("[ACTION] processFileOperation END - operation completed");
        return response;
    }
}