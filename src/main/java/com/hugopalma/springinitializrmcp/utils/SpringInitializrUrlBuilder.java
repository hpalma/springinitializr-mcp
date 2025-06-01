package com.hugopalma.springinitializrmcp.utils;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.StringJoiner;

/**
 * Builder class for constructing Spring Initializr download URLs.
 */
public class SpringInitializrUrlBuilder {
    private static final String BASE_URL = "https://start.spring.io/starter.zip";

    private String projectType;
    private String language;
    private String groupId;
    private String artifactId;
    private String springBootVersion;
    private String name;
    private String description;
    private String packageName;
    private String packaging;
    private String javaVersion;
    private String dependencies;

    public static SpringInitializrUrlBuilder builder() {
        return new SpringInitializrUrlBuilder();
    }

    public SpringInitializrUrlBuilder projectType(String projectType) {
        if (projectType != null && !projectType.trim().isEmpty()) {
            this.projectType = projectType.trim();
        }
        return this;
    }

    public SpringInitializrUrlBuilder language(String language) {
        if (language != null && !language.trim().isEmpty()) {
            this.language = language.trim();
        }
        return this;
    }

    public SpringInitializrUrlBuilder groupId(String groupId) {
        if (groupId != null && !groupId.trim().isEmpty()) {
            this.groupId = groupId.trim();
        }
        return this;
    }

    public SpringInitializrUrlBuilder artifactId(String artifactId) {
        if (artifactId != null && !artifactId.trim().isEmpty()) {
            this.artifactId = artifactId.trim();
        }
        return this;
    }

    public SpringInitializrUrlBuilder springBootVersion(String springBootVersion) {
        if (springBootVersion != null && !springBootVersion.trim().isEmpty()) {
            this.springBootVersion = springBootVersion.trim();
        }
        return this;
    }

    public SpringInitializrUrlBuilder name(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name.trim();
        }
        return this;
    }

    public SpringInitializrUrlBuilder description(String description) {
        if (description != null && !description.trim().isEmpty()) {
            this.description = description.trim();
        }
        return this;
    }

    public SpringInitializrUrlBuilder packageName(String packageName) {
        if (packageName != null && !packageName.trim().isEmpty()) {
            this.packageName = packageName.trim();
        }
        return this;
    }

    public SpringInitializrUrlBuilder packaging(String packaging) {
        if (packaging != null && !packaging.trim().isEmpty()) {
            this.packaging = packaging.trim();
        }
        return this;
    }

    public SpringInitializrUrlBuilder javaVersion(String javaVersion) {
        if (javaVersion != null && !javaVersion.trim().isEmpty()) {
            this.javaVersion = javaVersion.trim();
        }
        return this;
    }

    public SpringInitializrUrlBuilder dependencies(String dependencies) {
        if (dependencies != null) {
            this.dependencies = dependencies.trim();
        }
        return this;
    }

    /**
     * Builds the final Spring Initializr URL with all configured parameters.
     *
     * @return Complete URL for downloading the Spring Boot project
     */
    public String build() {
        StringJoiner params = new StringJoiner("&");

        if (projectType != null) {
            addParam(params, "type", projectType);
        }

        if (language != null) {
            addParam(params, "language", language);
        }

        if (packaging != null) {
            addParam(params, "packaging", packaging);
        }

        if (javaVersion != null) {
            addParam(params, "javaVersion", javaVersion);
        }

        if (springBootVersion != null) {
            addParam(params, "bootVersion", springBootVersion);
        }

        if (groupId != null) {
            addParam(params, "groupId", groupId);
        }

        if (artifactId != null) {
            addParam(params, "artifactId", artifactId);
        }

        if (name != null) {
            addParam(params, "name", name);
        }

        if (description != null) {
            addParam(params, "description", description);
        }

        if (packageName != null) {
            addParam(params, "packageName", packageName);
        }

        if (dependencies != null && !dependencies.isEmpty()) {
            addParam(params, "dependencies", dependencies);
        }

        return BASE_URL + "?" + params;
    }

    /**
     * Helper method to add URL-encoded parameters to the StringJoiner.
     */
    private void addParam(StringJoiner params, String key, String value) {
        String encodedValue = URLEncoder.encode(value, StandardCharsets.UTF_8);
        params.add(key + "=" + encodedValue);
    }

    /**
     * Convenience method to create a builder from all the method parameters.
     */
    public static SpringInitializrUrlBuilder fromParameters(
            String projectType,
            String language,
            String groupId,
            String artifactId,
            String springBootVersion,
            String name,
            String description,
            String packageName,
            String packaging,
            String javaVersion,
            String dependencies) {

        return SpringInitializrUrlBuilder.builder()
                .projectType(projectType)
                .language(language)
                .groupId(groupId)
                .artifactId(artifactId)
                .springBootVersion(springBootVersion)
                .name(name)
                .description(description)
                .packageName(packageName)
                .packaging(packaging)
                .javaVersion(javaVersion)
                .dependencies(dependencies);
    }
}
