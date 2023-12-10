package com.example.quinbookpost.service.impl;

import com.example.quinbookpost.dto.CommentDto;
import com.example.quinbookpost.dto.CommentResponseDto;
import com.example.quinbookpost.entity.Comment;
import com.example.quinbookpost.entity.Like;
import com.example.quinbookpost.repository.CommentCustomRepository;
import com.example.quinbookpost.repository.CommentRepository;
import com.example.quinbookpost.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentCustomRepository commentCustomRepository;

//    public boolean removeCommentPost(CommentDto commentDto){
//        return true;
//    }

    @Override
    public boolean addCommentPost (@RequestBody CommentDto commentDto){
        Comment comment=new Comment();
        comment.setCommentDate(commentDto.getCommentDate());
        comment.setCommentDescription(commentDto.getCommentDescription());
        comment.setCommentId(UUID.randomUUID().toString());
        comment.setPostId(commentDto.getPostId());
        comment.setUserId(commentDto.getUserId());
        Comment savedComment = commentRepository.save(comment);
        return Objects.nonNull(savedComment);
    }

    @Override
    public void removeCommentById(String userId, String postId) {
        // Find the likeId by userId and postId
        Optional<String> commentIdOptional = commentRepository.findCommentIdByUserIdAndPostId(userId, postId);

        if (commentIdOptional.isPresent()) {
            String commentId = commentIdOptional.get();
            commentRepository.deleteById(commentId);
            System.out.println("Comment with id " + commentId + " deleted successfully.");
        } else {
            System.out.println("No comment found for userId: " + userId + " and postId: " + postId);
        }
    }
    @Override
    public long getCommentCountByPostId(String postId) {
        return commentRepository.countByPostId(postId);
    }

    @Override
    public List<Comment> getCommentByPostId(String postId) {
        return commentCustomRepository.getAllCommentsForPost(postId);
    }

}
