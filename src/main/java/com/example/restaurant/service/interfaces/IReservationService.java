package com.example.restaurant.service.interfaces;


import com.example.restaurant.model.Reservation;
import com.example.restaurant.model.dto.ReservationRequest;
import com.example.restaurant.model.dto.TimeRange;

import java.util.List;

public interface IReservationService {
    Reservation reserve(ReservationRequest request);
    List<Reservation> findReservationsInSpecificDate(TimeRange timeRange);
}
