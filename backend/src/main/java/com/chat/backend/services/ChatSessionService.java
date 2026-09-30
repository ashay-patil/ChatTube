package com.chat.backend.services;

import com.chat.backend.DTO.ChatSessionResponse;
import com.chat.backend.entities.ChatSession;
import com.chat.backend.entities.User;
import com.chat.backend.repositories.ChatSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatSessionService {
    @Autowired
    private ChatSessionRepository chatSessionRepository;

    public ChatSession save(ChatSession chatSession, User user) {
        chatSession.setUser(user);
        return chatSessionRepository.save(chatSession);
    }

    public List<ChatSessionResponse> getAllChatSessions(User user) {
        List<ChatSession> chatSessions = chatSessionRepository.findByUser(user);
        List<ChatSessionResponse> chatSessionResponses = new ArrayList<>();
        chatSessions.forEach((chatSession) -> {
            ChatSessionResponse chatSessionResponse = new ChatSessionResponse(chatSession.getId(), chatSession.getSessionName());
            chatSessionResponses.add(chatSessionResponse);
        });
        return chatSessionResponses;
    }
}
