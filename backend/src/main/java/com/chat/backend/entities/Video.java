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
    private String Id;

    private String youtubeVideoId;

    private String youtubeVideoUrl;

    @DocumentReference
    private User user;

    public Video(String youtubeVideoId, String youtubeVideoUrl, User user) {
        this.youtubeVideoId = youtubeVideoId;
        this.youtubeVideoUrl = youtubeVideoUrl;
        this.user = user;
    }
}
