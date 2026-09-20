package com.chat.backend.controllers;

import com.chat.backend.DTO.YoutubeLinksRequest;
import com.chat.backend.entities.Video;
import com.chat.backend.entities.VideoChunk;
import com.chat.backend.services.YoutubeLinksSave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class VideoLinksController {
    @Autowired
    private YoutubeLinksSave youtubeLinksSave;

    @PostMapping("/upload-youtube-videos")
    public List<VideoChunk> uploadYoutubeVideos(@RequestBody YoutubeLinksRequest youtubeLinksRequest) throws  Exception{
//        System.out.println("Reached Controller : " + youtubeLinksRequest);
        return youtubeLinksSave.saveYoutubeLinksToDB(youtubeLinksRequest.getYoutubeLinks());
    }
}
