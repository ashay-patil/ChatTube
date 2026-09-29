package com.chat.backend.controllers;


import com.chat.backend.entities.ChatSession;
import com.chat.backend.entities.User;
import com.chat.backend.services.ChatSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat-session")
public class ChatSessionController {

    @Autowired
    private ChatSessionService chatSessionService;

    @PostMapping("/create-chat-session")
    public ChatSession createChatSession(@RequestBody ChatSession chatSession, @AuthenticationPrincipal User user) {
        return chatSessionService.save(chatSession, user);
    }

    // Get All ChatSessions for the user
}
