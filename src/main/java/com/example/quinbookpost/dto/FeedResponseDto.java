package com.example.quinbookpost.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedResponseDto {

    private String postId;
    private String userId;
    private String userName;
    private String userProfilePic;
    private Date createdDate;
    private String caption;
    private String media;
    private int likeCount;
    private int commentCount;
    private List<CommentResponseDto> commentList = new ArrayList<>();
}
