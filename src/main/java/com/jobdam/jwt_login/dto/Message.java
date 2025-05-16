package com.jobdam.jwt_login.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Message<T> {
    private int status;
    private String message;
    private T data;
}
