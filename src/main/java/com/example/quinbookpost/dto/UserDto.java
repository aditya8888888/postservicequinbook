package com.example.quinbookpost.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String userId;
    private String userEmail;
    private String userName;
    private String userBio;
    private String userProfilePic;
    private boolean userIsPrivate;
    private String userAccountType;
}
