package com.example.employeemanagement.controller;

import com.example.employeemanagement.service.ReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
public class ReportRestController {

    private final ReportService reportService;

    public ReportRestController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/summary")
    public ReportService.EmployeeSummary summary() {
        return reportService.getSummary();
    }

    @GetMapping("/statistics")
    public ReportService.EmployeeStatistics statistics() {
        return reportService.getStatistics();
    }
}
