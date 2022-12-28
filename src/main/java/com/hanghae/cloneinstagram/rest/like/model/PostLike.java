package com.hanghae.cloneinstagram.rest.like.model;

import com.hanghae.cloneinstagram.config.model.Timestamped;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.function.Supplier;

@Entity (name = "post_like")
@RequiredArgsConstructor
@Getter
@Table(indexes = @Index(name = "idx__postId", columnList = "postId"))
public class PostLike extends Timestamped implements Supplier<PostLike> {
     
     @Id
     @GeneratedValue (strategy = GenerationType.IDENTITY)
     private Long id;
     
     @Column(nullable = false)
     private Long postId;
     
     @Column(nullable = false)
     private Long userId;
     
     public PostLike(Long postId, Long userId) {
          this.postId = postId;
          this.userId = userId;
     }
     
     @Override
     public PostLike get() {
          return null;
     }
     
}
