package com.hugopalma.springinitializrmcp;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

@Service
class SpringInitialzrTools {

    @Tool(description = "Generates and downloads a Spring Boot project to a given folder")
    String generateAndDownload(
            @ToolParam(description = PROJECT_TYPE_DESCRIPTION, required = false)
            String projectType,

            @ToolParam(description = LANGUAGE_DESCRIPTION, required = false)
            String language,
            String groupId,
            String artifactId,
            String version,
            String name,
            String description,
            String packageName,
            String packaging,
            String javaVersion,
            String dependencies) {
        return "Hugo Palma";
    }

    private static final String PROJECT_TYPE_DESCRIPTION = "The project type. Defaults to 'gradle-project'. Supported values: 'maven-project', 'gradle-project', 'gradle-project-kotlin'.";
    private static final String LANGUAGE_DESCRIPTION = "The language. Defaults to 'java'. Supported values: 'java', 'kotlin', 'groovy'.";
}
