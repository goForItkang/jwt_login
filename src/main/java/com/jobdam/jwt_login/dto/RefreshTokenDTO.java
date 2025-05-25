package com.jobdam.jwt_login.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenDTO {
    private Long id;
    private String refreshToken;
    private LocalDateTime expiryDate;  // 만료 시간 설정

}
