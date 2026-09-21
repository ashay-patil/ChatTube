package com.chat.backend.DTO;

import lombok.Data;

import java.util.List;

@Data
public class LLMResponse {
    private String response;
    private List<TimeStamp> timestamps;
}
