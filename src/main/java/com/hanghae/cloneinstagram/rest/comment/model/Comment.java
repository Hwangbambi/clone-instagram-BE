package com.hanghae.cloneinstagram.rest.comment.model;

import com.hanghae.cloneinstagram.config.model.Timestamped;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long postId;

    @Column(nullable = false)
    private String content;

    @Column
    private Boolean deleted;

    @Column
    private int likes;

}
