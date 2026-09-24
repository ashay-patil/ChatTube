package com.chat.backend.services;

import com.chat.backend.entities.ChatSession;
import com.chat.backend.entities.User;
import com.chat.backend.repositories.ChatSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatSessionService {
    @Autowired
    private ChatSessionRepository chatSessionRepository;

    public ChatSession save(ChatSession chatSession, User user) {
        chatSession.setUser(user);
        return chatSessionRepository.save(chatSession);
    }
}
