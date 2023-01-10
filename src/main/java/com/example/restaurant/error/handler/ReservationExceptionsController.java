package com.example.restaurant.error.handler;

import com.example.restaurant.error.AppError;
import com.example.restaurant.error.NoTableAvailableException;
import com.example.restaurant.error.ReservationStartTimeAfterEndTimeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ReservationExceptionsController {
    @ExceptionHandler(ReservationStartTimeAfterEndTimeException.class)
    public ResponseEntity<?> handle(ReservationStartTimeAfterEndTimeException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new AppError(e.getMessage(),
                "reservation start time should be before end time"));
    }

    @ExceptionHandler(NoTableAvailableException.class)
    public ResponseEntity<?> handle(NoTableAvailableException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new AppError(e.getMessage(),
                "No table available, please choose another time"));
    }
}
