package com.example.user.service.exceptions;

import com.example.user.service.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class globalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handlerResourceNotFoundException(ResourceNotFoundException ex){
        String message = ex.getMessage();
        ApiResponse response = new ApiResponse();
        response.setMessage(message);
        response.setSuccess(true);
        response.setStatus(HttpStatus.NOT_FOUND);

        return new ResponseEntity<ApiResponse>(response,HttpStatus.NOT_FOUND);
    }

}
