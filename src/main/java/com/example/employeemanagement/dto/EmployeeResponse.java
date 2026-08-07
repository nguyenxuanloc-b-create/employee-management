package com.example.employeemanagement.dto;

import com.example.employeemanagement.entity.Employee;

public record EmployeeResponse(
        Long id,
        String name,
        String email,
        Long departmentId,
        String departmentName
) {
    public static EmployeeResponse from(Employee employee) {
        return new EmployeeResponse(
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                employee.getDepartment().getId(),
                employee.getDepartment().getName()
        );
    }
}
