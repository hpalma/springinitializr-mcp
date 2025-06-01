package com.hugopalma.springinitializrmcp;

final class SpringInitializrConstants {
    static final String DOWNLOAD_FOLDER_PATH_DESCRIPTION = "The absolute path of the folder to where the generated project zip file will be downloaded to. Don't provide any value unless the user explicitly mentions it.";
    static final String SHOULD_EXTRACT_DESCRIPTION = "Whether the downloaded zip file should be extracted. Defaults to 'false'.";

    static final String PROJECT_TYPE_DESCRIPTION = "The project type. Defaults to 'gradle-project'. Supported values: 'maven-project', 'gradle-project', 'gradle-project-kotlin'.";
    static final String LANGUAGE_DESCRIPTION = "The language. Defaults to 'java'. Supported values: 'java', 'kotlin', 'groovy'.";
    static final String GROUP_ID_DESCRIPTION = "The group id of the generated project.";
    static final String ARTIFACT_ID_DESCRIPTION = "The artifact id of the generated project.";
    static final String SPRING_BOOT_VERSION_DESCRIPTION = "The Spring Boot version.";
    static final String PROJECT_NAME_DESCRIPTION = "The project name.";
    static final String PROJECT_DESCRIPTION_DESCRIPTION = "The project description.";
    static final String PACKAGE_NAME_DESCRIPTION = "The package name of the generated project. Must be a valid java package name.";
    static final String PACKAGING_DESCRIPTION = "The packaging of the generated project. Defaults to 'jar'. Supported values: 'jar', 'war'.";
    static final String JAVA_VERSION_DESCRIPTION = "The java version to use. ${JAVA_VERSION_DEFAULT_AND_SUPPORTED}";
    static final String DEPENDENCIES_DESCRIPTION = "The dependencies to add to the generated project. Defaults to ''. ${DEPENDENCIES_SUPPORTED}";
}
