package com.chat.backend.controllers;

import com.chat.backend.entities.Video;
import com.chat.backend.repositories.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class HelloController {

//    private String mongoDbUri;
    @Autowired
    VideoRepository repository;
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello Chat App";
    }

    @GetMapping("video-test")
    public List<Video> getAllVideos() {
//        System.out.println(mongoDbUri);
        return repository.findAll();
    }
}
