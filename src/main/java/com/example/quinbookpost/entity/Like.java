package com.example.quinbookpost.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = Like.COLLECTION_NAME)
@Data
public class Like {
    public static final String COLLECTION_NAME = "like";

    @Id
    private String likeId;

    private String userId;

    private String postId;

    private Date likeDate;

}
