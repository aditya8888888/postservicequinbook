package com.example.quinbookpost.repository;

import com.example.quinbookpost.dto.PostLikeCount;
import com.example.quinbookpost.entity.Like;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

//public interface LikeRepository extends MongoRepository<Like,String> {
//
//
//}
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends MongoRepository<Like, String> {
    @Query(value = "{ 'userId' : ?0, 'postId' : ?1 }", fields = "{ 'likeId' : 1}")
    Optional<String> findLikeIdByUserIdAndPostId(String userId, String postId);

    @Query(value = "{ 'postId' : ?0 }", count = true)
    long countByPostId(String postId);


}

