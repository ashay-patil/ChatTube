package com.chat.backend.repositories;

import com.chat.backend.entities.ChatHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatHistoryRepository extends MongoRepository<ChatHistory, String> {
    List<ChatHistory> findByChatSessionIdAndUserIdOrderByCreatedAtAsc(String chatSessionId, String userId);
}
