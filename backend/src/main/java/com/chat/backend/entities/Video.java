package com.chat.backend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("Video")
public class Video {
    @Id
    private String Id;

    private String youtubeVideoId;

    private String youtubeVideoUrl;

    public Video(String youtubeVideoId, String youtubeVideoUrl) {
        this.youtubeVideoId = youtubeVideoId;
        this.youtubeVideoUrl = youtubeVideoUrl;
    }
}
