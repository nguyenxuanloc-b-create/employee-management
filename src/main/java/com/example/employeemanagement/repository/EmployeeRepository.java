package com.example.employeemanagement.repository;

import com.example.employeemanagement.dto.DepartmentEmployeeCount;
import com.example.employeemanagement.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByNameContainingIgnoreCase(String name);

    List<Employee> findByDepartment_NameContainingIgnoreCase(String departmentName);

    List<Employee> findByNameContainingIgnoreCaseOrDepartment_NameContainingIgnoreCase(
            String name, String departmentName);

    @Query("""
            select new com.example.employeemanagement.dto.DepartmentEmployeeCount(
                e.department.name, count(e)
            )
            from Employee e
            group by e.department.id, e.department.name
            order by count(e) desc
            """)
    List<DepartmentEmployeeCount> countEmployeesByDepartment();
}
