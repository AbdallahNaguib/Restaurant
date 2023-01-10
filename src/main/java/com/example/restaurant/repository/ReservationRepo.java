package com.example.restaurant.repository;

import com.example.restaurant.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepo extends JpaRepository<Reservation, Long> {
    @Query(nativeQuery = true,value = "select reservations.* from reservations " +
            "where reservations.startTime >= :startTime and reservations.startTime <= :endTime")
    List<Reservation> findInSpecificDate(@Param("startTime") long startTime, @Param("endTime") long endTime);
}
