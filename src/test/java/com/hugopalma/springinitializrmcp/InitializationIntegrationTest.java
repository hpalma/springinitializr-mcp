package com.hugopalma.springinitializrmcp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@EnabledIfSystemProperty(named = "test.native.integration", matches = "true")
class InitializationIntegrationTest {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final String INITIALIZE_REQUEST = """
            {"method":"initialize","params":{"protocolVersion":"2024-11-05","capabilities":{},"clientInfo":{"name":"claude-ai","version":"0.1.0"}},"jsonrpc":"2.0","id":0}""";

    @Test
    @Timeout(value = 60, unit = TimeUnit.SECONDS)
    void testNativeExecutableMcpProtocol() throws Exception {
        Path nativeExecutable = findNativeExecutable();

        // Test the native executable
        Process serverProcess = startNativeExecutable(nativeExecutable);

        try {
            testMcpProtocolWithProcess(serverProcess);
        } finally {
            if (serverProcess.isAlive()) {
                serverProcess.destroyForcibly();
                serverProcess.waitFor(5, TimeUnit.SECONDS);
            }
        }
    }

    @Test
    @Timeout(value = 30, unit = TimeUnit.SECONDS)
    void testNativeExecutableStartupTime() throws Exception {
        Path nativeExecutable = findNativeExecutable();

        long startTime = System.currentTimeMillis();
        Process serverProcess = startNativeExecutable(nativeExecutable);

        try {
            // Send a simple request to ensure it's fully started
            try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(serverProcess.getOutputStream()));
                 BufferedReader reader = new BufferedReader(new InputStreamReader(serverProcess.getInputStream()))) {

                writer.println(INITIALIZE_REQUEST);
                writer.flush();

                String response = readResponseWithTimeout(reader, Duration.ofSeconds(5));
                assertNotNull(response, "Should get response from native executable");

                long startupTime = System.currentTimeMillis() - startTime;
                System.out.println("Native executable startup time: " + startupTime + "ms");

                // Native images should start very quickly
                assertTrue(startupTime < 5000, "Native startup should be under 5 seconds");
            }
        } finally {
            if (serverProcess.isAlive()) {
                serverProcess.destroyForcibly();
                serverProcess.waitFor(5, TimeUnit.SECONDS);
            }
        }
    }

    private void testMcpProtocolWithProcess(Process serverProcess) throws IOException, InterruptedException {
        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(serverProcess.getOutputStream()));
             BufferedReader reader = new BufferedReader(new InputStreamReader(serverProcess.getInputStream()))) {

            // Wait a bit for startup
            Thread.sleep(1000);

            assertTrue(serverProcess.isAlive(), "Native executable should be running");

            // Test 1: Initialize request
            System.out.println("Sending initialize request...");
            writer.println(INITIALIZE_REQUEST);
            writer.flush();

            String initResponse = readResponseWithTimeout(reader, Duration.ofSeconds(10));
            validateInitializeResponse(initResponse);
        }
    }

    private void validateInitializeResponse(String response) throws IOException {
        assertNotNull(response, "Initialize response should not be null");

        JsonNode responseJson = objectMapper.readTree(response);
        assertEquals("2.0", responseJson.get("jsonrpc").asText());
        assertEquals(0, responseJson.get("id").asInt());

        if (responseJson.has("result")) {
            JsonNode result = responseJson.get("result");
            assertTrue(result.has("protocolVersion"));
            assertTrue(result.has("capabilities"));
            assertTrue(result.has("serverInfo"));

            JsonNode serverInfo = result.get("serverInfo");
            assertEquals("springinitializr", serverInfo.get("name").asText());
        } else if (responseJson.has("error")) {
            fail("Initialize failed with error: " + responseJson.get("error"));
        }
    }

    private Path findNativeExecutable() {
        String executableName = "springinitializr-mcp";
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            executableName += ".exe";
        }

        return Paths.get("build", "native", "nativeCompile", executableName);
    }

    private Process startNativeExecutable(Path executable) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder(executable.toString());
        processBuilder.directory(new File("."));
        processBuilder.redirectErrorStream(false);

        Process process = processBuilder.start();

        // Native images should start very quickly, but give it a moment
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted during startup wait", e);
        }

        if (!process.isAlive()) {
            throw new RuntimeException("Native executable failed to start");
        }

        return process;
    }

    private String readResponseWithTimeout(BufferedReader reader, Duration timeout) throws IOException {
        long startTime = System.currentTimeMillis();
        long timeoutMillis = timeout.toMillis();

        StringBuilder response = new StringBuilder();
        String line;

        while (System.currentTimeMillis() - startTime < timeoutMillis) {
            if (reader.ready()) {
                line = reader.readLine();
                if (line != null) {
                    response.append(line);
                    if (isValidJson(response.toString())) {
                        return response.toString();
                    }
                }
            } else {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Interrupted while reading response", e);
                }
            }
        }

        String result = response.toString();
        if (result.trim().isEmpty()) {
            return null;
        }
        return result;
    }

    private boolean isValidJson(String json) {
        try {
            objectMapper.readTree(json);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
