package com.example.employeemanagement.service;

import com.example.employeemanagement.dto.EmployeeRequest;
import com.example.employeemanagement.entity.Department;
import com.example.employeemanagement.entity.Employee;
import com.example.employeemanagement.exception.EmployeeNotFoundException;
import com.example.employeemanagement.repository.DepartmentRepository;
import com.example.employeemanagement.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EmployeeService {

    private static final Logger log = LoggerFactory.getLogger(EmployeeService.class);

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

    @CacheEvict(value = {"employeeSummary", "employeeStatistics"}, allEntries = true)
    public Employee create(EmployeeRequest request) {
        Employee employee = new Employee();
        applyRequest(employee, request);
        Employee saved = employeeRepository.save(employee);
        log.info("Created employee id={} email={}", saved.getId(), saved.getEmail());
        return saved;
    }

    @CacheEvict(value = {"employeeSummary", "employeeStatistics"}, allEntries = true)
    public Employee update(Long id, EmployeeRequest request) {
        Employee employee = findById(id);
        applyRequest(employee, request);
        Employee saved = employeeRepository.save(employee);
        log.info("Updated employee id={} email={}", saved.getId(), saved.getEmail());
        return saved;
    }

    @CacheEvict(value = {"employeeSummary", "employeeStatistics"}, allEntries = true)
    public void delete(Long id) {
        Employee employee = findById(id);
        employeeRepository.delete(employee);
        log.info("Deleted employee id={} email={}", employee.getId(), employee.getEmail());
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
