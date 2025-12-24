package com.assignment.soln.model.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class EmployeeDTO {
    private String name;
    private String email;
    private String department;
    private String dateOfJoining;
    private BigDecimal salary;
    private String manager;
}
