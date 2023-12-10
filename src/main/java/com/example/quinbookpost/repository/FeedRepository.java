package com.example.quinbookpost.repository;

import com.example.quinbookpost.entity.Feed;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedRepository extends MongoRepository<Feed,String> {
}
