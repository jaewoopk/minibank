package com.minibank.exception;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 전역 예외 처리기
 */
@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 공통 에러 응답 DTO
    @Data
    @AllArgsConstructor
    public static class ErrorResponse {
        private String code;
        private String message;
    }

    /**
     * 이미 존재하는 계좌
     */
    @ExceptionHandler(AccountAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateAccountException(AccountAlreadyExistsException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("ACCOUNT_ALREADY_EXISTS", ex.getMessage()));
    }

    /**
     * 잔액 부족
     */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleIllegalState(IllegalStateException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT) // 409
                .body(new ErrorResponse("INSUFFICIENT_BALANCE", ex.getMessage()));
    }

    /**
     * 잘못된 파라미터
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST) // 400
                .body(new ErrorResponse("INVALID_ARGUMENT", ex.getMessage()));
    }

    /**
     * 그 외 모든 예외 - 내부 서버 오류
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("INTERNAL_ERROR", ex.getMessage()));
    }
}
