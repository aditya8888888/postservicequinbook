package com.example.quinbookpost.service.impl;

import com.example.quinbookpost.dto.LikeDto;
import com.example.quinbookpost.dto.LikeIdResponse;
import com.example.quinbookpost.dto.PostDto;
import com.example.quinbookpost.dto.PostLikeCount;
import com.example.quinbookpost.entity.Like;
import com.example.quinbookpost.repository.LikeRepository;
import com.example.quinbookpost.repository.PostRepository;
import com.example.quinbookpost.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    private LikeRepository likeRepository;


    @Override
    public boolean addLikePost(LikeDto likeDto) {
        Optional<LikeIdResponse> existingLike = likeRepository.findLikeIdByUserIdAndPostId(likeDto.getUserId(), likeDto.getPostId());

        if (existingLike.isPresent()) {
            System.out.println("Like already exists for userId: " + likeDto.getUserId() + " and postId: " + likeDto.getPostId());
            return false;
        } else {
            // Create a new like and save it
            Like like = new Like();
            like.setLikeDate(new Date());
            like.setLikeId(UUID.randomUUID().toString());
            like.setUserId(likeDto.getUserId());
            like.setPostId(likeDto.getPostId());

            Like savedLike = likeRepository.save(like);
            return Objects.nonNull(savedLike);
        }
    }


        @Override
        public void removeLikeById(LikeDto likeDto) {
            // Find the likeId by userId and postId
            Optional<LikeIdResponse> likeIdOptional = likeRepository.findLikeIdByUserIdAndPostId(likeDto.getUserId(), likeDto.getPostId());

            if (likeIdOptional.isPresent()) {
                LikeIdResponse likeIdResponse = likeIdOptional.get();
                String likeId = likeIdResponse.getLikeId();
                likeRepository.deleteById(likeId);
                System.out.println("Like with id " + likeId + " deleted successfully.");
            } else {
                System.out.println("No like found for userId: " + likeDto.getUserId() + " and postId: " + likeDto.getPostId());
            }
        }

        @Override
    public long getLikeCountByPostId(String postId) {
        return likeRepository.countByPostId(postId);
    }

    @Override
    public boolean toggleLike(LikeDto likeDto) {
        try {
            // Check if the like already exists
            Optional<LikeIdResponse> likeIdOptional = likeRepository.findLikeIdByUserIdAndPostId(likeDto.getUserId(), likeDto.getPostId());

            if (likeIdOptional.isPresent()) {
                // Like exists, remove it
                LikeIdResponse likeIdResponse = likeIdOptional.get();
                String likeId = likeIdResponse.getLikeId();
                likeRepository.deleteById(likeId);
                System.out.println("Like with id " + likeId + " removed successfully.");

            } else {
                // Like doesn't exist, add it
                Like like = new Like();
                like.setLikeDate(new Date());
                like.setLikeId(UUID.randomUUID().toString());
                like.setUserId(likeDto.getUserId());
                like.setPostId(likeDto.getPostId());
                Like savedLike = likeRepository.save(like);

                if (Objects.nonNull(savedLike)) {
                    System.out.println("Like added successfully.");
                    return true;
                } else {

                    System.out.println("Failed to add like.");
                    return false;
                }
            }

        } catch (DataIntegrityViolationException e) {
            // Handle database constraint violations
            // For example, log the exception, provide a custom error message, or rethrow a more specific exception
            e.printStackTrace(); // Log or handle the exception accordingly
        } catch (Exception e) {
            // Handle other exceptions that may occur
            e.printStackTrace(); // Log or handle the exception accordingly
        }
        return  false;
    }
}
