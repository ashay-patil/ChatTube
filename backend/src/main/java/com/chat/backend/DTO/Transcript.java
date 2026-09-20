package com.chat.backend.DTO;

import lombok.Data;

@Data
public class Transcript {
    private String lang;
    private String text;
    private Integer offset;
    private Integer duration;
}
