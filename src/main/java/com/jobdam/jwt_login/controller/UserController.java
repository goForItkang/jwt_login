package com.jobdam.jwt_login.controller;

import com.jobdam.jwt_login.dto.Message;
import com.jobdam.jwt_login.dto.UserDTO;
import com.jobdam.jwt_login.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    //일반 유저 로그인
    @PostMapping("/api/signup")
    public HttpEntity<Message> signup(@RequestBody UserDTO userDTO) {
        Message message = new Message();
        message.setStatus(200);
        message.setMessage("Sign up successful");
        userService.userSave(userDTO);
        return new HttpEntity<>(message);
    }
    @PostMapping("/api/login")
    public HttpEntity<Message>login(@RequestBody UserDTO userDTO){
        //로그인한 유저 정보를 유저
        UserDTO loginSuccessUser = userService.login(userDTO);
        String token = userService.tokenGenerate(loginSuccessUser);
        Message message = new Message();
        message.setStatus(200);
        message.setMessage("Login successful");
        String accessToken = "token";
        System.out.println("정보"+loginSuccessUser);
        message.setData(accessToken);
        return new HttpEntity<>(message);
    }


}
