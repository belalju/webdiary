package com.bd.webdiary.advice;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
public class ExceptionsAdvice extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders httpHeaders,
                                                                  HttpStatus httpStatus, WebRequest webRequest){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", httpStatus.value());

        //Get all errors
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getField() + " -> " + x.getDefaultMessage())
                .collect(Collectors.toList());

        body.put("message", errors);
        return new ResponseEntity<>(body, httpHeaders, httpStatus);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleException(ConstraintViolationException ex){
        return error(BAD_REQUEST, ex);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleException(Exception ex){
        return error(INTERNAL_SERVER_ERROR, ex);
    }

    @ExceptionHandler({SQLException.class})
    public ResponseEntity<Object> handleSqlException(SQLException ex){
        return error(INTERNAL_SERVER_ERROR, ex);
    }

    private ResponseEntity<Object> error(HttpStatus status, Exception ex){

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());

        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, status);
    }
}
