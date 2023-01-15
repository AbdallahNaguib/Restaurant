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
//todo use requests mapping to set the common root for the requests or leave it empty
@RequestMapping("/api")
public class ReservationController {
    private final ReservationService reservationService;

    //ToDo what protects these service from unauthorised access? add @PreAuthorize
    //our urls should always show the resource name "reservations" not the action "reserve"
    @PostMapping("/reserve")
    public ResponseEntity<Reservation> reserve(@RequestBody ReservationRequest request) {
        return ResponseEntity.ok(reservationService.reserve(request));
    }
    
    //ToDo this should be a get not post, filter criteria should be in query param
    //why are you adding prefix for /admin
    @PostMapping("/admin/reservations")
    public ResponseEntity<List<ReservationResponseDTO>> getReservations(@RequestBody TimeRange timeRange){
        List<ReservationResponseDTO> reservations = reservationService.findReservationsInSpecificDate(timeRange);
        return ResponseEntity.ok(reservations);
    }
}
