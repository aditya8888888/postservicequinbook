package com.example.quinbookpost.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = Feed.COLLECTION_NAME)
@Data
public class Feed {
    public static final String COLLECTION_NAME = "feed";

    @Id
    private String userId;

    private List<String> postList = new ArrayList<>();
}
