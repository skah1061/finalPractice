package com.example.plusweek.dto;

import com.example.plusweek.entiry.Post;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostResponseDto {
    Long id;
    String title;
    String content;
    String username;
    LocalDateTime createdAt;
    LocalDateTime modifiedAt;
    private List<CommentResponseDto> comment;



    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.username = post.getUsername();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
        this.comment = post.getCommentList()
                .stream()
                .map(CommentResponseDto::new)
                .toList();
    }
}
