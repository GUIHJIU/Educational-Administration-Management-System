package com.example.studentmanagementsystemtest.util;

import java.io.Serializable;

public class ResponseResult< T > implements Serializable {
    private Integer code;    // 状态码
    private String message; // 提示信息
    private T data;         // 响应数据
    private Long timestamp; // 时间戳

    // 构造方法私有化
    private ResponseResult() {
        this.timestamp = System.currentTimeMillis();
    }

    // 核心静态工厂方法
    public static < T > ResponseResult< T > success(T data) {
        ResponseResult< T > responseResult = new ResponseResult<>();
        responseResult.code = ErrorCode.SUCCESS.getCode();
        responseResult.message = ErrorCode.SUCCESS.getMessage();
        responseResult.data = data;
        return responseResult;
    }

    public static < T > ResponseResult< T > success() {
        return success(null);
    }

    public static < T > ResponseResult< T > error(Integer code, String message) {
        ResponseResult< T > responseResult = new ResponseResult<>();
        responseResult.code = code;
        responseResult.message = message;
        return responseResult;
    }

    public static < T > ResponseResult< T > error(ErrorCode errorCode) {
        return error(errorCode.getCode(), errorCode.getMessage());
    }

    public static < T > ResponseResult< T > error(ErrorCode errorCode, String fieldErrors) {
        return error(errorCode.getCode(), errorCode.getMessage() + ": " + fieldErrors);
    }

    public static < T > ResponseResult< T > error(ErrorCode errorCode, Class< T > type) {
        ResponseResult< T > result = new ResponseResult<>();
        result.code = errorCode.getCode();
        result.message = errorCode.getMessage();
        return result;
    }
    // 链式调用支持
    public ResponseResult< T > code(Integer code) {
        this.code = code;
        return this;
    }

    public ResponseResult< T > message(String message) {
        this.message = message;
        return this;
    }

    public ResponseResult< T > data(T data) {
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
