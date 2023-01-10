package com.example.restaurant.controller;

import com.example.restaurant.model.DinnerTable;
import com.example.restaurant.model.dto.DinnerTableDTO;
import com.example.restaurant.model.dto.TimeRange;
import com.example.restaurant.service.TableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController()
@RequiredArgsConstructor
@RequestMapping("/api")
public class TablesController {
    private final TableService tableService;

    @PostMapping("/admin/tables")
    public ResponseEntity<DinnerTable> create(@Valid @RequestBody DinnerTableDTO request) {
        return ResponseEntity.ok(tableService.save(request));
    }

    @GetMapping("/tables")
    public ResponseEntity<List<DinnerTable>> getAvailableTables(@RequestBody TimeRange timeRange) {
        return ResponseEntity.ok(tableService.getAvailableTables(timeRange));
    }
    @GetMapping("/admin/tables")
    public ResponseEntity<List<DinnerTable>> getAllTables(){
        return ResponseEntity.ok(tableService.getAllTables());
    }
}
