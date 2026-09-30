package com.chat.backend.controllers;

import com.chat.backend.DTO.ChatResponse;
import com.chat.backend.DTO.LLMResponse;
import com.chat.backend.entities.User;
import com.chat.backend.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ChatController {

    @Autowired
    ChatService chatService;
    @PostMapping("/ask-question")
    public LLMResponse getResponse(@RequestBody String userQuestion, @RequestParam("chatSessionId") String chatSessionId, @AuthenticationPrincipal User user) throws Exception{
        return chatService.getResponse(userQuestion, chatSessionId, user);
    }

    @GetMapping("/get-all-chats")
    List<ChatResponse> getAllChats(@RequestParam("chatSessionId") String chatSessionId, @AuthenticationPrincipal User user) {
        return chatService.getAllChats(chatSessionId, user);
    }
}
