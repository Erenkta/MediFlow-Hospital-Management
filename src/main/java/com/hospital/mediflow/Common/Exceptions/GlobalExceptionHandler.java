package com.hospital.mediflow.Common.Exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler({BaseException.class})
    public ResponseEntity<ErrorResponse> handleRecordAlreadyExistsException(BaseException exception){
        ErrorResponse response = ErrorResponse.builder()
                .message(exception.getMessage())
                .errorCode(exception.getErrorCode())
                .occurredAt(exception.getTimestamp())
                .trace(exception.getStackTrace())
                .build();

        return new ResponseEntity<>(response,HttpStatus.valueOf(response.statusCode()));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        ErrorResponse response = ErrorResponse.builder()
                .message(exception.getMessage())
                .fieldErrors(exception.getFieldErrors())
                .occurredAt(LocalDateTime.now())
                .errorCode(ErrorCode.METHOD_ARGUMENT_NOT_VALID)
                .trace(exception.getStackTrace())
                .build();
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.statusCode()));
    }


}
