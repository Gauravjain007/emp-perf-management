package com.assignment.soln.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.assignment.soln.model.dto.EmployeeDetailsDTO;
import com.assignment.soln.model.entity.Employee;
import com.assignment.soln.model.mapper.EmployeeMapper;
import com.assignment.soln.repository.EmployeeRepository;
import com.assignment.soln.service.EmployeeService;
import com.assignment.soln.specification.EmployeeSpecification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final EmployeeMapper employeeMapper;

    @Override
    public List<EmployeeDetailsDTO> getEmployees(Integer score, LocalDate reviewDate, List<String> departments,
            List<String> projects) {
        log.info("Fetching employees with filters - score: {}, reviewDate: {}, departments: {}, projects: {}",
                score, reviewDate, departments, projects);
        Specification<Employee> spec = EmployeeSpecification.filterEmployees(score, reviewDate, departments, projects);

        return employeeRepository.findAll(spec).stream()
                .map(employeeMapper::toDto)
                .toList();
    }

    @Override
    public EmployeeDetailsDTO getEmployeeDetails(Long id) {
        log.info("Fetching details for employee with ID: {}", id);
        return employeeMapper
                .toDto(employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found")));
    }
}
