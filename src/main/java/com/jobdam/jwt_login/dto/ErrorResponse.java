package com.jobdam.jwt_login.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ErrorResponse {
    private int status;
    private String message;
    private String errorCode;
}
