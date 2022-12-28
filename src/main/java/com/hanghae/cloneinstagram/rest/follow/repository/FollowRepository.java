package com.hanghae.cloneinstagram.rest.follow.repository;

import com.hanghae.cloneinstagram.rest.follow.model.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    /*@Query(value = "select c.id, u.username, u.profile_url, c.content, c.created_at from comment c join users u on c.user_id = u.id\n" +
            "where c.deleted is not true and u.deleted is not true and c.post_id = :postId order by c.created_at desc", nativeQuery = true)*/
    List<Follow> findByUserId(@Param("userId") long userId);
}
