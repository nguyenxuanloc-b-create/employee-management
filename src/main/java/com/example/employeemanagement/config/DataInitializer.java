package com.example.employeemanagement.config;

import com.example.employeemanagement.entity.AppUser;
import com.example.employeemanagement.entity.Department;
import com.example.employeemanagement.entity.Employee;
import com.example.employeemanagement.repository.AppUserRepository;
import com.example.employeemanagement.repository.DepartmentRepository;
import com.example.employeemanagement.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile("dev")
public class DataInitializer {

    @Bean
    CommandLineRunner seedDemoData(
            DepartmentRepository departmentRepository,
            EmployeeRepository employeeRepository,
            AppUserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.count() == 0) {
                userRepository.save(new AppUser("admin", passwordEncoder.encode("admin123"), "ADMIN"));
                userRepository.save(new AppUser("user", passwordEncoder.encode("user123"), "USER"));
            }

            if (employeeRepository.count() == 0) {
                Department it = departmentRepository.findByNameIgnoreCase("IT")
                        .orElseGet(() -> departmentRepository.save(new Department("IT")));
                Department hr = departmentRepository.findByNameIgnoreCase("HR")
                        .orElseGet(() -> departmentRepository.save(new Department("HR")));
                Department sales = departmentRepository.findByNameIgnoreCase("Sales")
                        .orElseGet(() -> departmentRepository.save(new Department("Sales")));

                employeeRepository.save(new Employee("Nguyen Van An", "an@example.com", it));
                employeeRepository.save(new Employee("Tran Thi Binh", "binh@example.com", hr));
                employeeRepository.save(new Employee("Le Minh Chau", "chau@example.com", sales));
                employeeRepository.save(new Employee("Pham Gia Dung", "dung@example.com", it));
            }
        };
    }
}
