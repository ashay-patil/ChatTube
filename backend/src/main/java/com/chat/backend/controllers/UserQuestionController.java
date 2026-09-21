package com.chat.backend.controllers;

import com.chat.backend.DTO.VideoChunkSearchResult;
import com.chat.backend.repositories.VideoChunkRepository;
import com.chat.backend.services.VideoChunkSimilaritySearch;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.bson.Document;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserQuestionController {
    @Autowired
    private VideoChunkSimilaritySearch videoChunkSimilaritySearch;

    @PostMapping("/ask-question")
    public List<VideoChunkSearchResult> getResponse(@RequestBody String userQuestion) {
        return videoChunkSimilaritySearch.search(userQuestion);
    }
}
