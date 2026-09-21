package com.chat.backend.DTO;

import lombok.Data;

@Data
public class VideoChunkSearchResult {
    private String id;
    private String text;
    private int startTime;
    private int endTime;
    private String videoId;
    private int chunkIndex;
    private double score;
}
