package com.chat.backend.controllers;

import com.chat.backend.DTO.VideoResponse;
import com.chat.backend.DTO.YoutubeLinksRequest;
import com.chat.backend.entities.User;
import com.chat.backend.entities.Video;
import com.chat.backend.services.VideoLinksService;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class VideoLinksController {
    @Autowired
    private VideoLinksService videoLinksService;

    @PostMapping("/upload-youtube-videos")
    public String uploadYoutubeVideos(@RequestBody YoutubeLinksRequest youtubeLinksRequest, @RequestParam("chatSessionId") String chatSessionId, @AuthenticationPrincipal User user) {
        return videoLinksService.saveYoutubeLinksToDB(youtubeLinksRequest.getYoutubeLinks(), chatSessionId, user);
    }

    // Get All videos for the user with chatSessionId (RequestParam).
    @GetMapping("/get-videos")
    public List<VideoResponse> getAllVideos(@RequestParam("chatSessionId") String chatSessionId, @AuthenticationPrincipal User user) {
        return videoLinksService.getAllVideos(chatSessionId, user);
    }

    @GetMapping("/get-video")
    public VideoResponse getVideo(@RequestParam("videoId") String videoId, @AuthenticationPrincipal User user) {

//        System.out.println(videoId);
//        System.out.println(user);
        return videoLinksService.getVideo(videoId, user);
    }
}
