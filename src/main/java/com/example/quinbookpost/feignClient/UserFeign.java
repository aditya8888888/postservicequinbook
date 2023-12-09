package com.example.quinbookpost.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "token" ,url="http://localhost:8083",fallbackFactory = UserFeignFallBack.class)
public interface UserFeign {
//    @RequestMapping(method = RequestMethod.GET,value = "/user-service/user/token")
}
//@CrossOrigin
//@FeignClient(value = "token" ,url="http://localhost:8083",fallbackFactory = fallBack.class)
//public interface FeignImpl {
//    @RequestMapping(method = RequestMethod.GET,value = "/user-service/user/token")
//    boolean isTokenValid(@RequestHeader("authorization") String authorizationHeader);
//}