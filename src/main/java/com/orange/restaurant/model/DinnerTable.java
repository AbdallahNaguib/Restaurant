package com.orange.restaurant.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
//ToDo what is the size of the generated field? does it accomodate the limit set in the user story?
    private String tableName;
    private short maxPersons;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "table")
    private List<Reservation> reservations;
}
