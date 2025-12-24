package com.assignment.soln.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.assignment.soln.model.dto.EmployeeDTO;
import com.assignment.soln.model.dto.EmployeeDetailsDTO;
import com.assignment.soln.model.entity.Employee;
import com.assignment.soln.model.mapper.EmployeeMapper;
import com.assignment.soln.model.mapper.PerformanceReviewMapper;
import com.assignment.soln.repository.EmployeeRepository;
import com.assignment.soln.repository.PerformanceReviewRepository;

@SpringBootTest
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @Mock
    private PerformanceReviewRepository performanceReviewRepository;

    @Mock
    private PerformanceReviewMapper performanceReviewMapper;

    @InjectMocks
    private EmployeeServiceImpl employeeServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @SuppressWarnings("unchecked")
    @Test
    void testGetEmployees_ReturnsMappedDtos() {
        Integer score = 7;
        LocalDate reviewDate = LocalDate.now();
        List<String> departments = Arrays.asList("HR", "IT");
        List<String> projects = Arrays.asList("ProjectA");

        Employee emp = new Employee();
        EmployeeDTO dto = new EmployeeDTO();

        when(employeeRepository.findAll(any(Specification.class))).thenReturn(Collections.singletonList(emp));
        when(employeeMapper.toDto(emp)).thenReturn(dto);

        List<EmployeeDTO> result = employeeServiceImpl.getEmployees(score, reviewDate, departments, projects);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertSame(dto, result.get(0));
        verify(employeeRepository).findAll(any(Specification.class));
        verify(employeeMapper).toDto(emp);
    }

    @SuppressWarnings("unchecked")
    @Test
    void testGetEmployees_EmptyResult() {
        when(employeeRepository.findAll(any(Specification.class))).thenReturn(Collections.emptyList());

        List<EmployeeDTO> result = employeeServiceImpl.getEmployees(null, null, null, null);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetEmployeeDetails_ReturnsDto() {
        Long id = 1L;
        Employee emp = new Employee();
        EmployeeDetailsDTO dto = new EmployeeDetailsDTO();

        when(employeeRepository.findById(id)).thenReturn(Optional.of(emp));
        when(employeeMapper.toDetailsDto(emp)).thenReturn(dto);
        when(performanceReviewRepository.findTop3ByEmployeeOrderByReviewDateDesc(emp))
                .thenReturn(Collections.emptyList());
        when(performanceReviewMapper.toDTOList(Collections.emptyList())).thenReturn(Collections.emptyList());

        ResponseEntity<EmployeeDetailsDTO> result = employeeServiceImpl.getEmployeeDetails(id);

        assertNotNull(result);
        assertSame(HttpStatus.OK, result.getStatusCode());
        verify(employeeRepository).findById(id);
        verify(employeeMapper).toDetailsDto(emp);
    }

    @Test
    void testGetEmployeeDetails_ReturnsNotFound() {
        Long id = 2L;
        when(employeeRepository.findById(id)).thenReturn(Optional.empty());
        ResponseEntity<EmployeeDetailsDTO> responseEntity = employeeServiceImpl.getEmployeeDetails(id);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}
