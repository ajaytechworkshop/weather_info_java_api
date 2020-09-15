package com.weatherapp.exception;

import com.weatherapp.model.Error;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.ValidationException;

@ControllerAdvice
@Slf4j
public class AppExceptionHandler
{
    @ResponseBody
    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Error> httpClientExceptionHandler(HttpClientErrorException ex) {
        return ResponseEntity.status(ex.getRawStatusCode())
                .body(Error.builder()
                        .status(ex.getStatusText())
                        .message(ex.getMessage())
                        .build());
    }

    @ResponseBody
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Error> validationExceptionHandler(ValidationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Error.builder()
                        .status(HttpStatus.BAD_REQUEST.toString())
                        .message(ex.getMessage())
                        .build());
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> exceptionHandler(Exception ex) {
        log.error("Problem while processing the requests",ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Error.builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                        .message("Problem while processing the requests")
                        .build());
    }

}
