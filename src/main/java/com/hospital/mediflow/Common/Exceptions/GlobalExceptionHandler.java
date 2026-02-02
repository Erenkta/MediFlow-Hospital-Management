package com.hospital.mediflow.Common.Exceptions;

import jakarta.security.auth.message.AuthException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.naming.AuthenticationException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.jar.Attributes;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private final DefaultErrorAttributes errorAttributes;

    public GlobalExceptionHandler(DefaultErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @ExceptionHandler({BaseException.class})
    public ResponseEntity<ErrorResponse> handleBaseException(WebRequest request,BaseException exception){
        Map<String, Object> errorMap = getErrorAttributes(request);
        errorMap.put("path",((ServletWebRequest) request).getRequest().getRequestURI());

        ErrorResponse response = ErrorResponse.builder()
                .message(exception.getMessage())
                .path(errorMap.getOrDefault("path","").toString())
                .trace(errorMap.getOrDefault("trace","").toString())
                .errorCode(exception.getErrorCode())
                .occurredAt(exception.getTimestamp())
                .build();

        return new ResponseEntity<>(response,HttpStatus.valueOf(response.statusCode()));
    }
    @ExceptionHandler({MediflowAuthException.class})
    public ResponseEntity<ErrorResponse> handleAuthException(MediflowAuthException exception){
        ErrorResponse response = ErrorResponse.builder()
                .message(exception.getMessage())
                .errorCode(exception.getErrorCode())
                .occurredAt(exception.getTimestamp())
                .build();

        return new ResponseEntity<>(response,HttpStatus.valueOf(response.statusCode()));
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(WebRequest request,ConstraintViolationException exception) {
        Map<String, Object> errorMap = getErrorAttributes(request);
        errorMap.put("path",((ServletWebRequest) request).getRequest().getRequestURI());
        ErrorResponse response = ErrorResponse.builder()
                .message("Constraint validation has failed")
                .path(errorMap.getOrDefault("path","").toString())
                .trace(errorMap.getOrDefault("trace","").toString())
                .occurredAt(LocalDateTime.now())
                .errorCode(ErrorCode.VALIDATION_ERROR)
                .build();

        return ResponseEntity.badRequest().body(response);
    }
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(WebRequest request,MethodArgumentNotValidException exception){
        Map<String, Object> errorMap = getErrorAttributes(request);
        errorMap.put("path",((ServletWebRequest) request).getRequest().getRequestURI());

        ErrorResponse response = ErrorResponse.builder()
                .message("Argument validation has failed.")
                .path(errorMap.getOrDefault("path","").toString())
                .trace(errorMap.getOrDefault("trace","").toString())
                .fieldErrorList(simplifyFieldErrors(exception.getFieldErrors()))
                .occurredAt(LocalDateTime.now())
                .errorCode(ErrorCode.METHOD_ARGUMENT_NOT_VALID)
                .build();

        return  ResponseEntity.status(HttpStatus.valueOf(response.statusCode())).body(response);
    }
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(WebRequest request,MethodArgumentTypeMismatchException exception){
        Map<String, Object> errorMap = getErrorAttributes(request);
        errorMap.put("path",((ServletWebRequest) request).getRequest().getRequestURI());
        ErrorResponse response = ErrorResponse.builder()
                .message(String.format("Argument validation has failed. Please check the parameter value : %s",exception.getValue()))
                .path(errorMap.getOrDefault("path","").toString())
                .trace(errorMap.getOrDefault("trace","").toString())
                .occurredAt(LocalDateTime.now())
                .errorCode(ErrorCode.METHOD_ARGUMENT_NOT_VALID)
                .build();

        return  ResponseEntity.status(HttpStatus.valueOf(response.statusCode())).body(response);
    }
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(WebRequest request,IllegalArgumentException exception){
        Map<String, Object> errorMap = getErrorAttributes(request);
        errorMap.put("path",((ServletWebRequest) request).getRequest().getRequestURI());

        ErrorResponse response = ErrorResponse.builder()
                .message("Argument validation has failed.")
                .path(errorMap.getOrDefault("path","").toString())
                .trace(errorMap.getOrDefault("trace","").toString())
                .occurredAt(LocalDateTime.now())
                .errorCode(ErrorCode.METHOD_ARGUMENT_NOT_VALID)
                .build();

        return  ResponseEntity.status(HttpStatus.valueOf(response.statusCode())).body(response);
    }
    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ErrorResponse> handleRuntimeException(WebRequest request,RuntimeException exception){
        Map<String, Object> errorMap = getErrorAttributes(request);
        errorMap.put("path",((ServletWebRequest) request).getRequest().getRequestURI());

        ErrorResponse response = ErrorResponse.builder()
                .message(exception.getMessage())
                .path(errorMap.getOrDefault("path","").toString())
                .trace(errorMap.getOrDefault("trace","").toString())
                .build();

        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }
    private Map<String,Object> getErrorAttributes(WebRequest request){
        Collection<ErrorAttributeOptions.Include> attributeList = new ArrayList<>();
        if(Boolean.TRUE.equals(request.getAttribute("trace",WebRequest.SCOPE_REQUEST))){
            attributeList.add(
                    ErrorAttributeOptions.Include.STACK_TRACE
            );
        }
        return errorAttributes.getErrorAttributes(request, ErrorAttributeOptions.of(attributeList));

    }
    private List<SimpleFieldError> simplifyFieldErrors(List<FieldError> fieldErrors){
        return fieldErrors.stream()
                .map(fe -> new SimpleFieldError(
                        fe.getField(),
                        String.valueOf(fe.getRejectedValue()),
                        fe.getDefaultMessage()
                ))
                .toList();
    }
}
