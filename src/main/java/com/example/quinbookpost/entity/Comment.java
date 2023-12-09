package com.example.quinbookpost.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = Comment.COLLECTION_NAME)
@Data
public class Comment {
    public static final String COLLECTION_NAME = "comment";

    @Id
    private String commentId;

    private String userId;

    private String postId;

    private Date commentDate;

    private String commentDescription;
}
