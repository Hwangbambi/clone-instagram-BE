package com.hanghae.cloneinstagram.rest.post.dto;

import com.hanghae.cloneinstagram.rest.comment.model.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostRequestDto {
    private String content;
    private MultipartFile file;

}
