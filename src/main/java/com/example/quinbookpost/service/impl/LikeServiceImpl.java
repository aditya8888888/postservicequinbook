package com.example.quinbookpost.service.impl;

import com.example.quinbookpost.dto.LikeDto;
import com.example.quinbookpost.dto.PostDto;
import com.example.quinbookpost.dto.PostLikeCount;
import com.example.quinbookpost.entity.Like;
import com.example.quinbookpost.repository.LikeRepository;
import com.example.quinbookpost.repository.PostRepository;
import com.example.quinbookpost.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    private LikeRepository likeRepository;


    @Override
    public boolean addLikePost(LikeDto likeDto) {
        Like like=new Like();
        like.setLikeDate(likeDto.getLikeDate());
        like.setLikeId(UUID.randomUUID().toString());
        like.setUserId(likeDto.getUserId());
        like.setPostId(likeDto.getPostId());
        Like savedLike=likeRepository.save(like);
        return Objects.nonNull(savedLike);
    }


        @Override
        public void removeLikeById(String userId, String postId) {
            // Find the likeId by userId and postId
            Optional<String> likeIdOptional = likeRepository.findLikeIdByUserIdAndPostId(userId, postId);

            if (likeIdOptional.isPresent()) {
                String likeId = likeIdOptional.get();
                likeRepository.deleteById(likeId);
                System.out.println("Like with id " + likeId + " deleted successfully.");
            } else {
                System.out.println("No like found for userId: " + userId + " and postId: " + postId);
            }
        }

        @Override
    public long getLikeCountByPostId(String postId) {
        return likeRepository.countByPostId(postId);
    }
}
