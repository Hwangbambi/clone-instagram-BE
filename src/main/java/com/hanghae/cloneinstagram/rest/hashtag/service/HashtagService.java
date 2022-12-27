package com.hanghae.cloneinstagram.rest.hashtag.service;

import com.hanghae.cloneinstagram.rest.hashtag.model.Hashtag;
import com.hanghae.cloneinstagram.rest.hashtag.repository.HashtagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
@RequiredArgsConstructor
public class HashtagService {

    private final HashtagRepository hashtagRepository;
    
    @Transactional
    public void saveHashtag(Long id, String content) {
        //해시태그 정규식
        Pattern pattern = Pattern.compile("#[^\\s#]+");
        Matcher matcher = pattern.matcher(content);
        
        while (matcher.find()) {
            String hashtag = matcher.group().split("#")[1];
            System.out.println("hashtag : " + hashtag);
            hashtagRepository.save(new Hashtag(id,hashtag));
        }
    }

    @Transactional
    public void deleteHashtag(Long postId) {
        List<Hashtag> hashtagList = hashtagRepository.findByPostId(postId);
        for (Hashtag hashtag : hashtagList) {
            hashtagRepository.delete(hashtag);
        }

    }
}
