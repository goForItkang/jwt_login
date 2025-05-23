package com.jobdam.jwt_login.controller;

import com.jobdam.jwt_login.dto.Message;
import com.jobdam.jwt_login.dto.UserDTO;
import com.jobdam.jwt_login.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
//    @PostMapping("/api/signup")
//    public HttpEntity<Message> signup(@RequestBody UserDTO userDTO) {
//        Message message = new Message();
//        message.setStatus(200);
//        message.setMessage("Sign up successful");
//        userService.userSave(userDTO);
//        return new HttpEntity<>(message);
//    }

}
