package com.example.quinbookpost.service.impl;

import com.example.quinbookpost.dto.FeedResponseDto;
import com.example.quinbookpost.dto.PostDto;
import com.example.quinbookpost.entity.Feed;
import com.example.quinbookpost.entity.Post;
import com.example.quinbookpost.repository.FeedRepository;
import com.example.quinbookpost.repository.PostRepository;
import com.example.quinbookpost.service.FeedService;
import com.example.quinbookpost.service.PostService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FeedServiceImpl implements FeedService {

    @Autowired
    FeedRepository feedRepository;

    @Autowired
    PostRepository postRepository;


    @Override
    public List<FeedResponseDto> getFeedByUser(String userId) {

        Feed feed = feedRepository.findById(userId).orElse(null);
        List<FeedResponseDto> feedList = new ArrayList<>();

        for( String postId : feed.getPostList()){
            FeedResponseDto feedDto = new FeedResponseDto();
            Post post = new Post();
            //feign call for userId and userName from post entity

            post = postRepository.findById(postId).orElse(null);

            BeanUtils.copyProperties(post, feedDto);




        }




        return null;
    }

    @Override
    public Feed addFeed(String userId, String postId) {

        Feed feed = new Feed();
        feed.setUserId(userId);
        feed.getPostList().add(postId);
        return feedRepository.save(feed);
    }
}
