package com.hanghae.cloneinstagram.rest.comment.repository;

import com.hanghae.cloneinstagram.rest.comment.dto.CommentUsernameInterface;
import com.hanghae.cloneinstagram.rest.comment.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostIdAndDeletedIsFalseOrderByCreatedAtDesc(Long postId);
    
    
    @Query(
         nativeQuery = true,
         value = "select c.id, c.content, c.created_at, u.username, u.profile_url " +
              "from comment c join users u on c.user_id = u.id " +
              "where c.post_id = :postId and c.deleted is false and u.deleted is false " +
              "order by c.id desc " +
              "limit 2 "
    )
    List<CommentUsernameInterface> findByIdAndDeletedIsFalseOrderByCreatedAtDescLimit2(Long postId);
    
    @Query(
         nativeQuery = true,
         value = "select c.id, c.content, c.created_at, u.username, u.profile_url " +
              "from comment c join users u on c.user_id = u.id " +
              "where c.post_id = :postId and c.deleted is false and u.deleted is false " +
              "order by c.id desc "
    )
    List<CommentUsernameInterface> findByIdAndDeletedIsFalseOrderByCreatedAtDesc(Long postId);
    
    Optional<Comment> findByIdAndUserId(Long commentId, Long userId);
    
}
