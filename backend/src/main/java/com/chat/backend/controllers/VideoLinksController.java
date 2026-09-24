package com.chat.backend.controllers;

import com.chat.backend.DTO.YoutubeLinksRequest;
import com.chat.backend.entities.User;
import com.chat.backend.services.VideoLinksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class VideoLinksController {
    @Autowired
    private VideoLinksService videoLinksService;

    @PostMapping("/upload-youtube-videos")
    public String uploadYoutubeVideos(@RequestBody YoutubeLinksRequest youtubeLinksRequest, @RequestParam("chatSessionId") String chatSessionId, @AuthenticationPrincipal User user) throws  Exception{
        return videoLinksService.saveYoutubeLinksToDB(youtubeLinksRequest.getYoutubeLinks(), chatSessionId, user);
    }
}
