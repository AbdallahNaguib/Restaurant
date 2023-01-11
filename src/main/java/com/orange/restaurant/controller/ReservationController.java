package com.orange.restaurant.controller;

import com.orange.restaurant.model.Reservation;
import com.orange.restaurant.model.dto.ReservationRequest;
import com.orange.restaurant.model.dto.ReservationResponseDTO;
import com.orange.restaurant.model.dto.TimeRange;
import com.orange.restaurant.service.ReservationService;
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
    public ResponseEntity<List<ReservationResponseDTO>> getReservations(@RequestBody TimeRange timeRange){
        List<ReservationResponseDTO> reservations = reservationService.findReservationsInSpecificDate(timeRange);
        return ResponseEntity.ok(reservations);
    }
}
