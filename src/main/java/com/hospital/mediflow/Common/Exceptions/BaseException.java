package com.hospital.mediflow.Common.Exceptions;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@Getter
public class BaseException extends RuntimeException{
    private final ErrorCode errorCode;
    private final LocalDateTime timestamp;

    public BaseException(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = Objects.requireNonNull(errorCode);
        this.timestamp = LocalDateTime.now();
        log.error("An exception occured. Details : {}",this.toString());
    }
    public BaseException(String message, ErrorCode errorCode ,Throwable cause) {
        super(message, cause);
        this.errorCode = Objects.requireNonNull(errorCode,"Error code cannot be null.");
        this.timestamp = LocalDateTime.now();
    }

    @Override
    public String toString(){
        return String.format("Exception Type= %s | statusCode= %s | errorCode= %s | message= %s | occurredAt= %s",
                this.getClass().getSimpleName(),
                this.errorCode.getStatusCode(),
                this.errorCode,
                getMessage(),
                this.timestamp
        );
    }
}
