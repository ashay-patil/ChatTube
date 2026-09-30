package com.chat.backend.repositories;

import com.chat.backend.entities.ChatSession;
import com.chat.backend.entities.User;
import com.chat.backend.entities.Video;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends MongoRepository<Video, String> {
    List<Video> findByChatSessionAndUser(ChatSession chatSession, User user);
}
