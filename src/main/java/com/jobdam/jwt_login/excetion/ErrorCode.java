package com.jobdam.jwt_login.excetion;

import lombok.Getter;

@Getter
public enum ErrorCode {
    DUPLICATE_USERID(409,"U001","ID가 중복입니다."),
    UNAUTHORIZED(401, "A001", "인증되지 않은 사용자입니다."),
    INVALID_SIGNATURE(401, "A003", "JWT 서명이 유효하지 않습니다."),
    EXPIRED_TOKEN(401, "A004", "JWT 토큰이 만료되었습니다."),
    MALFORMED_TOKEN(400, "A005", "잘못된 JWT 형식입니다."),
    INVALID_TOKEN(401, "A006", "유효하지 않은 JWT 토큰입니다."),
    ACCESS_DENIED(403, "A003", "접근 권한이 없습니다."),
    USER_NOT_FOUND(404, "U002", "존재하지 않는 사용자입니다."),
    ENCODING_ERROR(6001,"S001","암호화 중 오류가 났습니다"),
    DECODING_ERROR(6002,"S002","복호화 오류가 났습니다");
    private final int status;
    private final String errorCode;
    private final String errorMsg;


    ErrorCode(int status, String errorCode, String errorMsg) {
        this.status = status;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

}
