package com.github.njsuarez.prices.infrastructure.adapter.rest.controller;

import com.github.njsuarez.prices.infrastructure.adapter.rest.api.model.ErrorDto;
import com.github.njsuarez.prices.domain.exceptions.BadParameterException;
import com.github.njsuarez.prices.domain.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestResponseEntityExceptionHandler  extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { NotFoundException.class })
    protected ResponseEntity<Object> handleNotFound(NotFoundException ex, WebRequest request) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler(value = {BadParameterException.class })
    protected ResponseEntity<Object> handleNotFound(BadParameterException ex, WebRequest request) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler(value = { Exception.class })
    protected ResponseEntity<Object> defaultHandler(Exception ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .findFirst()
                .orElse(ex.getMessage());
        return buildErrorResponse(HttpStatus.BAD_REQUEST, errorMessage, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }


    private ResponseEntity<Object> buildErrorResponse(HttpStatus httpStatus, String description, WebRequest request) {
        ErrorDto errorDto = new ErrorDto()
                .title(httpStatus.name())
                .status(httpStatus.value())
                .detail(description)
                .path(((ServletWebRequest)request).getRequest().getServletPath())
                .timestamp(LocalDateTime.now());
        return new ResponseEntity<>(errorDto, httpStatus);
    }

}
