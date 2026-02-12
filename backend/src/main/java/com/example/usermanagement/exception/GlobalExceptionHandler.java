package com.example.usermanagement.exception;
import com.example.usermanagement.dtos.responseDto.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 🔹 400 - @Valid validation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errors);
    }

    // 🔹 400 - Bind error
    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> handleBind(BindException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errors);
    }

    // 🔹 400 - Constraint violation
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(
            ConstraintViolationException ex) {

        return new ResponseEntity<>(
                new ErrorResponse(400, "Bad Request", ex.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    // 🔹 400 - Invalid JSON
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleInvalidJson() {

        return new ResponseEntity<>(
                new ErrorResponse(400, "Bad Request", "Invalid JSON request body"),
                HttpStatus.BAD_REQUEST
        );
    }

    // 🔹 400 - Invalid path variable
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch() {

        return new ResponseEntity<>(
                new ErrorResponse(400, "Bad Request", "Invalid parameter type"),
                HttpStatus.BAD_REQUEST
        );
    }

    // 🔹 401 - JWT expired
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponse> handleExpiredJwt() {

        return new ResponseEntity<>(
                new ErrorResponse(401, "Unauthorized", "JWT token expired. Please login again."),
                HttpStatus.UNAUTHORIZED
        );
    }

    // 🔹 401 - Invalid JWT
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorResponse> handleJwtException() {

        return new ResponseEntity<>(
                new ErrorResponse(401, "Unauthorized", "Invalid JWT token"),
                HttpStatus.UNAUTHORIZED
        );
    }

    // 🔹 401 - Username not found
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(
            UsernameNotFoundException ex) {

        return new ResponseEntity<>(
                new ErrorResponse(401, "Unauthorized", "User not found. Please login again."),
                HttpStatus.UNAUTHORIZED
        );
    }

    // 🔹 403 - Access denied
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied() {

        return new ResponseEntity<>(
                new ErrorResponse(403, "Forbidden", "Access denied. You do not have permission."),
                HttpStatus.FORBIDDEN
        );
    }

    // 🔹 404 - Resource not found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(
            ResourceNotFoundException ex) {

        return new ResponseEntity<>(
                new ErrorResponse(404, "Not Found", ex.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    // 🔹 404 - Wrong URL
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoHandler() {

        return new ResponseEntity<>(
                new ErrorResponse(404, "Not Found", "API endpoint not found"),
                HttpStatus.NOT_FOUND
        );
    }

    // 🔹 405 - Method not allowed
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotAllowed() {

        return new ResponseEntity<>(
                new ErrorResponse(405, "Method Not Allowed", "HTTP method not allowed"),
                HttpStatus.METHOD_NOT_ALLOWED
        );
    }

    // 🔹 409 - Duplicate / DB issue
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrity() {

        return new ResponseEntity<>(
                new ErrorResponse(409, "Conflict", "Duplicate or invalid data"),
                HttpStatus.CONFLICT
        );
    }

    // 🔹 500 - Fallback
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {

        return new ResponseEntity<>(
                new ErrorResponse(500, "Internal Server Error",
                        "Something went wrong. Please try again."),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
