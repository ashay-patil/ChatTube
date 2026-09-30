package com.chat.backend.DTO;

import lombok.Data;

@Data
public class ChatSessionResponse {
    private String id;
    private String sessionName;

    public ChatSessionResponse(String id, String sessionName) {
        this.id = id;
        this.sessionName = sessionName;
    }
}
