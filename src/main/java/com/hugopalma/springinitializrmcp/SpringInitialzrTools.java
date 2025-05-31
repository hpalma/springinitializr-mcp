package com.hugopalma.springinitializrmcp;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

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

    private static final String PROJECT_TYPE_DESCRIPTION = "The project type. Defaults to 'gradle-project'. Supported values: 'maven-project', 'gradle-project', 'gradle-project-kotlin'.";
    private static final String LANGUAGE_DESCRIPTION = "The language. Defaults to 'java'. Supported values: 'java', 'kotlin', 'groovy'.";
    private static final String GROUP_ID_DESCRIPTION = "The group id of the generated project.";
    private static final String ARTIFACT_ID_DESCRIPTION = "The artifact id of the generated project.";
    private static final String SPRING_BOOT_VERSION_DESCRIPTION = "The Spring Boot version.";
    private static final String PROJECT_NAME_DESCRIPTION = "The project name.";
    private static final String JAVA_VERSION_DESCRIPTION = "The java version to use. Defaults to '17'. Supported values: '17', '21', '24'.";
}
