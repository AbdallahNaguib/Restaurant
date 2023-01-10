package com.example.restaurant.controller;


import com.example.restaurant.model.Reservation;
import com.example.restaurant.model.dto.ReservationRequest;
import com.example.restaurant.model.dto.TimeRange;
import com.example.restaurant.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping("/reserve")
    public ResponseEntity<Reservation> reserve(@RequestBody ReservationRequest request) {
        return ResponseEntity.ok(reservationService.reserve(request));
    }
    @PostMapping("/admin/reservations")
    public ResponseEntity<List<Reservation>> getReservations(@RequestBody TimeRange timeRange){
        return ResponseEntity.ok(reservationService.findReservationsInSpecificDate(timeRange));
    }
}
