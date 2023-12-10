package com.example.quinbookpost.repository;

import com.example.quinbookpost.entity.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentCustomRepository {
    List<Comment> getAllCommentsForPost(String postId);
}
