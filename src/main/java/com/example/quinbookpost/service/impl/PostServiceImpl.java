package com.example.quinbookpost.service.impl;

import com.example.quinbookpost.dto.PostDto;
import com.example.quinbookpost.entity.Post;
import com.example.quinbookpost.repository.PostRepository;
import com.example.quinbookpost.service.PostService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {


    @Autowired
    private PostRepository postRepository;

    @Override
    public boolean addPost(PostDto postDto){
        Post post = new Post();
        BeanUtils.copyProperties(postDto, post);
        post.setPostId(UUID.randomUUID().toString());
        Post savedPost = postRepository.save(post);
        return Objects.nonNull(savedPost);

    }

    @Override
    public PostDto getPostById(String postId) {
        Post Post=postRepository.findById(postId).orElse(null);
        PostDto postDto=new PostDto();
        BeanUtils.copyProperties(Post,postDto);
        return postDto;
    }



}
