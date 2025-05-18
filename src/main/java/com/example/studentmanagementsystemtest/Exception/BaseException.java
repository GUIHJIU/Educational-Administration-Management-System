package com.example.studentmanagementsystemtest.Exception;

import com.example.studentmanagementsystemtest.util.ErrorCode;
import org.springframework.validation.FieldError;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 异常基类（所有自定义异常的父类）
 */
public class BaseException extends RuntimeException {
    private final ErrorCode errorCode;
    private final Map< String, Object > context = new HashMap<>();

    public BaseException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public BaseException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public BaseException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public Map< String, Object > getContext() {
        return Collections.unmodifiableMap(context);
    }

    public BaseException addContext(String key, Object value) {
        context.put(key, value);
        return this;
    }
}

