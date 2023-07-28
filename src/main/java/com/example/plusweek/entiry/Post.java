package com.example.plusweek.entiry;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
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
}
