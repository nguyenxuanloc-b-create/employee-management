package com.example.employeemanagement.controller;

import com.example.employeemanagement.service.UtilityService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/tools")
public class UtilityController {

    private final UtilityService utilityService;
    private final PasswordEncoder passwordEncoder;

    public UtilityController(UtilityService utilityService, PasswordEncoder passwordEncoder) {
        this.utilityService = utilityService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/sample-code")
    public Map<String, String> sampleCode() {
        return Map.of("employeeCode", utilityService.generateEmployeeCode());
    }

    @GetMapping("/format-name")
    public Map<String, String> formatName(@RequestParam String value) {
        return Map.of("input", value, "formatted", utilityService.formatName(value));
    }

    @GetMapping("/hash")
    public Map<String, String> hash(@RequestParam String value) {
        return Map.of("hash", passwordEncoder.encode(value));
    }
}
