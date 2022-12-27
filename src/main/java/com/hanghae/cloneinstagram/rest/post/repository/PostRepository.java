package com.hanghae.cloneinstagram.rest.post.repository;

import com.hanghae.cloneinstagram.rest.post.dto.PostUsernameInterface;
import com.hanghae.cloneinstagram.rest.post.model.Post;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
     
     boolean existsByIdAndUserId(Long postId, Long userId);
//    List<Post> findByDeletedIsFalseOrderByCreatedAtDesc();
     
     @Query (
          nativeQuery = true,
          value = "select post.*, u2.username, u2.profile_url from post join " +
               "(select id from " +
                    "(select p.id from post p " +
                    "inner join users u on p.user_id = u.id " +
                    "where p.deleted is false and u.username like %:search% " +
                    "order by p.id desc ) as A " +
               "union " +
                    "select id from " +
                    "(select p.id from post p " +
                    "inner join hashtag h on p.id = h.post_id " +
                    "where p.deleted is false and h.hashtag like %:search% " +
                    "order by p.id desc ) as B " +
               "order by id desc) postId on post.id = postId.id " +
               "join users u2 on post.user_id = u2.id " +
               "where u2.deleted is false " +
               "order by id desc limit :size" )
     List<PostUsernameInterface> findAllByUsernameAndDeletedIsFalseOrderByIdDesc(int size, String search);
     //deleted user 확인
     
     
     List<Post> findByDeletedIsFalseOrderByCreatedAtDesc();
     
     @Query (
          nativeQuery = true,
          value = "select post.*, u.username, u.profile_url " +
               "from post join " +
               "users u on post.user_id = u.id " +
               "where post.deleted is false and u.deleted is false " +
               "order by id desc limit :size" )
     List<PostUsernameInterface> findAllByDeletedIsFalseAndByUserOrderByIdDesc(@Param("size") int size);
     
     Optional<Post> findByIdAndDeletedIsFalse(Long postId);
}
