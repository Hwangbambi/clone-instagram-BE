package com.hanghae.cloneinstagram.rest.like.repository;

import com.hanghae.cloneinstagram.rest.like.dto.LikePostUserInterface;
import com.hanghae.cloneinstagram.rest.like.model.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import java.util.Optional;

public interface LikePostRepository extends JpaRepository<PostLike, Long> {
     Optional<PostLike> findByUserIdAndPostId(Long postId, Long userId);
     
     // 해당게시글의 좋아요리스트 (게시글 삭제시 관련좋아요는 delete되니 상관x)
     // 내가 팔로우 한지 안한지 도 보내주기. isFollow : true, false
     // 팔로우 한사람이 위쪽, 탈퇴한 유저는 빼고
     @Query (nativeQuery = true,
          value = "select a.username, a.profile_url, a.id, follow.user_id as follow " +
               "from (select u.username, u.profile_url, u.id as id " +
                    "from post_like pl join users u on pl.user_id = u.id " +
                    "where pl.post_id = :postId and u.deleted is false) a " + // 해당게시글의 좋아요한 유저들의 유저정보 들고오기용
               "left join follow on follow.user_id = :loggedUserId and a.id = follow.follow_id " +
               "order by Follow desc, a.id")
     List<LikePostUserInterface> findByPostId(@Param ("postId")Long postId, @Param("loggedUserId")Long loggedUserId);
     
     void deleteByPostId(Long postId);
     
}
