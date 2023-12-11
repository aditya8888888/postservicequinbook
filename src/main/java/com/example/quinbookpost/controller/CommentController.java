package com.example.quinbookpost.controller;

import com.example.quinbookpost.dto.CommentDto;
import com.example.quinbookpost.dto.LikeDto;
import com.example.quinbookpost.entity.Comment;
import com.example.quinbookpost.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
@CrossOrigin
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping
    public boolean addCommentPost (@RequestBody CommentDto commentDto){
        return commentService.addCommentPost(commentDto);
    }

    @DeleteMapping
    public void removeCommentPost(@RequestParam String userId,@RequestParam String postId){
        commentService.removeCommentById(userId,postId);
    }

    @GetMapping("/comment-count-by-post")
    public long getLikeCountByPostId(@RequestParam String postId) {
        return commentService.getCommentCountByPostId(postId);
    }

    @GetMapping("/comment-by-post")
    public List<Comment> getCommentByPost(@RequestParam String postId){
        return commentService.getCommentByPostId(postId);
    }
}
