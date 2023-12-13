package com.example.quinbookpost.controller;

import com.example.quinbookpost.dto.FeedResponseDto;
import com.example.quinbookpost.dto.Response;
import com.example.quinbookpost.entity.Feed;
import com.example.quinbookpost.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feed")
@CrossOrigin
public class FeedController {

    @Autowired
    FeedService feedService;

    @GetMapping("/get-feed-by-userid")
    public List<FeedResponseDto> getFeedByUser( @RequestParam  String userId){
        return  feedService.getFeedByUser(userId);
    }


    @PostMapping("/add-feed")
    public Feed addFeed(@RequestParam String userId, @RequestParam String postId) {
        return feedService.addFeed(userId, postId);
    }

    @PostMapping("/update-feed")
    public Response updateFeed(@RequestParam String userId, @RequestParam String requestedId )
    {
        Response response = new Response();
        if(feedService.updateFeed(userId,requestedId)){
            response.setError("");
            response.setMessage("Updated");
        }else{
            response.setError("Not updated");
            response.setMessage("");
        }

        return response;
    }

    @PostMapping("/create-feed")
    public Response creadFeed(@RequestParam String userId){
        return feedService.createFeed(userId);
    }
}
