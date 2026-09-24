package com.chat.backend.repositories;

import com.chat.backend.entities.VideoChunk;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoChunkRepository extends MongoRepository<VideoChunk, String> {
}
