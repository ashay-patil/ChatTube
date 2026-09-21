package com.chat.backend.repositories;

import com.chat.backend.entities.ChatHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatHistoryRepository extends MongoRepository<ChatHistory, String> {
}
