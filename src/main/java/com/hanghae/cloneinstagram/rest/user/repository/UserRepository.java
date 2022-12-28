package com.hanghae.cloneinstagram.rest.user.repository;

import com.hanghae.cloneinstagram.rest.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
     Optional<User> findByUsername(String username);
     Optional<User> findByEmail(String email);
     Optional<User> findByKakaoId(Long id);
     Boolean existsByUsername(String username);
     Boolean existsByEmail(String email);
     
     Optional<User> findByEmailAndKakaoIdIsNull(String email);
    boolean existsByIdAndDeletedIsFalse(Long followId);
}
