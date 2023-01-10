package com.example.restaurant.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "dinner_tables")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DinnerTable {
    @Id
    @GeneratedValue
    private Long id;

    private String tableName;
    private short maxPersons;

    @OneToMany(mappedBy = "table")
    private List<Reservation> reservations;
}
