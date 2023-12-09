package com.example.quinbookpost.repository;

import com.example.quinbookpost.entity.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository extends MongoRepository<Post,String> {

}
