package com.assignment.soln.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.assignment.soln.model.dto.EmployeeDTO;
import com.assignment.soln.model.dto.EmployeeDetailsDTO;

public interface EmployeeService {

    /**
     * Retrieve a list of employees based on the given filters.
     *
     * @param score       the target score for the employee's performance review
     * @param reviewDate  the target date of the employee's performance review
     * @param departments the target departments
     * @param projects    the target projects
     * @return a list of employees matching the given criteria
     */
    public List<EmployeeDTO> getEmployees(Integer score, LocalDate reviewDate, List<String> departments,
            List<String> projects);

    /**
     * Retrieve detailed information about a specific employee by their ID.
     * 
     * @param id the employee ID
     * @return the employee details
     */
    public ResponseEntity<EmployeeDetailsDTO> getEmployeeDetails(Long id);
}
