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
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
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


    PostService postService;

    FeedServiceImpl() {
        this.postService = new PostServiceImpl();
    }

   @Override
    public Response createFeed(String userId) {
        try {
            // Check if the feed already exists for the user
            if (feedRepository.existsById(userId)) {
                return new Response("Feed already exists for user: " + userId, null, null);
            }

            // Create a new feed for the user
            Feed newFeed = new Feed();
            newFeed.setUserId(userId);
            newFeed.setPostList(Collections.emptyList());

            // Save the new feed to the repository
            feedRepository.save(newFeed);

            return new Response("Feed created successfully for user: " + userId, null, null);
        } catch (Exception e) {
            return new Response(null, "Failed to create feed: " + e.getMessage(), null);
        }
    }

//    @Override
//    public List<FeedResponseDto> getFeedByUser(String userId) {
//
//        Feed feed = feedRepository.findById(userId).orElse(null);
//        List<FeedResponseDto> feedList = new ArrayList<>();
//
//        for (String postId : feed.getPostList()) {
//            FeedResponseDto feedDto = new FeedResponseDto();
//
//            Post post = postRepository.findById(postId).orElse(null);
//            BeanUtils.copyProperties(post, feedDto);
//
//            String userId1 = post.getUserId();
//            UserDto userDto = userFeign.getUserById(userId1);
//
//
//            BeanUtils.copyProperties(userDto, feedDto);
//
//            //feign call for userId and userName from post entity
//
//            feedDto.setLikeCount(likeService.getLikeCountByPostId(postId));
//            feedDto.setCommentCount(commentService.getCommentCountByPostId(postId));
//
//            List<Comment> comments = commentCustomRepository.getAllCommentsForPost(postId);  //not done
//            List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
//
//            for (Comment comment : comments) {
//                CommentResponseDto commentResponseDto = new CommentResponseDto();
//                UserDto userDto1 = new UserDto();
//                userDto1 = userFeign.getUserById(comment.getUserId());
//                commentResponseDto.setUserId(userDto1.getUserId());
//                commentResponseDto.setDescription(comment.getCommentDescription());
//                commentResponseDto.setUserName(userDto1.getUserName());
//                commentResponseDto.setUserProfilePic(userDto1.getUserProfilePic());
//                commentResponseDtoList.add(commentResponseDto);
//
//            }
//
//            feedDto.setCommentList(commentResponseDtoList);
//
//
//            feedList.add(feedDto);
//        }
//
//        return feedList;
//    }

    @Override
    public List<FeedResponseDto> getFeedByUser(String userId) {
        Feed feed = feedRepository.findById(userId).orElse(null);
        List<FeedResponseDto> feedList = new ArrayList<>();

        // Check if feed is not null before proceeding
        if (feed != null) {
            for (String postId : feed.getPostList()) {
                FeedResponseDto feedDto = new FeedResponseDto();

                Post post = postRepository.findById(postId).orElse(null);

                // Check if post is not null before proceeding
                if (post != null) {
                    BeanUtils.copyProperties(post, feedDto);

                    String userId1 = post.getUserId();
                    UserDto userDto = userFeign.getUserById(userId1);

                    // Check if userDto is not null before proceeding
                    if (userDto != null) {
                        BeanUtils.copyProperties(userDto, feedDto);

                        feedDto.setLikeCount(likeService.getLikeCountByPostId(postId));
                        feedDto.setCommentCount(commentService.getCommentCountByPostId(postId));

                        List<Comment> comments = commentCustomRepository.getAllCommentsForPost(postId);

                        // Check if comments is not null before proceeding
                        if (comments != null) {
                            List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();

                            for (Comment comment : comments) {
                                CommentResponseDto commentResponseDto = new CommentResponseDto();
                                UserDto userDto1 = userFeign.getUserById(comment.getUserId());

                                // Check if userDto1 is not null before proceeding
                                if (userDto1 != null) {
                                    commentResponseDto.setUserId(userDto1.getUserId());
                                    commentResponseDto.setDescription(comment.getCommentDescription());
                                    commentResponseDto.setUserName(userDto1.getUserName());
                                    commentResponseDto.setUserProfilePic(userDto1.getUserProfilePic());
                                    commentResponseDtoList.add(commentResponseDto);
                                }
                            }

                            feedDto.setCommentList(commentResponseDtoList);
                        }

                        feedList.add(feedDto);
                    }
                }
            }
        }

        return feedList;
    }


//    @Override
//    public Feed addFeed(String userId, String postId) {
//
//
//        Optional<Feed> feedOptional = feedRepository.findById(userId);
//        Feed feed = new Feed();
//        if (feedOptional.isPresent()) {
//            feed = feedOptional.get();
//            feed.getPostList().add(postId);
//        } else {
//            feed = new Feed();
//            feed.setUserId(userId);
//            List<String> postList = new ArrayList<>();
//            postList.add(postId);
//            feed.setPostList(postList);
//        }
//        return feedRepository.save(feed);
//    }

    @Override
    public Feed addFeed(String userId, String postId) {
        Optional<Feed> feedOptional = feedRepository.findById(userId);
        Feed feed;

        if (feedOptional.isPresent()) {
            feed = feedOptional.get();
            if (feed.getPostList() != null) {
                feed.getPostList().add(postId);
            } else {
                List<String> postList = new ArrayList<>();
                postList.add(postId);
                feed.setPostList(postList);
            }
        } else {
            feed = new Feed();
            feed.setUserId(userId);
            List<String> postList = new ArrayList<>();
            postList.add(postId);
            feed.setPostList(postList);
        }

        return feedRepository.save(feed);
    }



