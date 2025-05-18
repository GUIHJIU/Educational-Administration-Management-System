package com.example.studentmanagementsystemtest.Exception;

import com.example.studentmanagementsystemtest.util.ErrorCode;

// 认证授权异常（401/403）
public class AuthException extends BaseException {
    public AuthException(ErrorCode errorCode) {
        super(errorCode);
    }
}
