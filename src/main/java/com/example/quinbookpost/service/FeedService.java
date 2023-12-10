package com.example.quinbookpost.service;

import com.example.quinbookpost.dto.FeedResponseDto;
import com.example.quinbookpost.dto.PostDto;
import com.example.quinbookpost.entity.Feed;

import java.util.List;

public interface FeedService {

    List<FeedResponseDto> getFeedByUser(String userId);

    Feed addFeed(String userId, String postId);

//    Feed updateFeed(String userId, String postId);

}
