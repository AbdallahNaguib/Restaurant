package com.example.restaurant.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    private Long startTime, endTime;

    @ManyToOne()
    private DinnerTable table;
}
