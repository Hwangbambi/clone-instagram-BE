package com.hanghae.cloneinstagram.rest.like.model;

import com.hanghae.cloneinstagram.config.model.Timestamped;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.function.Supplier;

@Entity (name = "comment_like")
@RequiredArgsConstructor
@Getter
@Table(indexes = @Index(name = "idx__comment_id", columnList = "commentId"))
public class CommentLike extends Timestamped implements Supplier<CommentLike> {
     
     @Id
     @GeneratedValue (strategy = GenerationType.IDENTITY)
     private Long id;
     
     @Column(nullable = false)
     private Long commentId;
     
     @Column(nullable = false)
     private Long userId;
     
     public CommentLike(Long commentId, Long userId) {
          this.commentId = commentId;
          this.userId = userId;
     }
     
     @Override
     public CommentLike get() {
          return null;
     }
     
}
