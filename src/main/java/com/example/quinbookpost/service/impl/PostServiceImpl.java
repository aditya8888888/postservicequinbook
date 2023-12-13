package com.example.quinbookpost.service.impl;

import com.example.quinbookpost.dto.*;
import com.example.quinbookpost.entity.Comment;
import com.example.quinbookpost.entity.Post;
import com.example.quinbookpost.feignClient.UserFeign;
import com.example.quinbookpost.repository.CommentCustomRepository;
import com.example.quinbookpost.repository.PostRepository;
import com.example.quinbookpost.service.CommentService;
import com.example.quinbookpost.service.FeedService;
import com.example.quinbookpost.service.LikeService;
import com.example.quinbookpost.service.PostService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PostServiceImpl implements PostService {


    @Autowired
    private PostRepository postRepository;

    @Autowired
    UserFeign userFeign;

    @Autowired
    FeedService feedService;

    @Autowired
    LikeService likeService;

    @Autowired
    CommentService commentService;

    @Autowired
    CommentCustomRepository commentCustomRepository;

    @Autowired
    EmailProducer emailProducer;

//
//    @Override
//    public boolean addPost(PostDto postDto){
//        System.out.println("hokjgfop");
//        Post post = new Post();
//        BeanUtils.copyProperties(postDto, post);
//        UserDto userDto=userFeign.getUserById(postDto.getUserId());
//        post.setUserEmail(userDto.getUserEmail());
//        post.setPostId(UUID.randomUUID().toString());
//        Post savedPost = postRepository.save(post);
//
////        EmailDetails emailDetails=new EmailDetails();
////        emailDetails.setBody("Congratsss you have posted");
////        emailDetails.setRecipient(savedPost.getUserEmail());
////        emailDetails.setSource("Facebook");
////        emailDetails.setSubject("Hurray");
////
////        emailProducer.sendEmail(emailDetails);
//        //feign call to get all the friends list to update in there feed.
//        String userId = savedPost.getUserId();
//        List<String> friends = userFeign.getAllFriends(userId);
//
//        if(friends)
//        for(String friendId: friends){
//            feedService.addFeed(friendId, savedPost.getPostId());
//        }
//
//        return Objects.nonNull(savedPost);
//
//    }

    @Override
    public boolean addPost(PostDto postDto) {
        // Create a new Post object and copy properties
        Post post = new Post();
        BeanUtils.copyProperties(postDto, post);
        post.setCreatedDate(new Date());

        // Get user information using Feign client
        UserDto userDto = userFeign.getUserById(postDto.getUserId());

        // Check if the userDto is not null before accessing properties
        if (userDto != null) {
            post.setUserEmail(userDto.getUserEmail());
            post.setPostId(UUID.randomUUID().toString());

            // Save the post
            Post savedPost = postRepository.save(post);

            // Fetch friends using Feign client
            String userId = savedPost.getUserId();
            List<String> friends = userFeign.getAllFriends(userId);

            // Check if friends is not null before processing
            if (friends != null) {
                // Iterate through friends and add to their feeds
                for (String friendId : friends) {
                    feedService.addFeed(friendId, savedPost.getPostId());
                }
            }

            return true; // Indicates success
        } else {
            // Handle the case where userDto is null (e.g., user not found)
            return false; // Indicates failure
        }
    }


    @Override
    public PostDto getPostById(String postId) {
        Post Post=postRepository.findById(postId).orElse(null);
        PostDto postDto=new PostDto();
        BeanUtils.copyProperties(Post,postDto);
        return postDto;
    }

    public List<UserPostResponse> getPostByUserId(String userId){
        List<UserPostResponse> responses = new ArrayList<>();
        List<Post> postList = postRepository.getPostByUserId(userId);
        for(Post post: postList){
            UserPostResponse userPostResponse = new UserPostResponse();
            BeanUtils.copyProperties(post, userPostResponse);

            String userId1 = post.getUserId();
            UserDto userDto = userFeign.getUserById(userId1);
            BeanUtils.copyProperties(userDto, userPostResponse);

            userPostResponse.setLikeCount(likeService.getLikeCountByPostId(post.getPostId()));
            userPostResponse.setCommentCount(commentService.getCommentCountByPostId(post.getPostId()));

            List<Comment> comments = commentCustomRepository.getAllCommentsForPost(post.getPostId());  //not done
            List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();

            for( Comment comment: comments){
                CommentResponseDto commentResponseDto = new CommentResponseDto();
                UserDto userDto1 = new UserDto();
                userDto1 = userFeign.getUserById(comment.getUserId());
                commentResponseDto.setUserId(userDto1.getUserId());
                commentResponseDto.setDescription(comment.getCommentDescription());
                commentResponseDto.setUserName(userDto1.getUserName());
                commentResponseDto.setUserProfilePic(userDto1.getUserProfilePic());
                commentResponseDtoList.add(commentResponseDto);

            }

            userPostResponse.setCommentList(commentResponseDtoList);

            responses.add(userPostResponse);

        }



        return responses;
    }




}
