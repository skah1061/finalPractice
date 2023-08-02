package com.example.plusweek.repository;

import com.example.plusweek.entiry.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
