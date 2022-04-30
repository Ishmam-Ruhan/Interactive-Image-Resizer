package com.ishmamruhan.imageservice.ExceptionManagement.Errors;

import com.ishmamruhan.imageservice.ExceptionManagement.CustomError;
import com.ishmamruhan.imageservice.ExceptionManagement.ErrorTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomErrorHandler {

    @ExceptionHandler(CustomError.class)
    public ResponseEntity<Object> myCustomErrorHandler(CustomError customError){

        ErrorTemplate errorTemplate = new ErrorTemplate();

        errorTemplate.setErrorCode(customError.getError_code());
        errorTemplate.setErrorType(customError.getError_type());
        errorTemplate.setDetails(customError.getError_description());

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorTemplate);
    }
}
