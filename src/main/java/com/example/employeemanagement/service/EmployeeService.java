package com.example.employeemanagement.service;

import com.example.employeemanagement.dto.EmployeeRequest;
import com.example.employeemanagement.entity.Department;
import com.example.employeemanagement.entity.Employee;
import com.example.employeemanagement.exception.EmployeeNotFoundException;
import com.example.employeemanagement.repository.DepartmentRepository;
import com.example.employeemanagement.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final UtilityService utilityService;

    public EmployeeService(EmployeeRepository employeeRepository,
                           DepartmentRepository departmentRepository,
                           UtilityService utilityService) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.utilityService = utilityService;
    }

    @Transactional(readOnly = true)
    public List<Employee> findAll(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return employeeRepository.findAll();
        }
        return employeeRepository
                .findByNameContainingIgnoreCaseOrDepartment_NameContainingIgnoreCase(keyword, keyword);
    }

    @Transactional(readOnly = true)
    public Employee findById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    public Employee create(EmployeeRequest request) {
        Employee employee = new Employee();
        applyRequest(employee, request);
        return employeeRepository.save(employee);
    }

    public Employee update(Long id, EmployeeRequest request) {
        Employee employee = findById(id);
        applyRequest(employee, request);
        return employeeRepository.save(employee);
    }

    public void delete(Long id) {
        employeeRepository.delete(findById(id));
    }

    private void applyRequest(Employee employee, EmployeeRequest request) {
        employee.setName(utilityService.formatName(request.name()));
        employee.setEmail(request.email().trim().toLowerCase());
        employee.setDepartment(resolveDepartment(request.departmentId(), request.departmentName()));
    }

    private Department resolveDepartment(Long departmentId, String departmentName) {
        if (departmentId != null) {
            return departmentRepository.findById(departmentId)
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Department not found with id: " + departmentId));
        }
        if (departmentName == null || departmentName.isBlank()) {
            throw new IllegalArgumentException("departmentId or departmentName is required");
        }
        String normalizedName = departmentName.trim();
        return departmentRepository.findByNameIgnoreCase(normalizedName)
                .orElseGet(() -> departmentRepository.save(new Department(normalizedName)));
    }
}