//    @Override
//     public Boolean updateFeed(String userId,String requestedId ){
//
//        Optional<Feed> feedOne = feedRepository.findById(userId);
//        Optional<Feed> feedSecond = feedRepository.findById(requestedId);
//
//        if(feedOne.isPresent() && feedSecond.isPresent()){
//           List<UserPostResponse> userOne =  postService.getPostByUserId(userId);
//           List<UserPostResponse> userTwo = postService.getPostByUserId(requestedId);
//
//
//           for(UserPostResponse userPostOne: userOne){
//               feedSecond.get().getPostList().add(userPostOne.getPostId());
//           }
//
//           for(UserPostResponse userPostTwo: userTwo){
//               feedOne.get().getPostList().add(userPostTwo.getPostId());
//           }
//
//           feedRepository.save(feedOne.get());
//           feedRepository.save(feedSecond.get());
//
//           return true;
//
//
//        }else if( feedOne.isPresent() ){
//
//            List<UserPostResponse> userOne =  postService.getPostByUserId(userId);
//
//            Feed feedForSecond = new Feed();
//            feedForSecond.setUserId(requestedId);
//
//            List<String> postList = new ArrayList<>();
//            for(UserPostResponse userPostOne: userOne){
//                postList.add(userPostOne.getPostId());
//            }
//            feedForSecond.setPostList(postList);
//
//            feedRepository.save(feedForSecond);
//            return true;
//
//        }else if(feedSecond.isPresent()){
//
//
//            List<UserPostResponse> userSecond =  postService.getPostByUserId(requestedId);
//
//            Feed feedForOne = new Feed();
//            feedForOne.setUserId(userId);
//
//            List<String> postList = new ArrayList<>();
//            for(UserPostResponse userPostSecond: userSecond){
//                postList.add(userPostSecond.getPostId());
//            }
//            feedForOne.setPostList(postList);
//
//            feedRepository.save(feedForOne);
//            return true;
//
//        }else {
//            return false;
//        }
//
//     }


    @Override
    public Boolean updateFeed(String userId, String requestedId) {
        Optional<Feed> feedOne = feedRepository.findById(userId);
        Optional<Feed> feedSecond = feedRepository.findById(requestedId);

        if (feedOne.isPresent() && feedSecond.isPresent()) {
            List<UserPostResponse> userOne = postService.getPostByUserId(userId);
            List<UserPostResponse> userTwo = postService.getPostByUserId(requestedId);

            if (userOne != null) {
                for (UserPostResponse userPostOne : userOne) {
                    feedSecond.get().getPostList().add(userPostOne.getPostId());
                }
            }

            if (userTwo != null) {
                for (UserPostResponse userPostTwo : userTwo) {
                    feedOne.get().getPostList().add(userPostTwo.getPostId());
                }
            }

            feedRepository.save(feedOne.get());
            feedRepository.save(feedSecond.get());

            return true;

        } else if (feedOne.isPresent()) {
            List<UserPostResponse> userOne = postService.getPostByUserId(userId);

            Feed feedForSecond = new Feed();
            feedForSecond.setUserId(requestedId);

            if (userOne != null) {
                List<String> postList = new ArrayList<>();
                for (UserPostResponse userPostOne : userOne) {
                    postList.add(userPostOne.getPostId());
                }
                feedForSecond.setPostList(postList);
            }

            feedRepository.save(feedForSecond);
            return true;

        } else if (feedSecond.isPresent()) {
            List<UserPostResponse> userSecond = postService.getPostByUserId(requestedId);

            Feed feedForOne = new Feed();
            feedForOne.setUserId(userId);

            if (userSecond != null) {
                List<String> postList = new ArrayList<>();
                for (UserPostResponse userPostSecond : userSecond) {
                    postList.add(userPostSecond.getPostId());
                }
                feedForOne.setPostList(postList);
            }

            feedRepository.save(feedForOne);
            return true;

        } else {
            List<UserPostResponse> userOne = postService.getPostByUserId(userId);
            List<UserPostResponse> userTwo = postService.getPostByUserId(requestedId);

            Feed feedForOne = new Feed();
            Feed feedForSecond = new Feed();

            feedForOne.setUserId(userId);
            feedForSecond.setUserId(requestedId);

            List<String> feedOneList = new ArrayList<>();
            List<String> feedSecondList = new ArrayList<>();

            if (userOne != null) {
                for (UserPostResponse userPostOne : userOne) {
                    feedSecondList.add(userPostOne.getPostId());
                }
            }


            if (userTwo != null) {
                for (UserPostResponse userPostTwo : userTwo) {
                    feedOneList.add(userPostTwo.getPostId());
                }
            }

            feedForOne.setPostList(feedOneList);
            feedForSecond.setPostList(feedSecondList);

            feedRepository.save(feedForOne);
            feedRepository.save(feedForSecond);


            return false;
        }
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
