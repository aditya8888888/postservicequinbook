package com.example.quinbookpost.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = Post.COLLECTION_NAME)
@Data
public class Post {
    public static final String COLLECTION_NAME = "post";

    @Id
    private String postId;

    private String userId;

    private String caption;

    private String media;

    private String mediaType;

    private Date createdDate;

}
