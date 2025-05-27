package com.jobdam.jwt_login.util;

import com.jobdam.jwt_login.dto.UserDTO;
import com.jobdam.jwt_login.excetion.CustomException;
import com.jobdam.jwt_login.excetion.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.management.relation.Role;
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
        String roleResult = role.substring(1, role.length()-1);
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(email) // 고유 식별할 수있는내용
                .claim("role", role) // 권한 부여
                .setIssuedAt(new Date()) // 발금 시간
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))// 발금시간 30분
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    public String getUserId(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJwt(token)
                .getBody()
                .getSubject(); // subject 식별이름
    }
    public boolean validateToken(String token){
            try {
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(getSecretKey())
                        .build()
                        .parseClaimsJws(token)
                        .getBody();
                return !claims.getExpiration().before(new Date());
            } catch (io.jsonwebtoken.ExpiredJwtException e) {
                throw new CustomException(ErrorCode.INVALID_TOKEN);
            } catch (io.jsonwebtoken.SignatureException e) {
                throw new CustomException(ErrorCode.INVALID_SIGNATURE);
            } catch (io.jsonwebtoken.MalformedJwtException e) {
                throw new CustomException(ErrorCode.MALFORMED_TOKEN);
            } catch (Exception e) {
                throw new CustomException(ErrorCode.INVALID_TOKEN);
            }
    }
    // 권한 가져오기
    public String getRole(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJwt(token)
                .getBody();
        return claims.get("role").toString();
    }
}
