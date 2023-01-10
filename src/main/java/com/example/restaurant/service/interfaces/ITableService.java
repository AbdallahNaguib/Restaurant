package com.example.restaurant.service.interfaces;


import com.example.restaurant.model.DinnerTable;
import com.example.restaurant.model.dto.DinnerTableDTO;
import com.example.restaurant.model.dto.TimeRange;

import java.util.List;

public interface ITableService {
    List<DinnerTable> getAvailableTables(TimeRange timeRange);

    List<DinnerTable> getAllTables();

    boolean checkIfAvailable(DinnerTable table, TimeRange timeRange);

    DinnerTable save(DinnerTableDTO table);

}
