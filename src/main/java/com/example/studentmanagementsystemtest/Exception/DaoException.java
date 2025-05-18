package com.example.studentmanagementsystemtest.Exception;

import com.example.studentmanagementsystemtest.util.ErrorCode;

// 数据库操作异常
public class DaoException extends BaseException {
    public DaoException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
