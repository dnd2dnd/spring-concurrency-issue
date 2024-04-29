package com.project.common;

import static org.springframework.http.HttpStatus.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handlingApplicationException(BusinessException exception) {
        return ResponseEntity.status(exception.getHttpStatus())
            .body(ErrorResponse.of(exception.getHttpStatus(), exception.getMessage()));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex,
        HttpHeaders headers,
        HttpStatusCode status,
        WebRequest request
    ) {
        Map<String, String> errors = createNotValidExceptionResponse(ex);
        return ResponseEntity.status(status).body(errors);
    }

    private Map<String, String> createNotValidExceptionResponse(MethodArgumentNotValidException ex) {
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        return allErrors.stream()
            .collect(Collectors.toMap(
                error -> ((FieldError) error).getField(),
                ObjectError::getDefaultMessage
            ));
    }

}
