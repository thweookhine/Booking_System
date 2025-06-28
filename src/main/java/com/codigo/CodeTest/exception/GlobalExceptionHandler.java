package com.codigo.CodeTest.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.codigo.CodeTest.dto.ErrorResponse;

import io.jsonwebtoken.ExpiredJwtException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler({
		ResourceNotFoundException.class,
	})
    public ResponseEntity<ErrorResponse> handleResourceNotFound(RuntimeException ex) {        
        ErrorResponse error = new ErrorResponse();
        error.setStatus(404);
        error.setMessageList(List.of(ex.getMessage()));
        error.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_FOUND);
    }
	
	@ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException ex) {
		ErrorResponse error = new ErrorResponse();
        error.setStatus(403);
        error.setMessageList(List.of(ex.getMessage()));
        error.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<ErrorResponse>(error, HttpStatus.FORBIDDEN);
    }
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> messages = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            messages.add(error.getDefaultMessage());
        });
        ErrorResponse error = new ErrorResponse();
        error.setStatus(400);
        error.setMessageList(messages);
        error.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<ErrorResponse>(error, HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<ErrorResponse> handleJwtExpired(ExpiredJwtException ex) {
		ErrorResponse error = new ErrorResponse();
        error.setStatus(401);
        error.setMessageList(List.of(ex.getMessage()));
        error.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<ErrorResponse>(error, HttpStatus.UNAUTHORIZED);
    }
	
	@ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorResponse> handleInvalidToken(InvalidTokenException ex) {
		ErrorResponse error = new ErrorResponse();
        error.setStatus(401);
        error.setMessageList(List.of(ex.getMessage()));
        error.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<ErrorResponse>(error, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex) {
		ErrorResponse error = new ErrorResponse();
        error.setStatus(400);
        error.setMessageList(List.of(ex.getMessage()));
        error.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<ErrorResponse>(error, HttpStatus.BAD_REQUEST);
	}
//	
//	@ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
//		ErrorResponse error = new ErrorResponse();
//        error.setStatus(500);
//        error.setMessage("Internal Server Error: "+ ex.getMessage());
//        error.setTimestamp(LocalDateTime.now());
//        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}
