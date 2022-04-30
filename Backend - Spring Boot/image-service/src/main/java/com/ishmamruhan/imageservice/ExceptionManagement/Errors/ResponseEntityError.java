package com.ishmamruhan.imageservice.ExceptionManagement.Errors;

import com.ishmamruhan.UserService.ExceptionManagement.ErrorTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ResponseEntityError extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorTemplate errorTemplate = new ErrorTemplate();

        Map<String,String> errors =new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error -> {

            String field = "Error Case  # "+((FieldError) error).getField();
            String message = error.getDefaultMessage();

            errors.put(field,message);

        }));

        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }



}
