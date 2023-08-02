package com.example.plusweek.service;

import com.example.plusweek.exception.ApiResponseDto;
import com.example.plusweek.dto.CommentResponseDto;
import com.example.plusweek.dto.CommentRequestDto;
import com.example.plusweek.entiry.Comment;
import com.example.plusweek.entiry.Post;
import com.example.plusweek.entiry.User;
import com.example.plusweek.exception.NotFoundException;
import com.example.plusweek.exception.NotHavePermission;
import com.example.plusweek.repository.CommentRepository;
import com.example.plusweek.security.UserDetailsImpl;
import com.example.plusweek.service.inter.PostService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.concurrent.RejectedExecutionException;

@Service
public class CommentService {

    CommentRepository commentRepository;
    PostService postService;
    MessageSource messageSource;
    public CommentService(CommentRepository commentRepository, PostServiceImpl postService, MessageSource messageSource){
        this.commentRepository = commentRepository;
        this.postService = postService;
        this.messageSource = messageSource;
    }


    public CommentResponseDto createComment(CommentRequestDto commentRequestDto, UserDetailsImpl userDetails) {
            Post post = postService.findPost(commentRequestDto.getPostId());

        Comment comment = new Comment(commentRequestDto.getBody(), post, userDetails.getUser());

        commentRepository.save(comment);
        CommentResponseDto commentResponseDto = new CommentResponseDto(comment);

        return commentResponseDto;

    }
    public void deleteComment(Long id, User user) {

        Comment comment = findComment(id);

        if (!comment.getUser().equals(user)) {
            throw new NotHavePermission(messageSource.getMessage(
                    "not.have.permission",
                    null,
                    "Not have permission",
                    Locale.getDefault()
            ));
        }

        commentRepository.delete(comment);
    }

    @Transactional
    public ApiResponseDto updateComment(Long id, CommentRequestDto commentRequestDto, UserDetailsImpl userDetails) {
        Comment comment = findComment(id);
        if (userDetails.getUsername().equals(comment.getUser().getUsername())) {
            comment.setBody(commentRequestDto.getBody());
        } else {
            throw new NotHavePermission(messageSource.getMessage(
                    "not.have.permission",
                    null,
                    "Not have permission",
                    Locale.getDefault()
            ));
        }
        return new ApiResponseDto("수정완료", 200);
    }

    public Comment findComment(Long id) {
        return commentRepository.findById(id).orElseThrow(() ->
                new NotFoundException(messageSource.getMessage(
                        "not.found.exception",
                        null,
                        "NOT FOUND THIS COMMENT",
                        Locale.getDefault()
                )));

    }
}
