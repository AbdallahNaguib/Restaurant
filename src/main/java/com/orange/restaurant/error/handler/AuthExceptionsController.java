package com.orange.restaurant.error.handler;

import com.orange.restaurant.error.AppError;
import com.orange.restaurant.error.ConfirmPasswordNotSameAsOriginalPassException;
import com.orange.restaurant.error.EmailAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AuthExceptionsController {
    // thrown when registering and the user sends a wrong confirm password
    @ExceptionHandler(ConfirmPasswordNotSameAsOriginalPassException.class)
    public ResponseEntity<?> handle(ConfirmPasswordNotSameAsOriginalPassException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new AppError(e.getMessage(),
                "two passwords not the same"));
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<?> handle(EmailAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new AppError(e.getMessage(),
                "email already used"));
    }
}
