package com.orange.restaurant.service;

import com.orange.restaurant.model.DinnerTable;
import com.orange.restaurant.model.Reservation;
import com.orange.restaurant.model.dto.DinnerTableDTO;
import com.orange.restaurant.model.dto.TimeRange;
import com.orange.restaurant.repository.TableRepository;
import com.orange.restaurant.service.interfaces.ITableService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TableService implements ITableService {
    private final TableRepository tableRepository;

    @Override
    public DinnerTable save(DinnerTableDTO request) {
        DinnerTable table = DinnerTable.builder()
                .maxPersons(request.getMaxPersons())
                .tableName(request.getName())
                .build();
        return tableRepository.save(table);
    }

    @Override
    public List<DinnerTable> getAvailableTables(TimeRange timeRange) {
        List<DinnerTable> allTables = tableRepository.findAll();
        return allTables.stream().filter(table -> checkIfAvailable(table,timeRange)).toList();
    }

    @Override
    public List<DinnerTable> getAllTables() {
        return tableRepository.findAll();
    }

    // checks if the table is available at the that time frame
    @Override
    public boolean checkIfAvailable(DinnerTable table, TimeRange timeRange) {
        for (Reservation reservation : table.getReservations()) {
            boolean requestIsBeforeCurrentReservation = timeRange.getEndTime() <= reservation.getStartTime();
            boolean requestIsAfterCurrentReservation = timeRange.getStartTime() >= reservation.getEndTime();
            if (!requestIsAfterCurrentReservation && !requestIsBeforeCurrentReservation) {
                // then both intervals intersect
                return false;
            }
        }
        return true;
    }

}
