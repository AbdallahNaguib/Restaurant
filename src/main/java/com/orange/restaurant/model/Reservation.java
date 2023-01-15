package com.orange.restaurant.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Table(name = "reservations")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue
    private Long id;

    // the reservation times will be saved as long values
    //ToDo why sava time as long?
    private Long startTime, endTime;

    //ToDo who reserved this table? are you missing a field

    @ManyToOne(fetch = FetchType.LAZY)
    private DinnerTable table;
}
