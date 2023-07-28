package com.example.plusweek.contoller;

import com.example.plusweek.service.PostService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PostController {
    PostService postService;
    public PostController(PostService postService){
        this.postService = postService;
    }
}
