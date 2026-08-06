package com.example.employeemanagement.service;

import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.Locale;
import java.util.UUID;

@Service
public class UtilityService {

    public String formatName(String rawName) {
        if (rawName == null || rawName.isBlank()) {
            return "";
        }
        String normalized = rawName.trim().replaceAll("\\s+", " ").toLowerCase(Locale.ROOT);
        StringBuilder result = new StringBuilder();
        boolean capitalize = true;
        for (char c : normalized.toCharArray()) {
            if (capitalize && Character.isLetter(c)) {
                result.append(Character.toUpperCase(c));
                capitalize = false;
            } else {
                result.append(c);
            }
            if (Character.isWhitespace(c)) {
                capitalize = true;
            }
        }
        return result.toString();
    }

    public String generateEmployeeCode() {
        return "EMP-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase(Locale.ROOT);
    }

    public String slugify(String value) {
        if (value == null) {
            return "";
        }
        String noAccent = Normalizer.normalize(value, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
        return noAccent.toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("(^-|-$)", "");
    }
}
