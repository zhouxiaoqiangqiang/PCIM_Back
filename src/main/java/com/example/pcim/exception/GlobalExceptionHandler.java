package com.example.pcim.exception;

import com.example.pcim.dto.Response;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Response<?> handleException(Exception e) {
        e.printStackTrace();
        return Response.error(e.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public Response<?> handleBusinessException(BusinessException e) {
        return Response.error(e.getCode(), e.getMessage());
    }
}
