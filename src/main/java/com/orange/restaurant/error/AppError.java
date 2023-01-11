package com.orange.restaurant.error;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AppError {
    private String error;
    private String message;
}
