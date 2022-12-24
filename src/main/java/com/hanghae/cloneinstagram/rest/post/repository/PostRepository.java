package com.hanghae.cloneinstagram.rest.post.repository;

import com.hanghae.cloneinstagram.rest.post.model.Post;
import com.hanghae.cloneinstagram.rest.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    boolean existsByIdAndUserId(Long postId, Long userId);


}
