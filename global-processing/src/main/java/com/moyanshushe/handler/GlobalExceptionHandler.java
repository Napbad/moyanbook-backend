package com.moyanshushe.handler;

/*
 * Author: Napbad
 * Version: 1.0
 */

import com.moyanshushe.exception.BaseException;
import com.moyanshushe.exception.UnexpectedException;
import com.moyanshushe.model.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;

@ControllerAdvice
public class GlobalExceptionHandler {
    // 处理所有未捕获的异常
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Result> handleAllExceptions(Exception ex) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Result.error(ex.getMessage()));
//    }

    @ExceptionHandler(value = UnexpectedException.class)
    public ResponseEntity<Result> handleUnexpectedException(UnexpectedException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.error(ex.getMessage()));
    }

    @ExceptionHandler(value = BaseException.class)
    public ResponseEntity<Result> handleBaseException(BaseException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Result.error(ex.getMessage()));
    }

    @ExceptionHandler(value = SQLException.class)
    public ResponseEntity<Result> handleSQLException(Exception ex) {
        ex.printStackTrace();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Result.error("数据库异常"));
    }

}
