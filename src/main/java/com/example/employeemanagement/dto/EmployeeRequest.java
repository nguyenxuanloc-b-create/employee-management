package com.example.employeemanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EmployeeRequest(
        @NotBlank(message = "Name is required")
        @Size(min = 2, max = 120, message = "Name must contain 2-120 characters")
        String name,

        @NotBlank(message = "Email is required")
        @Email(message = "Email is invalid")
        @Size(max = 160, message = "Email is too long")
        String email,

        Long departmentId,

        @Size(max = 100, message = "Department name is too long")
        String departmentName
) {
}
