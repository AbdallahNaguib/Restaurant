package com.orange.restaurant.service;

import com.orange.restaurant.error.NoTableAvailableException;
import com.orange.restaurant.error.ReservationStartTimeAfterEndTimeException;
import com.orange.restaurant.model.DinnerTable;
import com.orange.restaurant.model.Reservation;
import com.orange.restaurant.model.dto.ReservationRequest;
import com.orange.restaurant.model.dto.TimeRange;
import com.orange.restaurant.repository.ReservationRepo;
import com.orange.restaurant.repository.TableRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {
    List<Reservation> mockReservations = new ArrayList<>();
    @Mock
    private ReservationRepo reservationRepo;
    @Mock
    private TableRepository tableRepository;
    @InjectMocks
    private TableService tableService;
    @InjectMocks
    private ReservationService reservationService;

    @BeforeEach
    void init(){
        reservationService.setTableService(tableService);
    }

    @Test
    public void testReserve_InvalidTimeRange() {
        Assertions.assertThrows(ReservationStartTimeAfterEndTimeException.class, () -> {
            reservationService.reserve(ReservationRequest.builder()
                    .timeRange(TimeRange.builder()
                            .startTime(20L)
                            .endTime(15L)
                            .build())
                    .build());
        });
    }

    @Test
    public void testReserve_NoTableAvailable() {
        when(tableRepository
                .findAllByMaxPersonsGreaterThanEqualOrderByMaxPersonsAsc(anyInt()))
                .thenAnswer((Answer<List<DinnerTable>>) x -> new ArrayList<>());
        Assertions.assertThrows(NoTableAvailableException.class, () -> {
            reservationService.reserve(ReservationRequest.builder()
                    .numberOfPersons((short) 3)
                    .timeRange(TimeRange.builder()
                            .startTime(20L)
                            .endTime(25L)
                            .build())
                    .build());
        });
    }

    @Test
    public void testReserve_Valid() {
        when(tableRepository
                .findAllByMaxPersonsGreaterThanEqualOrderByMaxPersonsAsc(anyInt()))
                .thenAnswer((Answer<List<DinnerTable>>) x -> {
                    List<DinnerTable> tables = new ArrayList<>();
                    tables.add(DinnerTable.builder()
                            .maxPersons((short) 3)
                            .tableName("t1")
                            .build());
                    return tables;
                });
        when(reservationRepo.save(any()))
                .thenAnswer(new Answer<Reservation>() {
                    @Override
                    public Reservation answer(InvocationOnMock par) throws Throwable {
                        Reservation input = par.getArgument(0, Reservation.class);
                        mockReservations.add(input);
                        return input;
                    }
                });
        Assertions.assertDoesNotThrow(() -> {
            reservationService.reserve(ReservationRequest.builder()
                    .numberOfPersons((short) 3)
                    .timeRange(TimeRange.builder()
                            .startTime(20L)
                            .endTime(25L)
                            .build())
                    .build());
        });

    }
}
