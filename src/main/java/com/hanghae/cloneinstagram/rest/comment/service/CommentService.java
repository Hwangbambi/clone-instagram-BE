package com.hanghae.cloneinstagram.rest.comment.service;

import com.hanghae.cloneinstagram.rest.comment.model.Comment;
import com.hanghae.cloneinstagram.rest.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    @Transactional(readOnly = true)
    public List<Comment> getCommentList(Long postId) {
        //작성일 기준 내림차순, deleted is false
        List<Comment> commentList = commentRepository.findByPostIdAndDeletedIsFalseOrderByCreatedAtDesc(postId);
        return commentList;
    }
}
