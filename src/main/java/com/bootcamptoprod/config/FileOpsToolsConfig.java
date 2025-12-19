package com.bootcamptoprod.config;

import com.embabel.agent.core.ToolGroup;
import com.embabel.agent.core.ToolGroupDescription;
import com.embabel.agent.core.ToolGroupPermission;
import com.embabel.agent.tools.mcp.McpToolGroup;
import io.modelcontextprotocol.client.McpSyncClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Set;

@Configuration
public class FileOpsToolsConfig {

    private final List<McpSyncClient> mcpSyncClients;

    @Autowired
    public FileOpsToolsConfig(@Lazy List<McpSyncClient> mcpSyncClients) {
        Assert.notNull(mcpSyncClients, "McpSyncClients must not be null");
        this.mcpSyncClients = mcpSyncClients;
    }

    @Bean
    public ToolGroup fileOperationsMcpToolGroup() {
        return new McpToolGroup(
                ToolGroupDescription.Companion.invoke(
                        "File system operations including create, read, edit, and delete via MCP",
                        "file-operations-mcp-tool-group"  // ← MUST match toolGroups = {"file-operations-mcp-tool-group"} in agent
                ),
                "File Operations Provider",
                "file-ops-tool-group",
                Set.of(ToolGroupPermission.HOST_ACCESS),
                mcpSyncClients,
                toolCallback -> {
                    String toolName = toolCallback.getToolDefinition().name();
                    return toolName.contains("File") ||
                            toolName.contains("file") ||
                            toolName.equals("createFile") ||
                            toolName.equals("readFile") ||
                            toolName.equals("editFile") ||
                            toolName.equals("deleteFile");
                }
        );
    }
}