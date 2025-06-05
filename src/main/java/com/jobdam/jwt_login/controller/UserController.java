package com.jobdam.jwt_login.controller;

import com.jobdam.jwt_login.dto.Message;
import com.jobdam.jwt_login.dto.ReqSignupUser;
import com.jobdam.jwt_login.dto.UserDTO;
import com.jobdam.jwt_login.service.UserService;
import jakarta.validation.Valid;
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
        //login 한 user에 access Token 발급
        UserDTO loginSuccessUser = userService.login(userDTO);
        String token = userService.tokenGenerate(loginSuccessUser);

        // refresh Token 발급 및 확인
        userService.saveRefreshToken(loginSuccessUser.getUserId());
        Message message = new Message();
        message.setStatus(200);
        message.setMessage("Login successful");
        System.out.println("정보"+loginSuccessUser);
        message.setData(token); // 토큰 값 생성
        return new HttpEntity<>(message);
    }
    @PostMapping("/api/test/signup")
    public HttpEntity<Message>signupTest(@RequestBody @Valid ReqSignupUser reqSignupUser){
        Message message = new Message();
        message.setStatus(200);
        message.setMessage("Sign up successful");
        userService.passwordMach(reqSignupUser);
        return new HttpEntity<>(message);
    }


}
