package com.example.plusweek.entiry;

import com.example.plusweek.dto.PostRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Post extends Timestamped{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(name= "title", nullable = false)
    String title;
    @Column(name= "content")
    String content;
    @Column(name = "username",nullable = false)
    String username;

    @OneToMany(mappedBy = "post", cascade = {CascadeType.REMOVE})
    private List<Comment> commentList = new ArrayList<>();

    public void setTitle(String title){
        this.title = title;
    }
    public void setContent(String content){
        this.content = content;
    }
    public Post(PostRequestDto postRequestDto, String username){
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
        this.username = username;

    }
}
