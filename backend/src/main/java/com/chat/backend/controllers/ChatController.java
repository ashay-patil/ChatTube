package com.chat.backend.controllers;

import com.chat.backend.DTO.LLMResponse;
import com.chat.backend.entities.User;
import com.chat.backend.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ChatController {

    @Autowired
    ChatService chatService;
    @PostMapping("/ask-question")
    public LLMResponse getResponse(@RequestBody String userQuestion, @AuthenticationPrincipal User user) throws Exception{
        return chatService.getResponse(userQuestion, user);
    }
}
