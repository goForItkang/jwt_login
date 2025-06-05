package com.jobdam.jwt_login.controller;

import com.jobdam.jwt_login.dto.ErrorResponse;
import com.jobdam.jwt_login.dto.Message;
import com.jobdam.jwt_login.excetion.CustomException;
import com.jobdam.jwt_login.excetion.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleException(CustomException ex){
        ErrorCode errorCode = ex.getErrorCode();
        ErrorResponse errorResponse = new ErrorResponse(errorCode.getStatus(),errorCode.getErrorMsg(),errorCode.getErrorCode());
        return ResponseEntity.status(errorCode.getStatus()).body(errorResponse);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldError().getDefaultMessage();
        ErrorResponse errorResponse = new ErrorResponse(400, errorMessage, "VALIDATION_FAILED");

        return ResponseEntity.badRequest().body(errorResponse);
    }
}
