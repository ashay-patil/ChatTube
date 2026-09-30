package com.chat.backend.repositories;

import com.chat.backend.entities.ChatHistory;
import com.chat.backend.entities.ChatSession;
import com.chat.backend.entities.User;
import org.apache.catalina.LifecycleState;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatHistoryRepository extends MongoRepository<ChatHistory, String> {
    List<ChatHistory> findByChatSessionIdAndUserId(String chatSessionId, String userId);
}
