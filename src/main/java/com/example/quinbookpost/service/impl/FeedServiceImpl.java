package com.example.quinbookpost.service.impl;

import com.example.quinbookpost.dto.*;
import com.example.quinbookpost.entity.Comment;
import com.example.quinbookpost.entity.Feed;
import com.example.quinbookpost.entity.Post;
import com.example.quinbookpost.feignClient.UserFeign;
import com.example.quinbookpost.repository.CommentCustomRepository;
import com.example.quinbookpost.repository.FeedRepository;
import com.example.quinbookpost.repository.PostRepository;
import com.example.quinbookpost.service.CommentService;
import com.example.quinbookpost.service.FeedService;
import com.example.quinbookpost.service.LikeService;
import com.example.quinbookpost.service.PostService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FeedServiceImpl implements FeedService {

    @Autowired
    FeedRepository feedRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    LikeService likeService;

    @Autowired
    CommentService commentService;

    @Autowired
    CommentCustomRepository commentCustomRepository;

    @Autowired
    UserFeign userFeign;

    @Override
    public List<FeedResponseDto> getFeedByUser(String userId) {

        Feed feed = feedRepository.findById(userId).orElse(null);
        List<FeedResponseDto> feedList = new ArrayList<>();

        for( String postId : feed.getPostList()){
            FeedResponseDto feedDto = new FeedResponseDto();

            Post post = postRepository.findById(postId).orElse(null);
            BeanUtils.copyProperties(post, feedDto);

            String userId1 = post.getUserId();
            UserDto userDto = userFeign.getUserById(userId1);


            BeanUtils.copyProperties(userDto, feedDto);

            //feign call for userId and userName from post entity

            feedDto.setLikeCount(likeService.getLikeCountByPostId(postId));
            feedDto.setCommentCount(commentService.getCommentCountByPostId(postId));

            List<Comment> comments = commentCustomRepository.getAllCommentsForPost(postId);  //not done
            List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();

            for( Comment comment: comments){
                CommentResponseDto commentResponseDto = new CommentResponseDto();
                UserDto userDto1 = new UserDto();
                userDto1 = userFeign.getUserById(comment.getUserId());
                commentResponseDto.setUserId(userDto1.getUserId());
                commentResponseDto.setDescription(comment.getCommentDescription());
                commentResponseDto.setUserName(userDto1.getUserName());
                commentResponseDtoList.add(commentResponseDto);

            }

            feedDto.setCommentList(commentResponseDtoList);


            feedList.add(feedDto);
        }

        return feedList;
    }

    @Override
    public Feed addFeed(String userId, String postId) {


        Optional<Feed> feedOptional = feedRepository.findById(userId);
        Feed feed = new Feed();
        if(feedOptional.isPresent()){
            feed = feedOptional.get();
            feed.getPostList().add(postId);
        }else{
            feed = new Feed();
            feed.setUserId(userId);
            List<String> postList = new ArrayList<>();
            postList.add(postId);
            feed.setPostList(postList);
        }
        return feedRepository.save(feed);
    }

//    public Feed getFeedDetails(String userId){
//        return feedRepository.findById(userId).orElse(null);
//    }

//    @Override
//    public Feed updateFeed(String userId, String postId){
//        Feed feed = feedRepository.findById(userId).orElse(null);
//
//        if(feed.getPostList() == null){
//            List<String> postList = new ArrayList<>();
//            postList.add(postId);
//            feed.setPostList(postList);
//        }else {
//            feed.getPostList().add(postId);
//        }
//
//
//        return  feedRepository.save(feed);
//    }
}
