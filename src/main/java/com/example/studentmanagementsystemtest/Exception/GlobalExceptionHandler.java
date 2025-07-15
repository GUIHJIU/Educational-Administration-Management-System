package com.example.studentmanagementsystemtest.Exception;

import com.example.studentmanagementsystemtest.util.ErrorCode;
import com.example.studentmanagementsystemtest.util.ResponseResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// 新建全局异常处理器
@RestControllerAdvice
public class GlobalExceptionHandler {
    // 统一业务异常处理
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity< ResponseResult< ? > > handleBusinessException(BusinessException ex) {
        return ResponseEntity.status(ex.getErrorCode().getCode())
                .body(ResponseResult.error(ex.getErrorCode()));
    }

    // 参数验证异常处理
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity< ResponseResult< ? > > handleValidationExceptions(MethodArgumentNotValidException ex) {
        String fieldErrors = ex.getBindingResult().getFieldErrors().toString();
        return ResponseEntity.status(400)
                .body(ResponseResult.error(ErrorCode.BAD_REQUEST, fieldErrors));
    }

    // 未知异常兜底处理
    @ExceptionHandler(Exception.class)
    public ResponseEntity< ResponseResult< ? > > handleUnexpectedException(Exception ex) {
        return ResponseEntity.status(500)
                .body(ResponseResult.error(ErrorCode.INTERNAL_ERROR));
    }
}

