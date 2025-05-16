package com.jobdam.jwt_login.controller;

import com.jobdam.jwt_login.dto.Message;
import com.jobdam.jwt_login.dto.UserDTO;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @PostMapping("/api/signup")
    public HttpEntity<Message> signup(@RequestBody UserDTO userDTO) {
        Message message = new Message();
        message.setMessage("Sign up successful");

        return new HttpEntity<>(message);
    }
}
