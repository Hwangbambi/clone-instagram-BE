package com.hanghae.cloneinstagram.rest.follow.model;

import com.hanghae.cloneinstagram.rest.follow.dto.FollowRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "follow")
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long followId;

    public Follow(FollowRequestDto followRequestDto){
        this.userId = followRequestDto.getUserId();
        this.followId = followRequestDto.getFollowId();
    }

}
