package com.jobdam.jwt_login.excetion;

import lombok.Getter;

@Getter
public enum ErrorCode {
    DUPLICATE_USERID(409,"U001","ID가 중복입니다.");

    private final int status;
    private final String errorCode;
    private final String errorMsg;


    ErrorCode(int status, String errorCode, String errorMsg) {
        this.status = status;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

}
