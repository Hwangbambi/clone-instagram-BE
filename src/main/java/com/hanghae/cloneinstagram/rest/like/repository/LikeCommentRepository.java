package com.hanghae.cloneinstagram.rest.like.repository;

import com.hanghae.cloneinstagram.rest.like.model.CommentLike;
import com.hanghae.cloneinstagram.rest.like.model.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeCommentRepository extends JpaRepository<CommentLike, Long> {
     Optional<CommentLike> findByUserIdAndCommentId(Long commentId, Long userId);
}
