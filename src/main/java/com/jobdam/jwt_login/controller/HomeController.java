package com.jobdam.jwt_login.controller;

import com.jobdam.jwt_login.dto.Message;
import com.jobdam.jwt_login.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HomeController {
    @GetMapping("/")
    //메인 화면 controller
    public HttpEntity<Message> index(){
        Message message = new Message();
        message.setMessage("Hello World");
        HttpHeaders headers = new HttpHeaders();
        //  인덱스에 정보 없음 대충 넣어줌
        message.setData("Hello World");
        return new HttpEntity<>(message, headers);
    }
}
