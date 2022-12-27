package com.hanghae.cloneinstagram.rest.like.model;

import com.hanghae.cloneinstagram.config.model.Timestamped;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.function.Supplier;

@Entity (name = "post_like")
@RequiredArgsConstructor
@Getter
public class PostLike extends Timestamped implements Supplier<PostLike> {
     
     @Id
     @GeneratedValue (strategy = GenerationType.IDENTITY)
     private Long id;
     
     @Column (nullable = false)
     private boolean isLike;
     
     @Override
     public PostLike get() {
          return this;
     }
}
