package com.example.plusweek.service;

import com.example.plusweek.dto.PostRequestDto;
import com.example.plusweek.dto.PostResponseDto;
import com.example.plusweek.entiry.Post;
import com.example.plusweek.entiry.User;
import com.example.plusweek.repository.PostRepository;
import com.example.plusweek.security.UserDetailsImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.RejectedExecutionException;

@Service
public class PostService {

    PostRepository postRepository;

    public PostService(PostRepository postRepository){
        this.postRepository = postRepository;
    }


    public void createPost(PostRequestDto postRequestDto, UserDetailsImpl userDetails) {

        Post post = new Post(postRequestDto,userDetails.getUsername());

        postRepository.save(post);
    }
    public List<PostResponseDto> showAllPost() {
        List<PostResponseDto> responseDtoList = postRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(PostResponseDto::new)
                .toList();
        return responseDtoList;
    }


        public Post findPost(Long id) {
            return postRepository.findById(id).orElseThrow(() ->
                    new IllegalArgumentException("해당 게시글은 존재하지 않습니다."));
        }

    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto requestDto,UserDetailsImpl user) {
        Post post = findPost(id);

        if (!post.getUsername().equals(user.getUsername())) {
            throw new RejectedExecutionException();
        }
        post.setTitle(requestDto.getTitle());
        post.setContent(requestDto.getContent());

        return new PostResponseDto(post);

    }

    public void deletePost(Long id, User user) {
        Post post = findPost(id);

        if (!post.getUsername().equals(user.getUsername())) {
            throw new RejectedExecutionException();
        }
        postRepository.delete(post);
    }
}

