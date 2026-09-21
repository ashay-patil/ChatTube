package com.chat.backend.services;

import com.chat.backend.DTO.VideoChunkSearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class VideoChunkSimilaritySearch {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private EmbeddingService embeddingService;

    public List<VideoChunkSearchResult> search(List<Double> queryEmbedding) {

        Document vectorSearch = new Document(
                "$vectorSearch",
                new Document("index", "vector_index")
                        .append("path", "embedding")
                        .append("queryVector", queryEmbedding)
                        .append("numCandidates", 50)
                        .append("limit", 10)
        );

        Document project = new Document(
                "$project",
                new Document("_id", 1)
                        .append("text", 1)
                        .append("startTime", 1)
                        .append("endTime", 1)
                        .append("video", 1)
                        .append("chunkIndex", 1)
                        .append("score",
                                new Document("$meta", "vectorSearchScore"))
        );

        List<Document> pipeline = List.of(
                vectorSearch,
                project
        );

        List<Document> rawResult = mongoTemplate
                .getCollection("video_chunks")
                .aggregate(pipeline)
                .into(new ArrayList<>());

        List<VideoChunkSearchResult> results = rawResult.stream()
                .map(doc -> {

                    VideoChunkSearchResult result = new VideoChunkSearchResult();

                    result.setId(doc.getObjectId("_id").toString());
                    result.setChunkIndex(doc.getInteger("chunkIndex"));
                    result.setStartTime(doc.getInteger("startTime"));
                    result.setEndTime(doc.getInteger("endTime"));
                    result.setText(doc.getString("text"));
                    result.setScore(doc.getDouble("score"));
                    result.setVideoId(
                            doc.getObjectId("video").toString()
                    );
                    return result;
                })
                .toList();

        return results;
    }
}