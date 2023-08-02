package com.example.plusweek.dto;

import com.example.plusweek.entiry.Post;
import lombok.Getter;

@Getter
public class CommentRequestDto {
    Long postId;
    String body;

    public void setBody(String body){
        this.body = body;
    }
}
