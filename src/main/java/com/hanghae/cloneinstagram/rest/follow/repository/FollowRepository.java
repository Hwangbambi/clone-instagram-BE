package com.hanghae.cloneinstagram.rest.follow.repository;

import com.hanghae.cloneinstagram.rest.follow.dto.FollowRecomImpl;
import com.hanghae.cloneinstagram.rest.follow.model.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import software.amazon.awssdk.services.apigateway.model.Op;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    //follow 테이블에서 user.getId() 로 조회한 결과, 나오는 followId 는 제외하고 리턴?, 게시글 많은 순으로 조회
    @Query(
         nativeQuery = true,
         value = "select pu.user_id as userId, pu.user_id, u.username, u.profile_url " +
              "from " +
                  "(select count(*) as cnt, user_id " + // 게시글 많이 작성한 유저아이디
                  "from post where post.deleted is not true " + // 삭제안된게시글만
                  "group by user_id order by cnt desc) pu " +
              "left join follow " +
              "on follow.user_id = :userId and pu.user_id = follow.follow_id " +
              "join users u on pu.user_id = u.id " +
              "where follow.user_id is null and u.deleted is false limit 20;") // 내가 팔로우하지 않은사람들 20개
    List<FollowRecomImpl> findByUserId(@Param("userId") long userId);
    
    Optional<Follow> findByUserIdAndFollowId(Long id, Long followId);
}
