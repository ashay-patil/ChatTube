package com.chat.backend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("Video")
public class Video {
    @Id
    private String id;

    private String youtubeVideoId;

    private String youtubeVideoUrl;

    @DocumentReference
    private User user;

    @DocumentReference
    private ChatSession chatSession;

    public Video(String youtubeVideoId, String youtubeVideoUrl, User user, ChatSession chatSession) {
        this.youtubeVideoId = youtubeVideoId;
        this.youtubeVideoUrl = youtubeVideoUrl;
        this.user = user;
        this.chatSession = chatSession;
    }
}
