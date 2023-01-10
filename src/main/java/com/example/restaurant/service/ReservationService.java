package com.example.restaurant.service;

import com.example.restaurant.error.NoTableAvailableException;
import com.example.restaurant.error.ReservationStartTimeAfterEndTimeException;
import com.example.restaurant.model.DinnerTable;
import com.example.restaurant.model.Reservation;
import com.example.restaurant.model.dto.ReservationRequest;
import com.example.restaurant.model.dto.TimeRange;
import com.example.restaurant.repository.ReservationRepo;
import com.example.restaurant.repository.TableRepository;
import com.example.restaurant.service.interfaces.IReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService implements IReservationService {
    private final ReservationRepo reservationRepo;
    private final TableRepository tableRepository;
    private final TableService tableService;

    @Override
    public Reservation reserve(ReservationRequest request) {
        if (request.getTimeRange().getStartTime() >= request.getTimeRange().getEndTime()) {
            throw new ReservationStartTimeAfterEndTimeException();
        }
        List<DinnerTable> tables = tableRepository
                .findAllByMaxPersonsGreaterThanEqualOrderByMaxPersonsAsc(request.getNumberOfPersons());
        // tables are ordered asc based on the number of people so that if two tables are available the one with the least maxPer
        // is taken , if the customer wants a table for 3 persons and tables available with 4 and 5 persons the one with 4 is
        // reserved
        for (DinnerTable table : tables) {
            if (tableService.checkIfAvailable(table,request.getTimeRange())){
                return makeReservation(table,request);
            }
        }
        throw new NoTableAvailableException();
    }

    @Override
    public List<Reservation> findReservationsInSpecificDate(TimeRange timeRange) {
        return reservationRepo.findInSpecificDate(timeRange.getStartTime(),timeRange.getEndTime());
    }

    private Reservation makeReservation(DinnerTable table, ReservationRequest request) {
        Reservation reservation = Reservation.builder()
                .table(table)
                .startTime(request.getTimeRange().getStartTime())
                .endTime(request.getTimeRange().getEndTime())
                .build();
        return reservationRepo.save(reservation);
    }


}
