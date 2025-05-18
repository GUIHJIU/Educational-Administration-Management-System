package com.example.studentmanagementsystemtest.Exception;

import com.example.studentmanagementsystemtest.util.ErrorCode;

// 分布式锁异常
public class LockException extends BaseException {
    public LockException(ErrorCode errorCode) {
        super(errorCode);
    }
}
