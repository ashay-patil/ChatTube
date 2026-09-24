package com.chat.backend.controllers;

import com.chat.backend.DTO.ChatHistorySearchResult;
import com.chat.backend.entities.User;
import com.chat.backend.repositories.VideoRepository;
import com.chat.backend.RAG.ChatHistorySimilaritySearch;
import com.chat.backend.RAG.EmbeddingService;
import com.chat.backend.RAG.LLMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class TestController {

    @Autowired
    VideoRepository repository;

    @Autowired
    LLMService llmService;

    @Autowired
    ChatHistorySimilaritySearch chatHistorySimilaritySearch;

    @Autowired
    EmbeddingService embeddingService;
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello Chat App";
    }

    @GetMapping("/test-gemini")
    public String testGemini() throws Exception{
        return llmService.getGeminiResponse("Hello How Are you", "Sample VideoContext", "Sample Chat Context");
    }

    @GetMapping("/test-chat-history-retrieval")
    public List<ChatHistorySearchResult> getRelevantChatHistory() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        User user =
                (User) authentication.getPrincipal();
        List<Double> testEmbedding = embeddingService.generateEmbedding("What all questions about brain power did I ask you ? ");
        return chatHistorySimilaritySearch.search(testEmbedding, user);
    }

    @GetMapping("/get-me")
    public User getMe(@AuthenticationPrincipal User user) {
        return user;
    }

}
