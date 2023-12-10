package com.example.quinbookpost.repository.impl;

import com.example.quinbookpost.entity.Comment;
import com.example.quinbookpost.repository.CommentCustomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentCustomRepositoryImpl implements CommentCustomRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List<Comment> getAllCommentsForPost(String postId) {
        Query query = new Query(Criteria.where("postId").is(postId));
        return mongoTemplate.find(query, Comment.class);
    }
}
