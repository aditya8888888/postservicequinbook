package com.example.quinbookpost.feignClient;


import com.example.quinbookpost.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "users", url = "http://10.20.2.122:8080", fallbackFactory = UserFeignFallBack.class)
//@FeignClient(value = "users", url = "http://10.20.3.178:8080", fallbackFactory = UserFeignFallBack.class)
public interface UserFeign {

   @RequestMapping(method = RequestMethod.GET, value = "/user/get-user-by-id")
    UserDto getUserById(@RequestParam("userId") String userId);

   @RequestMapping(method = RequestMethod.GET, value = "/user/find-all-friends")
    List<String> getAllFriends(@RequestParam("userId") String userId);
}
