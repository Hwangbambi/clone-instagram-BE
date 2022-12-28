package com.hanghae.cloneinstagram.rest.like.repository;

import com.hanghae.cloneinstagram.rest.like.dto.LikePostUserInterface;
import com.hanghae.cloneinstagram.rest.like.model.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import java.util.Optional;

public interface LikePostRepository extends JpaRepository<PostLike, Long> {
     Optional<PostLike> findByUserIdAndPostId(Long postId, Long userId);
    @Query(value = "select u.username, u.profile_url from post_like pl join users u on pl.user_id = u.id\n" +
            "where u.deleted is false and pl.post_id = :postId", nativeQuery = true)
    List<LikePostUserInterface> findByPostId(@Param("postId") long postId);
}
