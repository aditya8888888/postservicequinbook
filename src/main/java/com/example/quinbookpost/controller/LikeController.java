package com.example.quinbookpost.controller;

import com.example.quinbookpost.dto.LikeDto;
import com.example.quinbookpost.dto.PostDto;
import com.example.quinbookpost.dto.Response;
import com.example.quinbookpost.service.LikeService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public void removeLikePost(@RequestBody LikeDto likeDto){
        likeService.removeLikeById(likeDto);
    }
    @GetMapping("/like-count-by-post")
    public long getLikeCountByPostId(@RequestParam String postId) {
        return likeService.getLikeCountByPostId(postId);
    }

    @PutMapping("/toggle-like")
    public ResponseEntity<Response> toggleLike(@RequestBody LikeDto likeDto){
        Boolean status = likeService.toggleLike(likeDto);
        Response response = new Response();
        if(status){
            response.setMessage("Operation Successful");
        }else {
            response.setError("Operation failed");
        }
        return ResponseEntity.ok(response);
    }
}
