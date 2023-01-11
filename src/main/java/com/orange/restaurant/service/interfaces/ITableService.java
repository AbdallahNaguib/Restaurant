package com.orange.restaurant.service.interfaces;

import com.orange.restaurant.model.DinnerTable;
import com.orange.restaurant.model.dto.DinnerTableRequestDTO;
import com.orange.restaurant.model.dto.TableResponseDTO;
import com.orange.restaurant.model.dto.TimeRange;

import java.util.List;

public interface ITableService {
    List<TableResponseDTO> getAvailableTables(TimeRange timeRange);

    List<TableResponseDTO> getAllTables();

    boolean checkIfAvailable(DinnerTable table, TimeRange timeRange);

    DinnerTable save(DinnerTableRequestDTO table);

}
