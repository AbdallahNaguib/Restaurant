package com.orange.restaurant.service;

import com.orange.restaurant.error.NoTableAvailableException;
import com.orange.restaurant.error.ReservationStartTimeAfterEndTimeException;
import com.orange.restaurant.model.DinnerTable;
import com.orange.restaurant.model.Reservation;
import com.orange.restaurant.model.dto.ReservationRequest;
import com.orange.restaurant.model.dto.ReservationResponseDTO;
import com.orange.restaurant.model.dto.TimeRange;
import com.orange.restaurant.repository.ReservationRepo;
import com.orange.restaurant.repository.TableRepository;
import com.orange.restaurant.service.interfaces.IReservationService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
@Data
@AllArgsConstructor
public class ReservationService implements IReservationService {
    private final ReservationRepo reservationRepo;
    private final TableRepository tableRepository;
    private TableService tableService;

    //Avoid using the dto in the service layer, this separates the view and the request from business processing
    @Override
    public Reservation reserve(ReservationRequest request) {
        if (request.getTimeRange().getStartTime() >= request.getTimeRange().getEndTime()) {
            throw new ReservationStartTimeAfterEndTimeException();
        }
        //ToDo retrieve the tables available in the requested timeslot by joining with the reservations
        //instead of retrieving all tables and filtering in loop
        List<DinnerTable> tables = tableRepository
                .findAllByMaxPersonsGreaterThanEqualOrderByMaxPersonsAsc(request.getNumberOfPersons());
        // tables are ordered asc based on the number of people so that if two tables are available the one with the least maxPer
        // is taken , if the customer wants a table for 3 persons and tables available with 4 and 5 persons the one with 4 is
        // reserved
        //ToDo when looping favor streams with filter above for with condition
        /* tables.stream()
                .filter( table-> tableService.checkIfAvailable(table,request.getTimeRange()))
                .findFirst()
                .orElseThrow( () -> new NoTableAvailableException()); */
        for (DinnerTable table : tables) {
            if (tableService.checkIfAvailable(table,request.getTimeRange())){
                return makeReservation(table,request);
            }
        }
        throw new NoTableAvailableException();
    }

    @Override
    public List<ReservationResponseDTO> findReservationsInSpecificDate(TimeRange timeRange) {
       //Todo mapping is done by the controller, services should do the business logic only
        // this is to ensure that we can have multiple projections from the same service
        return reservationRepo.findInSpecificDate(timeRange.getStartTime(),timeRange.getEndTime())
                .stream().map(ReservationResponseDTO::mapFromEntity).toList();
    }

    private Reservation makeReservation(DinnerTable table, ReservationRequest request) {
        //ToDo checkout mapstruct, map you DTO in the controller and do the get table and save logic in the service
        Reservation reservation = Reservation.builder()
                .table(table)
                .startTime(request.getTimeRange().getStartTime())
                .endTime(request.getTimeRange().getEndTime())
                .build();
        return reservationRepo.save(reservation);
    }


}
