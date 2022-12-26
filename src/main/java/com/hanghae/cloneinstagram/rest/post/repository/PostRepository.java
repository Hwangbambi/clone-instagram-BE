package com.hanghae.cloneinstagram.rest.post.repository;

import com.hanghae.cloneinstagram.rest.post.model.Post;
import com.hanghae.cloneinstagram.rest.user.model.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
     
     boolean existsByIdAndUserId(Long postId, Long userId);
//    List<Post> findByDeletedIsFalseOrderByCreatedAtDesc();
     
     @Query (
          nativeQuery = true,
          value = "select * from post p " +
               "where (p.id >= (select Max(p.id) from post) - :postIdx) and p.deleted is false " +
               "order by p.id desc " +
               "limit :size")
     List<Post> findByDeletedIsFalseOrderByCreatedAtDesc(@Param("size") int size, @Param ("postIdx") Long postIdx);
     List<Post> findByDeletedIsFalseOrderByCreatedAtDesc();
     Slice<Post> findAllByDeletedIsFalseOrderByIdDesc(PageRequest page);
     Optional<Post> findByIdAndDeletedIsFalse(Long postId);
}
