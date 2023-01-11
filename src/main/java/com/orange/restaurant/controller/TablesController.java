package com.orange.restaurant.controller;

import com.orange.restaurant.model.DinnerTable;
import com.orange.restaurant.model.dto.DinnerTableDTO;
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

    @PostMapping("/admin/tables/create")
    public ResponseEntity<DinnerTable> create(@Valid @RequestBody DinnerTableDTO request) {
        return ResponseEntity.ok(tableService.save(request));
    }

    @PostMapping("/tables")
    public ResponseEntity<List<DinnerTable>> getAvailableTables(@RequestBody TimeRange timeRange) {
        return ResponseEntity.ok(tableService.getAvailableTables(timeRange));
    }
    @GetMapping("/admin/tables")
    public ResponseEntity<List<DinnerTable>> getAllTables(){
        return ResponseEntity.ok(tableService.getAllTables());
    }
}
