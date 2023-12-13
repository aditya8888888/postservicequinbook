package com.example.quinbookpost.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class UserPostResponse {

    private String userId;

    private String postId;

    private String userName;

    private String userProfilePic;

    private String caption;

    private String media;

    private String mediaType;

    private Date createdDate;

    private long likeCount;

    private long commentCount;

    private List<CommentResponseDto> commentList = new ArrayList<>();



}
