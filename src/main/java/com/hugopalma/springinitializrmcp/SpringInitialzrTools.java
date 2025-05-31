package com.hugopalma.springinitializrmcp;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import static com.hugopalma.springinitializrmcp.SpringInitialzrConstants.*;

@Service
class SpringInitialzrTools {

    @Tool(description = "Generates and downloads a Spring Boot project to a given folder. The return value is the full path of the downloaded file.")
    String generateSpringBootProject(
            @ToolParam(description = PROJECT_TYPE_DESCRIPTION, required = false)
            String projectType,

            @ToolParam(description = LANGUAGE_DESCRIPTION, required = false)
            String language,

            @ToolParam(description = GROUP_ID_DESCRIPTION, required = false)
            String groupId,

            @ToolParam(description = ARTIFACT_ID_DESCRIPTION, required = false)
            String artifactId,

            @ToolParam(description = SPRING_BOOT_VERSION_DESCRIPTION, required = false)
            String springBootVersion,

            @ToolParam(description = PROJECT_NAME_DESCRIPTION, required = false)
            String name,
            String description,
            String packageName,
            String packaging,

            @ToolParam(description = JAVA_VERSION_DESCRIPTION, required = false)
            String javaVersion,
            String dependencies) {
        return "/Users/hugo.palma/Desktop/demo.zip";
    }
}
