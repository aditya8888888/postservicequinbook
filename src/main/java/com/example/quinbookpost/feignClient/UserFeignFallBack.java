package com.example.quinbookpost.feignClient;

import com.example.quinbookpost.dto.UserDto;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserFeignFallBack implements FallbackFactory<UserFeign> {
    @Override
    public UserFeign create(Throwable throwable) {
        return new UserFeign() {
            @Override
            public UserDto getUserById(String userId) {
                return null;
            }

            @Override
            public List<String> getAllFriends(String userId) {
                return null;
            }
        };
    }
}
