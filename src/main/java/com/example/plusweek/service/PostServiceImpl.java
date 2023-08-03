package com.example.plusweek.service;

import com.example.plusweek.dto.PostRequestDto;
import com.example.plusweek.dto.PostResponseDto;
import com.example.plusweek.entiry.Post;
import com.example.plusweek.entiry.User;
import com.example.plusweek.exception.NotFoundException;
import com.example.plusweek.exception.NotHavePermission;
import com.example.plusweek.repository.PostRepository;
import com.example.plusweek.security.UserDetailsImpl;
import com.example.plusweek.service.inter.PostService;
import org.aspectj.bridge.Message;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.RejectedExecutionException;

@Service
public class PostServiceImpl implements PostService {

    PostRepository postRepository;
    private final MessageSource messageSource;
    public PostServiceImpl(PostRepository postRepository,MessageSource messageSource){
        this.postRepository = postRepository;
        this.messageSource = messageSource;
    }



    @Override
    public void createPost(PostRequestDto postRequestDto, UserDetailsImpl userDetails) {

        Post post = new Post(postRequestDto,userDetails.getUsername());

        postRepository.save(post);
    }
    @Override
    public List<PostResponseDto> showAllPost() {
        List<PostResponseDto> responseDtoList = postRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(PostResponseDto::new)
                .toList();
        return responseDtoList;
    }
    @Override
    public PostResponseDto getPost(Long id) {

        Post post = findPost(id);

        return new PostResponseDto(post);
    }
    /* search */
    @Transactional
    @Override
    public List<Post> search(String keyword) {
        List<Post> postsList = postRepository.findByTitleContaining(keyword);
        return postsList;
    }
    @Override
    public Post findPost(Long id) {
            return postRepository.findById(id).orElseThrow(() ->
                    new NotFoundException(messageSource.getMessage(
                            "not.found.exception",
                            null,
                            "NOT FOUND ID",
                            Locale.getDefault()
                    ))
            );
    }
    @Override
    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto requestDto, UserDetailsImpl user) {
        Post post = findPost(id);

        if (!post.getUsername().equals(user.getUsername())) {
            throw new NotHavePermission(messageSource.getMessage(
                    "not.have.permission",
                    null,
                    "You Do Not Have Permission",
                    Locale.getDefault()
            ));
        }
        post.setTitle(requestDto.getTitle());
        post.setContent(requestDto.getContent());

        return new PostResponseDto(post);

    }
    @Override
    public void deletePost(Long id, User user) {
        Post post = findPost(id);

        if (!post.getUsername().equals(user.getUsername())) {
            throw new NotHavePermission(messageSource.getMessage(
                    "not.have.permission",
                    null,
                    "You Do Not Have Permission",
                    Locale.getDefault()
            ));
        }
        postRepository.delete(post);
    }
}

