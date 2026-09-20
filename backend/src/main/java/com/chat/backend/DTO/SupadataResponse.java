package com.chat.backend.DTO;

import lombok.Data;

import java.util.List;

@Data
public class SupadataResponse {
    private String lang;
    private List<String> availableLangs;
    private List<Transcript> content ;
}
