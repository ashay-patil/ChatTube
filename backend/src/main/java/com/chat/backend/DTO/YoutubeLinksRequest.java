package com.chat.backend.DTO;

import lombok.Data;

import java.util.List;

@Data
public class YoutubeLinksRequest {
    private List<String> youtubeLinks;
}
