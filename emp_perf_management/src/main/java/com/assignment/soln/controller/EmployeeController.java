package com.assignment.soln.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.soln.model.dto.EmployeeDTO;
import com.assignment.soln.model.dto.EmployeeDetailsDTO;
import com.assignment.soln.service.EmployeeService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/employees")
@AllArgsConstructor
public class EmployeeController {

    private EmployeeService employeeService;

    /**
     * Retrieve a list of employees based on the given filters.
     * 
     * @param score       the target score for the employee's performance review
     * @param reviewDate  the target date of the employee's performance review
     * @param departments the target departments
     * @param projects    the target projects
     * @return a list of employees matching the given criteria
     */
    @GetMapping
    public List<EmployeeDTO> getEmployees(
            @RequestParam(required = false) Integer score,
            @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate reviewDate,
            @RequestParam(required = false) List<String> departments,
            @RequestParam(required = false) List<String> projects) {

        return employeeService.getEmployees(score, reviewDate, departments, projects);
    }

    /**
     * Returns the employee details for the given id.
     *
     * @param id the employee id
     * @return the employee details
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDetailsDTO> getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeDetails(id);
    }
}
