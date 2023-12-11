package com.example.quinbookpost.service;

import com.example.quinbookpost.dto.LikeDto;
import com.example.quinbookpost.dto.PostLikeCount;
import com.example.quinbookpost.entity.Like;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface LikeService {

    public boolean addLikePost (LikeDto likeDto);

    public void removeLikeById(LikeDto likeDto);

    public long getLikeCountByPostId(String postId);

    public boolean toggleLike(LikeDto likeDto);


}
