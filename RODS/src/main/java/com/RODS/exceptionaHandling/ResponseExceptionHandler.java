package com.RODS.exceptionaHandling;



import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {
            IllegalArgumentException.class,
            IllegalStateException.class,
            NoSuchElementException.class})
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        log.info("ResponseExceptionHandler in work");
        log.info(ex.getMessage());

        String bodyOfResponse = ex.getMessage();
        HttpHeaders header = new HttpHeaders();
        return handleExceptionInternal(ex, bodyOfResponse, header, HttpStatus.CONFLICT, request);
    }


    @Nonnull
    @Override
    protected ResponseEntity<Object> handleTypeMismatch(
            TypeMismatchException ex, @Nonnull HttpHeaders headers,
            @Nonnull HttpStatusCode status, @Nonnull WebRequest request) {
        ApiError apiError = new ApiError(
                HttpStatus.NOT_ACCEPTABLE, ex.getLocalizedMessage(), "Invalid input.");
        log.info("handleTypeMismatch in work");
        log.warn(ex.getLocalizedMessage());
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }

    @Override
    @Nonnull
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, @Nonnull HttpHeaders headers,
            @Nonnull HttpStatusCode status, @Nonnull WebRequest request) {
        ApiError apiError = new ApiError(
                HttpStatus.NOT_ACCEPTABLE, ex.getLocalizedMessage(), "Invalid input.");
        log.info("handleHttpMessageNotReadable in work");
        log.warn(ex.getLocalizedMessage());
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }

    @Override
    @Nonnull
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, @Nonnull HttpHeaders headers,
            @Nonnull HttpStatusCode status, @Nonnull WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        log.info("handleMethodArgumentNotValid on work");
        log.warn(errors.toString());

        ApiError apiError =
                new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        return handleExceptionInternal(
                ex, apiError, headers, apiError.getStatus(), request);
    }

    @Override
    @Nonnull
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex, @Nonnull HttpHeaders headers,
            @Nonnull HttpStatusCode status, @Nonnull WebRequest request) {

        String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();
        log.info(error);

        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), error);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

}

