package com.example.quinbookpost.service.impl;

import com.example.quinbookpost.dto.PostDto;
import com.example.quinbookpost.entity.Post;
import com.example.quinbookpost.feignClient.UserFeign;
import com.example.quinbookpost.repository.PostRepository;
import com.example.quinbookpost.service.FeedService;
import com.example.quinbookpost.service.PostService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PostServiceImpl implements PostService {


    @Autowired
    private PostRepository postRepository;

    @Autowired
    UserFeign userFeign;

    @Autowired
    FeedService feedService;

    @Override
    public boolean addPost(PostDto postDto){
        Post post = new Post();
        BeanUtils.copyProperties(postDto, post);
        post.setPostId(UUID.randomUUID().toString());
        Post savedPost = postRepository.save(post);

        //feign call to get all the friends list to update in there feed.
        String userId = savedPost.getUserId();
        List<String> friends = userFeign.getAllFriends(userId);

        for(String friendId: friends){
            feedService.addFeed(friendId, savedPost.getPostId());
        }

        return Objects.nonNull(savedPost);

    }

    @Override
    public PostDto getPostById(String postId) {
        Post Post=postRepository.findById(postId).orElse(null);
        PostDto postDto=new PostDto();
        BeanUtils.copyProperties(Post,postDto);
        return postDto;
    }

    public List<Post> getPostByUserId(String userId){
        return postRepository.getPostByUserId(userId);
    }


}
