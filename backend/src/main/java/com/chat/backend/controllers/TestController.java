package com.chat.backend.controllers;

import com.chat.backend.repositories.VideoRepository;
import com.chat.backend.services.LLMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TestController {

    @Autowired
    VideoRepository repository;

    @Autowired
    LLMService llmService;

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello Chat App";
    }

    @GetMapping("/test-gemini")
    public String testGemini() throws Exception{
        return llmService.getGeminiResponse("Hello How Are you", "Sample Context");
    }


}
