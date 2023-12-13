package com.example.quinbookpost.service;

import com.example.quinbookpost.dto.FeedResponseDto;
import com.example.quinbookpost.dto.PostDto;
import com.example.quinbookpost.dto.Response;
import com.example.quinbookpost.entity.Feed;

import java.util.List;

public interface FeedService {

    List<FeedResponseDto> getFeedByUser(String userId);

    Feed addFeed(String userId, String postId);

    Boolean updateFeed(String userId,String requestedId );

//    Feed updateFeed(String userId, String postId);
    Response createFeed(String userId);

}
