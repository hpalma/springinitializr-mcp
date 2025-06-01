package com.hugopalma.springinitializrmcp.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SpringInitializrUrlBuilderTest {

    @Test
    void testBasicUrlGeneration() {
        String url = SpringInitializrUrlBuilder.builder()
                .projectType("maven-project")
                .language("java")
                .groupId("com.example")
                .artifactId("demo")
                .springBootVersion("3.5.0")
                .name("Demo Project")
                .description("Demo project for Spring Boot")
                .packageName("com.example.demo")
                .packaging("jar")
                .javaVersion("17")
                .dependencies("web,data-jpa")
                .build();

        assertTrue(url.startsWith("https://start.spring.io/starter.zip?"));
        assertTrue(url.contains("type=maven-project"));
        assertTrue(url.contains("language=java"));
        assertTrue(url.contains("groupId=com.example"));
        assertTrue(url.contains("artifactId=demo"));
        assertTrue(url.contains("bootVersion=3.5.0"));
        assertTrue(url.contains("name=Demo+Project")); // URL encoded space
        assertTrue(url.contains("description=Demo+project+for+Spring+Boot"));
        assertTrue(url.contains("packageName=com.example.demo"));
        assertTrue(url.contains("packaging=jar"));
        assertTrue(url.contains("javaVersion=17"));
        assertTrue(url.contains("dependencies=web%2Cdata-jpa")); // URL encoded comma
    }

    @Test
    void testFromParametersConvenienceMethod() {
        String url = SpringInitializrUrlBuilder.fromParameters(
                "gradle-project",
                "kotlin",
                "com.example",
                "my-app",
                "3.5.0",
                "My Application",
                "A sample application",
                "com.example.myapp",
                "jar",
                "21",
                "web,actuator"
        ).build();

        assertTrue(url.contains("type=gradle-project"));
        assertTrue(url.contains("language=kotlin"));
        assertTrue(url.contains("groupId=com.example"));
        assertTrue(url.contains("artifactId=my-app"));
        assertTrue(url.contains("bootVersion=3.5.0"));
        assertTrue(url.contains("javaVersion=21"));
        assertTrue(url.contains("dependencies=web%2Cactuator"));
    }

    @Test
    void testNullAndEmptyParametersHandling() {
        String url = SpringInitializrUrlBuilder.builder()
                .projectType(null) // should not be included
                .language("") // should not be included
                .groupId("  ") // should be ignored (whitespace only)
                .artifactId("test")
                .springBootVersion(null) // should not be included
                .dependencies("") // should not be included
                .build();

        assertFalse(url.contains("type="));
        assertFalse(url.contains("language="));
        assertTrue(url.contains("artifactId=test"));
        assertFalse(url.contains("groupId=")); // not included due to whitespace
        assertFalse(url.contains("bootVersion=")); // not included due to null
        assertFalse(url.contains("dependencies=")); // not included due to empty string
    }

    @Test
    void testUrlEncoding() {
        String url = SpringInitializrUrlBuilder.builder()
                .name("My Amazing Project!")
                .description("A project with special chars: &, =, +, %")
                .dependencies("spring-web,spring-data-jpa")
                .build();

        assertTrue(url.contains("name=My+Amazing+Project%21"));
        assertTrue(url.contains("description=A+project+with+special+chars%3A+%26%2C+%3D%2C+%2B%2C+%25"));
        assertTrue(url.contains("dependencies=spring-web%2Cspring-data-jpa"));
    }

    @Test
    void testMinimalConfiguration() {
        String url = SpringInitializrUrlBuilder.builder().build();

        assertTrue(url.endsWith("starter.zip?"));
    }
}
