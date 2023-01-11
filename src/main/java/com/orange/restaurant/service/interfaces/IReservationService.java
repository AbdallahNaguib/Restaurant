package com.orange.restaurant.service.interfaces;

import com.orange.restaurant.model.Reservation;
import com.orange.restaurant.model.dto.ReservationRequest;
import com.orange.restaurant.model.dto.ReservationResponseDTO;
import com.orange.restaurant.model.dto.TimeRange;

import java.util.List;

public interface IReservationService {
    Reservation reserve(ReservationRequest request);
    List<ReservationResponseDTO> findReservationsInSpecificDate(TimeRange timeRange);
}
