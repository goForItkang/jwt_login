package com.jobdam.jwt_login.controller;

import com.jobdam.jwt_login.dto.Message;
import com.jobdam.jwt_login.excetion.CustomException;
import com.jobdam.jwt_login.excetion.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Message<?>> handleException(CustomException ex){
        ErrorCode errorCode = ex.getErrorCode();
        Message<?> message = new Message<>(errorCode.getStatus(), errorCode.getErrorMsg(), null);
        return ResponseEntity.status(errorCode.getStatus()).body(message);
    }
}
