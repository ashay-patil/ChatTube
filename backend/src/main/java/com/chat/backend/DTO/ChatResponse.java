package com.chat.backend.DTO;

import lombok.Data;

import java.util.List;

@Data
public class ChatResponse {
    private String id;
    private String userQuestion;
    private String LLMResponse;


    public ChatResponse(String id, String userQuestion, String LLMResponse) {
        this.id = id;
        this.userQuestion = userQuestion;
        this.LLMResponse = LLMResponse;
    }
}
