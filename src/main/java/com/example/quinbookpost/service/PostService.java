package com.example.quinbookpost.service;

import com.example.quinbookpost.dto.PostDto;
import com.example.quinbookpost.entity.Post;
import org.springframework.web.bind.annotation.RequestParam;

public interface PostService {

    public boolean addPost(PostDto postDto);

    public PostDto getPostById(@RequestParam String postId);


}
