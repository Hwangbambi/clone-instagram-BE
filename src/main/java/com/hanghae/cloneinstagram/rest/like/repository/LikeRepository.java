package com.hanghae.cloneinstagram.rest.like.repository;

import com.hanghae.cloneinstagram.rest.like.model.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<PostLike, Long> {

}
