package com.orange.restaurant.error.handler;

import com.orange.restaurant.error.AppError;
import com.orange.restaurant.error.NoTableAvailableException;
import com.orange.restaurant.error.ReservationStartTimeAfterEndTimeException;
import com.orange.restaurant.error.ReservationStartTimeAlreadyPassed;
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
    @ExceptionHandler(ReservationStartTimeAlreadyPassed.class)
    public ResponseEntity<?> handle(ReservationStartTimeAlreadyPassed e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new AppError(e.getMessage(),
                "No table available, please choose another time"));
    }
}
