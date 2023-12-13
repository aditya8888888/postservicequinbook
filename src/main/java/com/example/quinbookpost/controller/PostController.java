package com.example.quinbookpost.controller;

import com.example.quinbookpost.dto.PostDto;
import com.example.quinbookpost.dto.UserPostResponse;
import com.example.quinbookpost.entity.Feed;
import com.example.quinbookpost.entity.Post;
import com.example.quinbookpost.repository.FeedRepository;
import com.example.quinbookpost.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
@CrossOrigin
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FeedRepository feedRepository;

    @PostMapping
    public ResponseEntity<Boolean> addPost(@RequestBody PostDto postDto) {
        boolean isPostAdded = postService.addPost(postDto);
        return isPostAdded
                ? ResponseEntity.ok(true)  // Return OK status if the post is added successfully
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);  // Return Internal Server Error status if there's an issue
    }

    @GetMapping
    public ResponseEntity<PostDto> getPostById(@RequestParam String postId) {
        PostDto postDto = postService.getPostById(postId);
        return (postDto != null)
                ? ResponseEntity.ok(postDto)  // Return OK status with the postDto if the post is found
                : ResponseEntity.notFound().build();  // Return Not Found status if the post is not found
    }


    @GetMapping("/get-post-by-userid")
    public List<UserPostResponse> getPostByUserId(@RequestParam String userId){
        return postService.getPostByUserId(userId);
    }



}

