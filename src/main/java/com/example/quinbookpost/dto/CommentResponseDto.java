package com.example.quinbookpost.dto;

import lombok.Data;

@Data
public class CommentResponseDto {
    private String description;
    private String userId;
    private String userName;
}
