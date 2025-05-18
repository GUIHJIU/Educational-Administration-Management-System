package com.example.studentmanagementsystemtest.Exception;

import com.example.studentmanagementsystemtest.util.ErrorCode;
import org.springframework.validation.FieldError;

import java.util.List;

// 参数校验异常（自动捕获JSR303校验错误）
public class ValidationException extends BusinessException {
    private final List< FieldError > fieldErrors;

    public ValidationException(List< FieldError > fieldErrors) {
        super(ErrorCode.BAD_REQUEST, "参数校验失败");
        this.fieldErrors = fieldErrors;
    }

    public List< FieldError > getFieldErrors() {
        return fieldErrors;
    }
}
