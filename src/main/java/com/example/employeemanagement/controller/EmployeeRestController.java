package com.example.employeemanagement.controller;

import com.example.employeemanagement.dto.EmployeeMemoryDto;
import com.example.employeemanagement.service.InMemoryEmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeRestController {

    private final InMemoryEmployeeService employeeService;

    public EmployeeRestController(InMemoryEmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<List<EmployeeMemoryDto>> findAll(
            @RequestParam(required = false) String keyword) {
        return ResponseEntity.ok(employeeService.findAll(keyword));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeMemoryDto> findById(@PathVariable Long id) {
        return employeeService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EmployeeMemoryDto> create(@RequestBody EmployeeMemoryDto request) {
        EmployeeMemoryDto created = employeeService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(URI.create("/api/employees/" + created.id()))
                .body(created);
    }
}
