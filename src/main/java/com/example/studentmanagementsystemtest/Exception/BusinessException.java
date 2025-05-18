package com.example.studentmanagementsystemtest.Exception;

import com.example.studentmanagementsystemtest.util.ErrorCode;

// 通用业务异常（400-499 错误码范围）
public class BusinessException extends BaseException {
    public BusinessException(ErrorCode errorCode) {
        super(errorCode);
    }

    public BusinessException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
