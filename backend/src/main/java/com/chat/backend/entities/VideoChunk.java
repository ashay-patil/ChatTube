package com.chat.backend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

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
    private double startTime;
    private double endTime;
    private String text;

    public VideoChunk(Video video, int chunkIndex, double startTime, double endTime, String text) {
        this.video = video;
        this.chunkIndex = chunkIndex;
        this.startTime = startTime;
        this.endTime = endTime;
        this.text = text;
    }
    // embedding array here

}
