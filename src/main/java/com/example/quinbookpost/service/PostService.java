package com.example.quinbookpost.service;

import com.example.quinbookpost.dto.PostDto;
import com.example.quinbookpost.dto.UserPostResponse;
import com.example.quinbookpost.entity.Post;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface PostService {

    public boolean addPost(PostDto postDto);

    public PostDto getPostById(@RequestParam String postId);

    public List<UserPostResponse> getPostByUserId(String userId);


}
