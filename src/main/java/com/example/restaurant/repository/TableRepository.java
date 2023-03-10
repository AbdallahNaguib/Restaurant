package com.example.restaurant.repository;

import com.example.restaurant.model.DinnerTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TableRepository extends JpaRepository<DinnerTable,Long> {
    List<DinnerTable> findAllByMaxPersonsGreaterThanEqualOrderByMaxPersonsAsc(int persons);
}
