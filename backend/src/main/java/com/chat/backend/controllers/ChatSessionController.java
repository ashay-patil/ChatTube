package com.chat.backend.controllers;


import com.chat.backend.DTO.ChatSessionResponse;
import com.chat.backend.entities.ChatSession;
import com.chat.backend.entities.User;
import com.chat.backend.services.ChatSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat-session")
public class ChatSessionController {

    @Autowired
    private ChatSessionService chatSessionService;

    @PostMapping("/create-chat-session")
    public ChatSession createChatSession(@RequestBody ChatSession chatSession, @AuthenticationPrincipal User user) {
        return chatSessionService.save(chatSession, user);
    }

    @GetMapping("/get-chat-sessions")
    public List<ChatSessionResponse> getAllChatSessions(@AuthenticationPrincipal User user) {
        return chatSessionService.getAllChatSessions(user);
    }
}
