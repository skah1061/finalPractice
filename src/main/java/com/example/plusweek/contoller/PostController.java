package com.example.plusweek.contoller;

import com.example.plusweek.exception.ApiResponseDto;
import com.example.plusweek.dto.PostRequestDto;
import com.example.plusweek.dto.PostResponseDto;
import com.example.plusweek.security.UserDetailsImpl;
import com.example.plusweek.service.inter.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {
    PostService postService;
    public PostController(PostService postService){
        this.postService = postService;
    }
    @PostMapping("/post")
    public String createPost(@RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        postService.createPost(postRequestDto,userDetails);
        return "작성완료";
    }
    @GetMapping("/post")
    public List<PostResponseDto> showAllPost(){
        return postService.showAllPost();
    }

    @GetMapping("/post/{id}")
    public PostResponseDto showPost(@PathVariable Long id){
        return postService.getPost(id);
    }
    @Transactional
    @PutMapping("/post/{id}")    //게시글 수정
    public PostResponseDto updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.updatePost(id, requestDto, userDetails);
    }
    @DeleteMapping("/post/{id}")    //게시글 삭제
    public ApiResponseDto deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.deletePost(id, userDetails.getUser());
        return new ApiResponseDto("게시글 삭제 완료", HttpStatus.OK.value());
    }
}
