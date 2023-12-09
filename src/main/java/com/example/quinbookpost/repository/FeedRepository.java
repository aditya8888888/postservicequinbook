package com.example.quinbookpost.repository;

import com.example.quinbookpost.entity.Feed;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FeedRepository extends MongoRepository<Feed,String> {
}
