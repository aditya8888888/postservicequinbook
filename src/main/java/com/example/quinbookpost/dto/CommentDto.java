package com.example.quinbookpost.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CommentDto {

    private String userId;

    private String postId;

    private String commentDescription;
}
