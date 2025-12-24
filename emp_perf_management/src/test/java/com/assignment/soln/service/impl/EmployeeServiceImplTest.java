package com.assignment.soln.service.impl;

import com.assignment.soln.model.dto.EmployeeDetailsDTO;
import com.assignment.soln.model.entity.Employee;
import com.assignment.soln.model.mapper.EmployeeMapper;
import com.assignment.soln.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeMapper employeeMapper;

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
        EmployeeDetailsDTO dto = new EmployeeDetailsDTO();

        when(employeeRepository.findAll(any(Specification.class))).thenReturn(Collections.singletonList(emp));
        when(employeeMapper.toDto(emp)).thenReturn(dto);

        List<EmployeeDetailsDTO> result = employeeServiceImpl.getEmployees(score, reviewDate, departments, projects);

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

        List<EmployeeDetailsDTO> result = employeeServiceImpl.getEmployees(null, null, null, null);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetEmployeeDetails_ReturnsDto() {
        Long id = 1L;
        Employee emp = new Employee();
        EmployeeDetailsDTO dto = new EmployeeDetailsDTO();

        when(employeeRepository.findById(id)).thenReturn(Optional.of(emp));
        when(employeeMapper.toDto(emp)).thenReturn(dto);

        EmployeeDetailsDTO result = employeeServiceImpl.getEmployeeDetails(id);

        assertNotNull(result);
        assertSame(dto, result);
        verify(employeeRepository).findById(id);
        verify(employeeMapper).toDto(emp);
    }

    @Test
    void testGetEmployeeDetails_ThrowsExceptionIfNotFound() {
        Long id = 2L;
        when(employeeRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> employeeServiceImpl.getEmployeeDetails(id));
        assertEquals("Employee not found", ex.getMessage());
        verify(employeeRepository).findById(id);
    }
}
