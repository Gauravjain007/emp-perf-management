package com.assignment.soln.model.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class EmployeeDetailsDTO {
    private String name;
    private String email;
    private String department;
    private String dateOfJoining;
    private BigDecimal salary;
    private String manager;
    private List<String> projects;
    private List<PerformanceReviewDTO> reviews;
}
