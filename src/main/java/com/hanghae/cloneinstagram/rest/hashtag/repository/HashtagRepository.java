package com.hanghae.cloneinstagram.rest.hashtag.repository;

import com.hanghae.cloneinstagram.rest.hashtag.model.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

}
