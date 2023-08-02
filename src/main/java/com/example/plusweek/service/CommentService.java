package com.example.plusweek.service;

import com.example.plusweek.dto.ApiResponseDto;
import com.example.plusweek.dto.CommentResponseDto;
import com.example.plusweek.dto.CommentRequestDto;
import com.example.plusweek.entiry.Comment;
import com.example.plusweek.entiry.Post;
import com.example.plusweek.entiry.User;
import com.example.plusweek.repository.CommentRepository;
import com.example.plusweek.security.UserDetailsImpl;
import com.example.plusweek.service.inter.PostService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.RejectedExecutionException;

@Service
public class CommentService {

    CommentRepository commentRepository;
    PostService postService;
    public CommentService(CommentRepository commentRepository, PostServiceImpl postService){
        this.commentRepository = commentRepository;
        this.postService = postService;
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
            throw new RejectedExecutionException();
        }

        commentRepository.delete(comment);
    }

    @Transactional
    public ApiResponseDto updateComment(Long id, CommentRequestDto commentRequestDto, UserDetailsImpl userDetails) {
        Comment comment = findComment(id);
        if (userDetails.getUsername().equals(comment.getUser().getUsername())) {
            comment.setBody(commentRequestDto.getBody());
        } else {
            throw new IllegalArgumentException("직접쓴 글이 아니면 수정할 수 없습니다.");
        }
        return new ApiResponseDto("수정완료", 200);
    }

    public Comment findComment(Long id) {
        return commentRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 댓글은 존재하지 않습니다."));

    }
}
