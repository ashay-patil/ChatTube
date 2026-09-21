package com.chat.backend.controllers;

import com.chat.backend.DTO.VideoChunkSearchResult;
import com.chat.backend.services.VideoChunkSimilaritySearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ChatController {
    @Autowired
    private VideoChunkSimilaritySearch videoChunkSimilaritySearch;

    @PostMapping("/ask-question")
    public List<VideoChunkSearchResult> getResponse(@RequestBody String userQuestion) {
        return videoChunkSimilaritySearch.search(userQuestion);
    }
}
