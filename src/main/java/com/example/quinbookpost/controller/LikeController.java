package com.example.quinbookpost.controller;

import com.example.quinbookpost.dto.LikeDto;
import com.example.quinbookpost.dto.PostDto;
import com.example.quinbookpost.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/like")
@CrossOrigin
public class LikeController {

    @Autowired
    private LikeService likeService;

    @PostMapping
    public boolean addLikePost (@RequestBody LikeDto likeDto){
        return likeService.addLikePost(likeDto);
    }

    @DeleteMapping
    public void removeLikePost(@RequestParam String userId,@RequestParam String postId){
        likeService.removeLikeById(userId,postId);
    }
    @GetMapping("/like-count-by-post")
    public long getLikeCountByPostId(@RequestParam String postId) {
        return likeService.getLikeCountByPostId(postId);
    }
}
