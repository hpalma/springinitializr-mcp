package com.hugopalma.springinitializrmcp;

import com.hugopalma.springinitializrmcp.utils.FileAndDownloadUtils;
import com.hugopalma.springinitializrmcp.utils.SpringInitializrUrlBuilder;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import static com.hugopalma.springinitializrmcp.SpringInitialzrConstants.*;

@Service
class SpringInitialzrTools {

    // Unfortunately Spring AI doesn't seem to support @ToolParam in nested classes yet.
    // https://github.com/spring-projects/spring-ai/issues/2866
    @Tool(
            name = "generate-spring-boot-project",
            description = "Generates and downloads a Spring Boot project to a given folder. The return value is the full path of the downloaded file.")
    String generateSpringBootProject(
            @ToolParam(description = DOWNLOAD_FOLDER_PATH_DESCRIPTION, required = false)
            String downloadFolderPath,
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
            @ToolParam(description = PROJECT_DESCRIPTION_DESCRIPTION, required = false)
            String description,
            @ToolParam(description = PACKAGE_NAME_DESCRIPTION, required = false)
            String packageName,
            @ToolParam(description = PACKAGING_DESCRIPTION, required = false)
            String packaging,
            @ToolParam(description = JAVA_VERSION_DESCRIPTION, required = false)
            String javaVersion,
            @ToolParam(description = DEPENDENCIES_DESCRIPTION, required = false)
            String dependencies) {

        // Build the Spring Initializr URL using the builder pattern
        String downloadUrl = SpringInitializrUrlBuilder.fromParameters(
                projectType,
                language,
                groupId,
                artifactId,
                springBootVersion,
                name,
                description,
                packageName,
                packaging,
                javaVersion,
                dependencies
        ).build();

        try {
            if (downloadFolderPath == null) {
                downloadFolderPath = System.getProperty("user.home");
            }

            var fullPath = FileAndDownloadUtils.downloadFile(artifactId, downloadUrl, downloadFolderPath);
            return fullPath.toString();
        } catch (Exception e) {
            throw new RuntimeException(String.format("Failed to download the project. Root cause: %s", e.getMessage()), e);
        }
    }
}
