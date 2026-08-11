package com.example.employeemanagement.service;

import com.example.employeemanagement.repository.DepartmentRepository;
import com.example.employeemanagement.repository.EmployeeRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class ReportService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public ReportService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    @Cacheable("employeeSummary")
    public EmployeeSummary getSummary() {
        return new EmployeeSummary(
                employeeRepository.count(),
                departmentRepository.count(),
                Instant.now()
        );
    }

    public record EmployeeSummary(long totalEmployees, long totalDepartments, Instant generatedAt) {
    }
}
