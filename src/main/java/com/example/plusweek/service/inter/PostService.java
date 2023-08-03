package com.example.plusweek.service.inter;

import com.example.plusweek.dto.PostRequestDto;
import com.example.plusweek.dto.PostResponseDto;
import com.example.plusweek.entiry.Post;
import com.example.plusweek.entiry.User;
import com.example.plusweek.security.UserDetailsImpl;
import com.example.plusweek.service.PostServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PostService{

    void createPost(PostRequestDto postRequestDto, UserDetailsImpl userDetails);

    List<PostResponseDto> showAllPost();

    PostResponseDto getPost(Long id);
    List<Post> search(String keyword);

    Post findPost(Long id);

    @Transactional
    PostResponseDto updatePost(Long id, PostRequestDto requestDto, UserDetailsImpl user);

    void deletePost(Long id, User user);
}
