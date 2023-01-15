package com.orange.restaurant.controller;

import com.orange.restaurant.model.DinnerTable;
import com.orange.restaurant.model.dto.DinnerTableRequestDTO;
import com.orange.restaurant.model.dto.TableResponseDTO;
import com.orange.restaurant.model.dto.TimeRange;
import com.orange.restaurant.service.TableService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequiredArgsConstructor
@RequestMapping("/api")
public class TablesController {
    private final TableService tableService;
//ToDo remov the admin prefix, no need to use /create, when you set the http method to POST it's understood that you are doing a creation
    @PostMapping("/admin/tables/create")
    public ResponseEntity<DinnerTable> create(@Valid @RequestBody DinnerTableRequestDTO request) {
        return ResponseEntity.ok(tableService.save(request));
    }

    //ToDo should be a get, filter should be in query param not body
    @PostMapping("/tables")
    public ResponseEntity<List<TableResponseDTO>> getAvailableTables(@RequestBody TimeRange timeRange) {
        return ResponseEntity.ok(tableService.getAvailableTables(timeRange));
    }
    @GetMapping("/admin/tables")
    public ResponseEntity<List<TableResponseDTO>> getAllTables(){
        return ResponseEntity.ok(tableService.getAllTables());
    }
}
