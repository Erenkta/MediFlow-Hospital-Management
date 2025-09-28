package com.hospital.mediflow.Common.Exceptions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.Builder;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.List;

@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse (
        String message,
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        List<FieldError> fieldErrors,
        LocalDateTime occurredAt,
        ErrorCode errorCode,
        StackTraceElement[] trace
)
{
    public String statusCode(){
        return errorCode().getStatusCode();
    }
}
