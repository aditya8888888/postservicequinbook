package com.example.quinbookpost.dto;

import lombok.Data;

import java.util.List;

@Data
public class FeedDto {

    private String userId;

    private List<String> postList;
}
