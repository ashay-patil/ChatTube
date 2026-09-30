package com.chat.backend.DTO;

import lombok.Data;

@Data
public class VideoResponse {
    private String Id;

    private String youtubeVideoId;

    private String youtubeVideoUrl;

    public VideoResponse(String id, String youtubeVideoId, String youtubeVideoUrl) {
        this.Id = id;
        this.youtubeVideoId = youtubeVideoId;
        this.youtubeVideoUrl = youtubeVideoUrl;
    }
}
