package com.orange.restaurant.repository;

import com.orange.restaurant.model.Reservation;
import com.orange.restaurant.model.dto.GetReservationsResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepo extends JpaRepository<Reservation, Long> {
    @Query(nativeQuery = true,value = "select start_time,end_time,table_id" +
            ",tableName,maxPersons, from reservations left join dinner_tables on " +
            "reservations.table_id = dinner_tables.id " +
            "where reservations.start_time >= :startTime and reservations.end_time <= :endTime")
    List<GetReservationsResponseDTO> findInSpecificDate(@Param("startTime") long startTime, @Param("endTime") long endTime);
}
