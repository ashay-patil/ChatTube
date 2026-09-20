package com.chat.backend.repositories;

import com.chat.backend.entities.VideoChunk;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VideoChunkRepository extends MongoRepository<VideoChunk, String> {
}
