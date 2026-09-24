package com.chat.backend.entities;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Data
@Document(collection = "chat_session")
public class ChatSession {
    @Id
    private String id;

    @DocumentReference
    private User user;

    @NonNull
    private String sessionName;
}
