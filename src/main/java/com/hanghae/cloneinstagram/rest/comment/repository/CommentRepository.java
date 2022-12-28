package com.hanghae.cloneinstagram.rest.comment.repository;

import com.hanghae.cloneinstagram.rest.comment.dto.CommentUsernameInterface;
import com.hanghae.cloneinstagram.rest.comment.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostIdAndDeletedIsFalseOrderByCreatedAtDesc(Long postId);
    
    
    @Query(
         nativeQuery = true,
         value = "select c.id, c.content, c.created_at, u.username, u.profile_url, cl.user_id as isLike " +
              "from comment c join users u on c.user_id = u.id " +
              "left join comment_like cl on c.id = cl.comment_id and cl.user_id = :loginUserId " + // 로그인한 유저의 해당댓글 좋아요 유무
              "where c.post_id = :postId and c.deleted is false and u.deleted is false " +
              "order by c.id desc " +
              "limit 2 "
    )
    List<CommentUsernameInterface> findByIdAndDeletedIsFalseOrderByCreatedAtDescLimit2(@Param ("postId")Long postId, @Param("loginUserId")Long loginUserId);
    
    @Query(
         nativeQuery = true,
         value = "select c.id, c.content, c.created_at, u.username, u.profile_url, cl.user_id as isLike " +
              "from comment c join users u on c.user_id = u.id " + // 댓글 작성자의 정보 가져오기용
              "left join comment_like cl on c.id = cl.comment_id and cl.user_id = :loginUserId " + // 로그인한 유저의 해당댓글 좋아요 유무
              "where c.post_id = :postId and c.deleted is false and u.deleted is false " +
              "order by c.id desc "
    )
    List<CommentUsernameInterface> findByIdAndDeletedIsFalseOrderByCreatedAtDesc(@Param ("postId")Long postId, @Param("loginUserId")Long loginUserId);
    
    Optional<Comment> findByIdAndUserId(Long commentId, Long userId);
    
}
