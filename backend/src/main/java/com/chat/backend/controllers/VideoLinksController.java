package com.chat.backend.controllers;

import com.chat.backend.DTO.YoutubeLinksRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class VideoLinksController {
    @PostMapping("/upload-youtube-videos")
    public void uploadYoutubeVideos(@RequestBody YoutubeLinksRequest youtubeLinksRequest) {
        System.out.println(youtubeLinksRequest.getYoutubeLinks());
    }
}
