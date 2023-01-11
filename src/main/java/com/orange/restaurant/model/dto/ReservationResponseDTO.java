package com.orange.restaurant.model.dto;

import com.orange.restaurant.model.Reservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReservationResponseDTO {
    private String tableName;
    private short maxPersons;
    private long tableId;
    private Long startTime, endTime;
    public static ReservationResponseDTO mapFromEntity(Reservation reservation){
        return new ReservationResponseDTO(reservation.getTable().getTableName(),
                reservation.getTable().getMaxPersons(),
                reservation.getTable().getId(),
                reservation.getStartTime(),
                reservation.getEndTime());
    }
}
