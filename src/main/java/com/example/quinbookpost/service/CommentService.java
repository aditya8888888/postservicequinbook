package com.example.quinbookpost.service;

import com.example.quinbookpost.dto.CommentDto;
import com.example.quinbookpost.dto.CommentResponseDto;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface CommentService {

    public boolean addCommentPost (@RequestBody CommentDto commentDto);
    public void removeCommentById(String userId,String postId);
    public long getCommentCountByPostId(String postId);
    public List<CommentResponseDto> getCommentByPostId(String postId);
}
