/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.munan.fitnesstrackerAPI.exceptions;

import static com.munan.fitnesstrackerAPI.constant.ErrorConstant.ACCOUNT_DISABLED;
import static com.munan.fitnesstrackerAPI.constant.ErrorConstant.ACCOUNT_LOCKED;
import static com.munan.fitnesstrackerAPI.constant.ErrorConstant.INCORRECT_CREDENTIALS;
import static com.munan.fitnesstrackerAPI.constant.ErrorConstant.INTERNAL_SERVER_ERROR_MSG;
import static com.munan.fitnesstrackerAPI.constant.ErrorConstant.METHOD_IS_NOT_ALLOWED;
import static com.munan.fitnesstrackerAPI.constant.ErrorConstant.NOT_ENOUGH_PERMISSION;
import static com.munan.fitnesstrackerAPI.constant.ErrorConstant.REQUEST_TYPE_MISMATCH;
import com.munan.fitnesstrackerAPI.utils.HttpResponse;
import jakarta.persistence.NoResultException;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 *
 * @author godwi
 */
@ControllerAdvice
public class GlobalExceptionHandling implements ErrorController{
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    
    
    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<HttpResponse> accountDisabledException(){
        return createHttpResponse(BAD_REQUEST, ACCOUNT_DISABLED);
    }
    
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<HttpResponse> badCredentialsException() {
        return createHttpResponse(BAD_REQUEST, INCORRECT_CREDENTIALS);
    }
    
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<HttpResponse> messageNotReadableException() {
        return createHttpResponse(NOT_ACCEPTABLE, REQUEST_TYPE_MISMATCH);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<HttpResponse> accessDeniedException() {
        return createHttpResponse(FORBIDDEN, NOT_ENOUGH_PERMISSION);
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<HttpResponse> lockedException() {
        return createHttpResponse(UNAUTHORIZED, ACCOUNT_LOCKED);
    }


    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<HttpResponse> emailExistException(AlreadyExistException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<HttpResponse> emailExistException(NotFoundException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }


    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<HttpResponse> methodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        HttpMethod supportedMethod = Objects.requireNonNull(exception.getSupportedHttpMethods()).iterator().next();
        return createHttpResponse(METHOD_NOT_ALLOWED, String.format(METHOD_IS_NOT_ALLOWED, supportedMethod));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpResponse> internalServerErrorException(Exception exception) {
        LOGGER.info(exception.getMessage());
        return createHttpResponse(INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR_MSG);
    }

    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<HttpResponse> notFoundException(NoResultException exception) {
        LOGGER.error(exception.getMessage());
        return createHttpResponse(NOT_FOUND, exception.getMessage());
    }
    
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<HttpResponse> noHandlerFoundException(NoHandlerFoundException e) {
        return createHttpResponse(BAD_REQUEST, "There is no mapping for this URL");
    }
    
    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String message){
        return new ResponseEntity<>(
                new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(), message), 
                        httpStatus
        );
    }
}
