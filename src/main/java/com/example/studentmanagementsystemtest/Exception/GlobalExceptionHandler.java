package com.example.studentmanagementsystemtest.Exception;

import com.example.studentmanagementsystemtest.util.ErrorCode;
import com.example.studentmanagementsystemtest.util.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

// 新建全局异常处理器
@RestControllerAdvice
public class GlobalExceptionHandler {
    // 统一业务异常处理
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity< Result< ? > > handleBusinessException(BusinessException ex) {
        return ResponseEntity.status(ex.getErrorCode().getCode())
                .body(Result.error(ex.getErrorCode()));
    }

    // 参数验证异常处理
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity< Result< ? > > handleValidationExceptions(MethodArgumentNotValidException ex) {
        String fieldErrors = ex.getBindingResult().getFieldErrors().toString();
        return ResponseEntity.status(400)
                .body(Result.error(ErrorCode.BAD_REQUEST, fieldErrors));
    }

    // 未知异常兜底处理
    @ExceptionHandler(Exception.class)
    public ResponseEntity< Result< ? > > handleUnexpectedException(Exception ex) {
        return ResponseEntity.status(500)
                .body(Result.error(ErrorCode.INTERNAL_ERROR));
    }
}

