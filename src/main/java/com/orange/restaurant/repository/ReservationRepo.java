package com.orange.restaurant.repository;

import com.orange.restaurant.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepo extends JpaRepository<Reservation, Long> {
    //ToDo try using spring data derived query--> findByStartTimeGreaterThanEndTimeLessThan
    @Query(nativeQuery = true,value = "select reservations.* from reservations " +
            "where reservations.start_time >= :startTime and reservations.end_time <= :endTime")
    List<Reservation> findInSpecificDate(@Param("startTime") long startTime, @Param("endTime") long endTime);
}
