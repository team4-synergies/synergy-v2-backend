package com.synergies.synergyv2.common.response.exception;

import com.synergies.synergyv2.common.response.CommonResponse;
import com.synergies.synergyv2.common.response.code.Code;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DefaultException.class)
    public ResponseEntity<Object> handleDefaultException(DefaultException e){
        Code code = e.getCode();
        return handleExceptionInternal(code);
    }

    private ResponseEntity<Object> handleExceptionInternal(Code code){
        CommonResponse errorResponse = CommonResponse.toErrorResponse(code);
        return ResponseEntity
                .status(code.getCode())
                .body(errorResponse);
    }

    private ResponseEntity<Object> handleExceptionInternal(Code code, String message){
        CommonResponse errorResponse = CommonResponse.toErrorResponse(code, message);
        return ResponseEntity
                .status(code.getCode())
                .body(errorResponse);
    }
}