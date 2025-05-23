package com.example.studentmanagementsystemtest.util;

public enum ErrorCode {
    SUCCESS(200, "操作成功"),
    BAD_REQUEST(400, "请求参数错误"),
    UNAUTHORIZED(401, "未授权访问"),
    FORBIDDEN(403, "禁止访问"),
    NOT_FOUND(404, "资源不存在"),
    INTERNAL_ERROR(500, "系统内部错误"),
    SERVICE_BUSY(503, "服务繁忙"),

    // 业务自定义错误码（示例）
    COURSE_NOT_EXIST(1001, "课程不存在"),
    STOCK_NOT_ENOUGH(1002, "库存不足"),
    TIME_CONFLICT(1003, "时间冲突"),
    UPDATE_COURSE_ERROR(1004, "课程更新失败"),
    OPERATION_FAILED(1005, "选课失败"),
    DUPLICATE_SELECTION(1006, "重复选课");


    private final Integer code;
    private final String message;

    ErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
