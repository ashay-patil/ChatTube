package com.chat.backend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Document(collection = "video_chunks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoChunk {

    @Id
    private String id;

    @DocumentReference
    private Video video;

    private int chunkIndex;
    private int startTime;
    private int endTime;
    private String text;
    private List<Double> embedding;

    private String userId;


    public VideoChunk(Video savedVideo, int chunkIndex, int startTime, int endTime, String chunkText, List<Double> embedding, String userId) {
        video = savedVideo;
        this.chunkIndex = chunkIndex;
        this.startTime = startTime;
        this.endTime = endTime;
        text = chunkText;
        this.embedding = embedding;
        this.userId = userId;
    }
}
