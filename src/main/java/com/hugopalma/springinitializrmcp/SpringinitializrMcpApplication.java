package com.hugopalma.springinitializrmcp;

import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class SpringinitializrMcpApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SpringinitializrMcpApplication.class, args);
    }

    @Bean
    List<ToolCallback> toolsProvider(SpringInitialzrTools springInitialzrTools) {
        return List.of(ToolCallbacks.from(springInitialzrTools));
    }
}
