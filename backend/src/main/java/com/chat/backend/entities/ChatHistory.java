package com.chat.backend.entities;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document("ChatHistory")
public class ChatHistory {
    // id , userQuestion, userQuestionEmbedding, LLMResponse, LLMResponseEmbedding
    @Id
    private String id;
    private String userQuestion;
    private List<Double> userQuestionEmbedding;
    private String LLMResponse;
    private List<Double> LLMResponseEmbedding;
    private String userId;

    // later need to add session and user fields
}
