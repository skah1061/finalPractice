package com.example.plusweek.contoller;

import com.example.plusweek.exception.ApiResponseDto;
import com.example.plusweek.dto.CommentRequestDto;
import com.example.plusweek.security.UserDetailsImpl;
import com.example.plusweek.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController
{
    CommentService commentService;

    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }
    @PostMapping("/comment")         //댓글 작성
    public ApiResponseDto createComment(@RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.createComment(commentRequestDto, userDetails);
        return new ApiResponseDto("댓글 작성 완료", HttpStatus.OK.value());
    }


    @DeleteMapping("/comment/{id}")
    public String deleteComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        commentService.deleteComment(id,userDetails.getUser());
        return "삭제 완료";
    }
    @PutMapping("/comment/{id}")
    public ApiResponseDto updadteComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        return commentService.updateComment(id,commentRequestDto,userDetails);

    }
}
