package com.hanghae.cloneinstagram.rest.comment.repository;

import com.hanghae.cloneinstagram.rest.comment.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostIdAndDeletedIsFalseOrderByCreatedAtDesc(Long postId);
}
