package com.hugopalma.springinitializrmcp;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

@Service
class SpringInitialzrTools {

    @Tool(description = "Get my name")
    String getName() {
        return "Hugo Palma";
    }
}
