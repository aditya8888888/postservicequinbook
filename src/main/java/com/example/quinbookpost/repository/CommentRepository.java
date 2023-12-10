package com.example.quinbookpost.repository;

import com.example.quinbookpost.dto.CommentResponseDto;
import com.example.quinbookpost.entity.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CommentRepository extends MongoRepository<Comment,String> {

    @Query(value = "{ 'userId' : ?0, 'postId' : ?1 }", fields = "{ 'commentId' : 1}")
    Optional<String> findCommentIdByUserIdAndPostId(String userId, String postId);

    @Query(value = "{ 'postId' : ?0 }", count = true)
    long countByPostId(String postId);

    List<CommentResponseDto> findByPostId(String postId);

}
