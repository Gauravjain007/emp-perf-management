package com.assignment.soln.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.assignment.soln.model.dto.EmployeeDTO;
import com.assignment.soln.model.dto.EmployeeDetailsDTO;
import com.assignment.soln.model.entity.Employee;
import com.assignment.soln.model.mapper.EmployeeMapper;
import com.assignment.soln.model.mapper.PerformanceReviewMapper;
import com.assignment.soln.repository.EmployeeRepository;
import com.assignment.soln.repository.PerformanceReviewRepository;
import com.assignment.soln.service.EmployeeService;
import com.assignment.soln.specification.EmployeeSpecification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PerformanceReviewRepository performanceReviewRepository;
    private final EmployeeMapper employeeMapper;
    private final PerformanceReviewMapper performanceReviewMapper;

    @Override
    public List<EmployeeDTO> getEmployees(Integer score, LocalDate reviewDate, List<String> departments,
            List<String> projects) {
        log.info("Fetching employees with filters - score: {}, reviewDate: {}, departments: {}, projects: {}",
                score, reviewDate, departments, projects);
        Specification<Employee> spec = EmployeeSpecification.filterEmployees(score, reviewDate, departments,
                projects);

        return employeeRepository.findAll(spec).stream()
                .map(employeeMapper::toDto)
                .toList();
    }

    @Override
    public ResponseEntity<EmployeeDetailsDTO> getEmployeeDetails(Long id) {
        log.info("Fetching details for employee with ID: {}", id);
        Employee employee = employeeRepository.findById(id).orElse(null);
        if (employee == null) {
            log.warn("Employee with ID: {} not found", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        EmployeeDetailsDTO dto = employeeMapper.toDetailsDto(employee);
        // Limit reviews to top 3 (latest by reviewDate)
        dto.setReviews(performanceReviewMapper
                .toDTOList(performanceReviewRepository.findTop3ByEmployeeOrderByReviewDateDesc(employee)));
        return ResponseEntity.ok(dto);
    }
}
