package com.chat.backend.services;


import com.chat.backend.DTO.ChatHistorySearchResult;
import com.chat.backend.DTO.VideoChunkSearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class ChatHistorySimilaritySearch {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private EmbeddingService embeddingService;

    public List<ChatHistorySearchResult> search(List<Double> queryEmbedding) {
        System.out.println("Got the embeddings");
        Document vectorSearchLLMResponse = new Document(
                "$vectorSearch",
                new Document("index", "chat_vector_index")
                        .append("path", "LLMResponseEmbedding")
                        .append("queryVector", queryEmbedding)
                        .append("numCandidates", 50)
                        .append("limit", 10)
        );
        Document vectorSearchUserQuestion = new Document(
                "$vectorSearch",
                new Document("index", "chat_vector_index")
                        .append("path", "userQuestionEmbedding")
                        .append("queryVector", queryEmbedding)
                        .append("numCandidates", 50)
                        .append("limit", 10)
        );

        Document project = new Document(
                "$project",
                new Document("_id", 1)
                        .append("userQuestion", 1)
                        .append("LLMResponse", 1)
                        .append("score",
                                new Document("$meta", "vectorSearchScore"))
        );

        List<Document> vectorSearchLLMResponsePipeline = List.of(
                vectorSearchLLMResponse,
                project
        );
        List<Document> vectorSearchUserQuestionPipeline = List.of(
                vectorSearchUserQuestion,
                project
        );

        List<Document> rawResultLLMResponse = mongoTemplate
                .getCollection("ChatHistory")
                .aggregate(vectorSearchLLMResponsePipeline)
                .into(new ArrayList<>());

        List<Document> rawResultUserQuestion = mongoTemplate
                .getCollection("ChatHistory")
                .aggregate(vectorSearchUserQuestionPipeline)
                .into(new ArrayList<>());

        List<Document> rawResult = new ArrayList<>();
        rawResult.addAll(rawResultLLMResponse);
        rawResult.addAll(rawResultUserQuestion);

        List<ChatHistorySearchResult> results = rawResult.stream()
                .map(doc -> {

                    ChatHistorySearchResult result = new ChatHistorySearchResult();

                    result.setId(doc.getObjectId("_id").toString());
                    result.setLLMResponse(doc.getString("LLMResponse"));
                    result.setScore(doc.getDouble("score"));
                    result.setUserQuestion(doc.getString("userQuestion"));
                    return result;
                })
                .toList();

        return results;
    }

}
