package com.chat.backend.DTO;

import lombok.Data;

@Data
public class ChatHistorySearchResult {
    private String id;
    private String userQuestion;
    private String LLMResponse;
    private double score;
}
