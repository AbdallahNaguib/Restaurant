package com.orange.restaurant.model.dto;

import com.orange.restaurant.model.DinnerTable;
import com.orange.restaurant.model.Reservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class TableResponseDTO {
    private String tableName;
    private long tableId;
    private short maxPersons;
    private List<TimeRange> reservations;

    public static TableResponseDTO mapFromEntity(DinnerTable table) {
        TableResponseDTO response = TableResponseDTO.builder()
                .maxPersons(table.getMaxPersons())
                .tableName(table.getTableName())
                .tableId(table.getId())
                .build();
        response.reservations = new ArrayList<>();
        for (Reservation reservation : table.getReservations()) {
            response.reservations.add(new TimeRange(reservation.getStartTime(),
                    reservation.getEndTime()));
        }
        return response;
    }
}
