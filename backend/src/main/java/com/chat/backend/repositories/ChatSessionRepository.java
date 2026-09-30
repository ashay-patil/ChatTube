package com.chat.backend.repositories;

import com.chat.backend.entities.ChatSession;
import com.chat.backend.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatSessionRepository extends MongoRepository<ChatSession, String> {
    List<ChatSession> findByUser(User user);
}
