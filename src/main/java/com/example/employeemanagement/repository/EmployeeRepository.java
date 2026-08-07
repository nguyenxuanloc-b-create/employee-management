package com.example.employeemanagement.repository;

import com.example.employeemanagement.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByNameContainingIgnoreCase(String name);

    List<Employee> findByDepartment_NameContainingIgnoreCase(String departmentName);

    List<Employee> findByNameContainingIgnoreCaseOrDepartment_NameContainingIgnoreCase(
            String name, String departmentName);
}
