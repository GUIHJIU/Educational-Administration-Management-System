package com.example.studentmanagementsystemtest.util;

import java.io.Serializable;
import java.util.List;

public class Result< T > implements Serializable {
    private Integer code;    // 状态码
    private String message; // 提示信息
    private T data;         // 响应数据
    private Long timestamp; // 时间戳

    // 构造方法私有化
    private Result() {
        this.timestamp = System.currentTimeMillis();
    }

    // 核心静态工厂方法
    public static < T > Result< T > success(T data) {
        Result< T > result = new Result<>();
        result.code = ErrorCode.SUCCESS.getCode();
        result.message = ErrorCode.SUCCESS.getMessage();
        result.data = data;
        return result;
    }

    public static < T > Result< T > success() {
        return success(null);
    }

    public static < T > Result< T > error(Integer code, String message) {
        Result< T > result = new Result<>();
        result.code = code;
        result.message = message;
        return result;
    }

    public static < T > Result< T > error(ErrorCode errorCode) {
        return error(errorCode.getCode(), errorCode.getMessage());
    }

    public static < T > Result< T > error(ErrorCode errorCode, String fieldErrors) {
        return error(errorCode.getCode(), errorCode.getMessage() + ": " + fieldErrors);
    }

    // 链式调用支持
    public Result< T > code(Integer code) {
        this.code = code;
        return this;
    }

    public Result< T > message(String message) {
        this.message = message;
        return this;
    }

    public Result< T > data(T data) {
        this.data = data;
        return this;
    }

    // Getter 方法
    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    // 状态判断方法
    public boolean isSuccess() {
        return ErrorCode.SUCCESS.getCode().equals(this.code);
    }
}
