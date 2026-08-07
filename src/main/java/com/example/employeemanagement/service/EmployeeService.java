package com.example.employeemanagement.service;

import com.example.employeemanagement.entity.Department;
import com.example.employeemanagement.entity.Employee;
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
                .orElseThrow(() -> new IllegalArgumentException("Employee not found: " + id));
    }

    public Employee create(Employee request) {
        request.setId(null);
        request.setName(utilityService.formatName(request.getName()));
        request.setDepartment(resolveDepartment(request.getDepartment()));
        return employeeRepository.save(request);
    }

    public Employee update(Long id, Employee request) {
        Employee employee = findById(id);
        employee.setName(utilityService.formatName(request.getName()));
        employee.setEmail(request.getEmail());
        employee.setDepartment(resolveDepartment(request.getDepartment()));
        return employeeRepository.save(employee);
    }

    public void delete(Long id) {
        employeeRepository.delete(findById(id));
    }

    private Department resolveDepartment(Department input) {
        if (input == null) {
            throw new IllegalArgumentException("Department is required");
        }
        if (input.getId() != null) {
            return departmentRepository.findById(input.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Department not found: " + input.getId()));
        }
        if (input.getName() == null || input.getName().isBlank()) {
            throw new IllegalArgumentException("Department id or name is required");
        }
        return departmentRepository.findByNameIgnoreCase(input.getName().trim())
                .orElseGet(() -> departmentRepository.save(new Department(input.getName().trim())));
    }
}
