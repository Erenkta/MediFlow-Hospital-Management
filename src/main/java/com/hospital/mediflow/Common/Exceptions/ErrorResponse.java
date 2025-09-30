package com.hospital.mediflow.Common.Exceptions;


import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;



import java.time.LocalDateTime;
import java.util.List;

@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse (
        String message,
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        String path,
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        List<SimpleFieldError> fieldErrorList,
        LocalDateTime occurredAt,
        ErrorCode errorCode,
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        String trace
)
{
    public int statusCode(){
        return Integer.parseInt(errorCode.getStatusCode());
    }
}

record SimpleFieldError(
        String field,
        String rejectedValue,
        String message
) {}