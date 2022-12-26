package com.hanghae.cloneinstagram.rest.hashtag.service;

import com.hanghae.cloneinstagram.rest.hashtag.dto.HashtagListRequestDto;
import com.hanghae.cloneinstagram.rest.hashtag.dto.HashtagRequestDto;
import com.hanghae.cloneinstagram.rest.hashtag.model.Hashtag;
import com.hanghae.cloneinstagram.rest.hashtag.repository.HashtagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
@RequiredArgsConstructor
public class HashtagService {

    private final HashtagRepository hashtagRepository;

    @Transactional
    public void saveHashtag(Long id, String content) {
        HashtagListRequestDto hashtagListRequestDto = new HashtagListRequestDto();

        //문자열 첫번째 : #, 문자열 마지막 : 공백
        Pattern pattern = Pattern.compile("[#](.*?)[\\s]");

        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            System.out.println(matcher.group(1));
            hashtagRepository.save(new Hashtag(id,matcher.group(1)));
            //hashtagListRequestDto.addHashtag(new HashtagRequestDto(id,matcher.group(1)));
        }
    }
}
