package com.example.quinbookpost.controller;

import com.example.quinbookpost.dto.CommentDto;
import com.example.quinbookpost.dto.CommentResponseDto;
import com.example.quinbookpost.dto.LikeDto;
import com.example.quinbookpost.dto.UserDto;
import com.example.quinbookpost.entity.Comment;
import com.example.quinbookpost.feignClient.UserFeign;
import com.example.quinbookpost.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/comment")
@CrossOrigin
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private UserFeign userFeign;

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
    public List<CommentResponseDto> getCommentByPost(@RequestParam String postId){
        List<CommentResponseDto> commentResponseDto = new ArrayList<>();
        List<Comment> commentList = commentService.getCommentByPostId(postId);

        for(Comment comment: commentList){
            CommentResponseDto commentResponseDto1 = new CommentResponseDto();
            BeanUtils.copyProperties(comment, commentResponseDto1);
            commentResponseDto1.setDescription(comment.getCommentDescription());

            UserDto userDto = userFeign.getUserById(comment.getUserId());
            commentResponseDto1.setUserName(userDto.getUserName());
            commentResponseDto1.setUserProfilePic(userDto.getUserProfilePic());

            commentResponseDto.add(commentResponseDto1);
        }

        return commentResponseDto;
    }
}
