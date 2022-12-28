package com.hanghae.cloneinstagram.rest.follow.model;

import com.hanghae.cloneinstagram.rest.follow.dto.FollowRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "follow" , indexes = @Index(name = "idx__userId", columnList = "userId"))
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
    
    public Follow(Long userId, Long followId){
        this.userId = userId;
        this.followId = followId;
    }

}
