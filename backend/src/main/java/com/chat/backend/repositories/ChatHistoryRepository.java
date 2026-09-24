package com.chat.backend.repositories;

import com.chat.backend.entities.ChatHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatHistoryRepository extends MongoRepository<ChatHistory, String> {
}
