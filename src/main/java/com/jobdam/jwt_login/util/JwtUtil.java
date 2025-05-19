package com.jobdam.jwt_login.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {
    //properties token 시크릿 키 가져옴
    @Value("${token.secret.key}")
    private String secretKey;
    // 서명키 생성
    public SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }
    //token 생성
    public String createToken(String email, String role){
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(email) // 고유 식별할 수있는내용
                .claim("role", role) // 권한 부여
                .setIssuedAt(new Date()) // 발금 시간
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))// 발금시간 30분
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}
