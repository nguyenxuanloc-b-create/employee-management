package com.example.employeemanagement.service;

import com.example.employeemanagement.dto.EmployeeMemoryDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class InMemoryEmployeeService {

    private final AtomicLong idGenerator = new AtomicLong(2);
    private final List<EmployeeMemoryDto> employees = new ArrayList<>();

    public InMemoryEmployeeService() {
        employees.add(new EmployeeMemoryDto(1L, "Nguyen Van An", "an@example.com", "IT"));
        employees.add(new EmployeeMemoryDto(2L, "Tran Thi Binh", "binh@example.com", "HR"));
    }

    public List<EmployeeMemoryDto> findAll(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return List.copyOf(employees);
        }
        String key = keyword.toLowerCase(Locale.ROOT);
        return employees.stream()
                .filter(e -> e.name().toLowerCase(Locale.ROOT).contains(key)
                        || e.department().toLowerCase(Locale.ROOT).contains(key))
                .toList();
    }

    public Optional<EmployeeMemoryDto> findById(Long id) {
        return employees.stream().filter(e -> e.id().equals(id)).findFirst();
    }

    public EmployeeMemoryDto create(EmployeeMemoryDto request) {
        EmployeeMemoryDto created = new EmployeeMemoryDto(
                idGenerator.incrementAndGet(),
                request.name(),
                request.email(),
                request.department()
        );
        employees.add(created);
        return created;
    }
}
