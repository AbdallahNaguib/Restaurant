package com.orange.restaurant.service.interfaces;

import com.orange.restaurant.model.DinnerTable;
import com.orange.restaurant.model.dto.DinnerTableDTO;
import com.orange.restaurant.model.dto.TimeRange;

import java.util.List;

public interface ITableService {
    List<DinnerTable> getAvailableTables(TimeRange timeRange);

    List<DinnerTable> getAllTables();

    boolean checkIfAvailable(DinnerTable table, TimeRange timeRange);

    DinnerTable save(DinnerTableDTO table);

}
