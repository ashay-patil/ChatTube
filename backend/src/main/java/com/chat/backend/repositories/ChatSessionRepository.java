package com.chat.backend.repositories;

import com.chat.backend.entities.ChatSession;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatSessionRepository extends MongoRepository<ChatSession, String> {
}
