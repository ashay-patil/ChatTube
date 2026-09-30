package com.chat.backend.entities;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Document("ChatHistory")
public class ChatHistory {
    @Id
    private String id;
    private String userQuestion;
    private List<Double> userQuestionEmbedding;
    private String LLMResponse;
    private List<Double> LLMResponseEmbedding;
    private String userId;
    private String chatSessionId;
    private Date createdAt;
}
