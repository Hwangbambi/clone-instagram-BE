package com.hanghae.cloneinstagram.rest.post.repository;

import com.hanghae.cloneinstagram.rest.post.dto.PostUsernameInterface;
import com.hanghae.cloneinstagram.rest.post.model.Post;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
     
     boolean existsByIdAndUserId(Long postId, Long userId);
//    List<Post> findByDeletedIsFalseOrderByCreatedAtDesc();
     
     @Query (
          nativeQuery = true,
          value = "select post.*, u2.username, u2.profile_url, pl.user_id as isLike  from post " +
               "join " +
               "(select id from " +  // 두개 union한거의 postId만 들고오기
                    "(select p.id from post p " +
                    "inner join users u on p.user_id = u.id " + // 게시글과 게시글작성자 join
                    "where p.deleted is false and u.username like %:search% " + // 삭제안된 게시글, 작성자명이 검색어와 일치
                    "order by p.id desc ) as A " +
               "union " +
                    "select id from " +
                    "(select p.id from post p " +
                    "inner join hashtag h on p.id = h.post_id " + // 게시글과 해시태그의 게시글 join
                    "where p.deleted is false and h.hashtag like %:search% " + // 삭제안된 게시글, 해당게시글의 해시태그가 검색어와 일치
                    "order by p.id desc ) as B " +
               "order by id desc) postId on post.id = postId.id " + // search에 해당되는 postId에 해당하는 post만 들고오기
               "join users u2 on post.user_id = u2.id " + // 해당되는 게시글의 작성자정보 들고오기용
               "left join post_like pl on post.id = pl.post_id and pl.user_id=:loggedUserId " + // 로그인한유저의 게시글좋아요유무 들고오기용
               "where u2.deleted is false " +
               "order by id desc limit :size" )
     List<PostUsernameInterface> findAllByUsernameAndDeletedIsFalseOrderByIdDesc(int size, String search, @Param("loggedUserId")Long loggedUserId);
     //deleted user 확인
     
     
     List<Post> findByDeletedIsFalseOrderByCreatedAtDesc();
     
     // 게시글 전체조회 (검색없는 버전. post,user deleted 필터링, 게시글작성자 user정보 같이, 로그인유저의 like여부 같이)
     @Query (
          nativeQuery = true,
          value = "select post.*, u.username, u.profile_url, pl.user_id as isLike  " +
               "from post " +
               "join users u on post.user_id = u.id " +
               "left join post_like pl on post.id = pl.post_id and pl.user_id=:loggedUserId " +
               "where post.deleted is false and u.deleted is false " +
               "order by id desc limit :size" )
     List<PostUsernameInterface> findAllByDeletedIsFalseAndByUserOrderByIdDesc(@Param("size")int size, @Param("loggedUserId")Long loggedUserId);
     
     // 게시글 상세조회 (postId의 게시글, post,user deleted 필터링, 게시글작성자 user정보 같이, 로그인유저의 like여부 같이)
     @Query (
          nativeQuery = true,
          value = "select post.*, u.username, u.profile_url, pl.user_id as isLike " +
               "from post " +
               "join users u on post.user_id = u.id " +
               "left join post_like pl on post.id = pl.post_id and pl.user_id=:loggedUserId " +
               "where post.id = :postId and post.deleted is false and u.deleted is false")
     Optional<PostUsernameInterface> findByIdAndDeletedIsFalseAndByUserOrderByIdDesc(@Param("postId")Long postId, @Param("loggedUserId")Long loggedUserId);
     
     Optional<Post> findByIdAndDeletedIsFalse(Long postId);
     
     // 로그인유저가 좋아요누른 게시글
     @Query (
          nativeQuery = true,
          value = "select post.*, u.username, u.profile_url, pl.user_id as isLike  " +
               "from post " +
               "join users u on post.user_id = u.id " +
               "join post_like pl on post.id = pl.post_id and pl.user_id=:loggedUserId " +
               "where post.deleted is false and u.deleted is false " +
               "order by id desc limit :size" )
     List<PostUsernameInterface> findAllByDeletedIsFalseAndByUserAndUserLikeOrderByIdDesc(@Param("size")int size,  @Param("loggedUserId")Long loggedUserId);
     
     // 로그인유저가 팔로우한 사람들 게시글 (나의좋아요유무도 같이확인)
     @Query (
          nativeQuery = true,
          value = "select  p.*, u.username, u.profile_url, pl.user_id as isLike  " +
               "from post p " +
               "join follow f on f.user_id= :loggedUserId and p.user_id = f.follow_id " + // 로그인유저가 팔로우한 사람들
               "left join post_like pl on p.id = pl.post_id and pl.user_id=:loggedUserId " + // 로그인유저의 게시글 좋아요유무
               "join users u on p.user_id = u.id " + // 게시글 작성자 유저정보 용
               "where p.deleted is false and u.deleted is false " + // 삭제안된 게시글, 탈퇴안한사람의 게시글
               "order by p.id desc limit :size ") // 게시글 id이름 내림차순
     List<PostUsernameInterface> findByFollowedUser(@Param("size")int size,  @Param("loggedUserId")Long loggedUserId);
     
}
