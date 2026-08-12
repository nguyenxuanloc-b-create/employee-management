package com.example.employeemanagement.service;

import com.example.employeemanagement.dto.DepartmentEmployeeCount;
import com.example.employeemanagement.repository.DepartmentRepository;
import com.example.employeemanagement.repository.EmployeeRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

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

    @Cacheable("employeeStatistics")
    public EmployeeStatistics getStatistics() {
        long total = employeeRepository.count();
        List<DepartmentEmployeeCount> byDepartment = employeeRepository.countEmployeesByDepartment();
        List<DepartmentShare> distribution = byDepartment.stream()
                .map(item -> new DepartmentShare(
                        item.departmentName(),
                        item.employeeCount(),
                        total == 0 ? 0.0 : item.employeeCount() * 100.0 / total
                ))
                .toList();
        return new EmployeeStatistics(total, distribution, Instant.now());
    }

    public record EmployeeSummary(long totalEmployees, long totalDepartments, Instant generatedAt) {
    }

    public record DepartmentShare(String departmentName, long employeeCount, double percentage) {
    }

    public record EmployeeStatistics(
            long totalEmployees,
            List<DepartmentShare> departments,
            Instant generatedAt
    ) {
    }
}
