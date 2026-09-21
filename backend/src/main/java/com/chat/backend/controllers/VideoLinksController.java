package com.chat.backend.controllers;

import com.chat.backend.DTO.YoutubeLinksRequest;
import com.chat.backend.services.YoutubeLinksSave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class VideoLinksController {
    @Autowired
    private YoutubeLinksSave youtubeLinksSave;

    @PostMapping("/upload-youtube-videos")
    public String uploadYoutubeVideos(@RequestBody YoutubeLinksRequest youtubeLinksRequest) throws  Exception{
        return youtubeLinksSave.saveYoutubeLinksToDB(youtubeLinksRequest.getYoutubeLinks());
    }
}
