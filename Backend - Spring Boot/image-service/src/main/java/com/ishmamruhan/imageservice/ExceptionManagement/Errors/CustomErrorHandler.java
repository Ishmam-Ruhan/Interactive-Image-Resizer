package com.ishmamruhan.imageservice.ExceptionManagement.Errors;

import com.ishmamruhan.imageservice.ExceptionManagement.CustomError;
import com.ishmamruhan.imageservice.ExceptionManagement.ErrorTemplate;
import com.ishmamruhan.imageservice.Services.NotificationService;
import org.apache.tomcat.util.http.fileupload.impl.FileUploadIOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.io.FileNotFoundException;

@ControllerAdvice
public class CustomErrorHandler {

    @Autowired
    private NotificationService notificationService;

    @ExceptionHandler(CustomError.class)
    public ResponseEntity<Object> myCustomErrorHandler(CustomError customError){

        ErrorTemplate errorTemplate = new ErrorTemplate();

        errorTemplate.setErrorCode(customError.getError_code());
        errorTemplate.setErrorType(customError.getError_type());
        errorTemplate.setDetails(customError.getError_description());

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorTemplate);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorTemplate> restExceptionHandler(Exception ex){
        return new ResponseEntity<>(new ErrorTemplate(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",ex.getMessage() ),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Object> multipartSizeExceddedErrorHandler(MaxUploadSizeExceededException ex){

        notificationService.sendNotification("Maximum file size excedded!");

        return new ResponseEntity<>(new ErrorTemplate(HttpStatus.EXPECTATION_FAILED.value(),
                ex.getMessage(), "Excedded Max Upload Size"),HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler(FileUploadIOException.class)
    public ResponseEntity<Object> inputOutputExceptionHandler(FileUploadIOException ex){
        return new ResponseEntity<>(new ErrorTemplate(HttpStatus.SERVICE_UNAVAILABLE.value(),
                ex.getMessage(), "I/O Exception Occurs"),HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<Object> fileNotFoundExceptionHandler2(FileNotFoundException ex){

        notificationService.sendNotification("File cannot processable! Try Again.");

        return new ResponseEntity<>(new ErrorTemplate(HttpStatus.SERVICE_UNAVAILABLE.value(),
                ex.getMessage(), "File Not Found"),HttpStatus.SERVICE_UNAVAILABLE);
    }


}
