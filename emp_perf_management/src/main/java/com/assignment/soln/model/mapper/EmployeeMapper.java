package com.assignment.soln.model.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.assignment.soln.model.dto.EmployeeDetailsDTO;
import com.assignment.soln.model.entity.Employee;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    @Mapping(source = "department.name", target = "department")
    @Mapping(source = "manager.name", target = "manager")
    @Mapping(target = "projects", expression = "java(employee.getProjects() != null ? employee.getProjects().stream().map(ep -> ep.getProject().getName()).filter(p -> p != null).collect(java.util.stream.Collectors.toList()) : null)")
    @Mapping(target = "reviews", source = "reviews")
    @Mapping(target = "dateOfJoining", expression = "java(employee.getDateOfJoining() != null ? employee.getDateOfJoining().toString() : null)")
    EmployeeDetailsDTO toDto(Employee employee);

    @InheritInverseConfiguration
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "manager", ignore = true)
    @Mapping(target = "projects", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    Employee toEntity(EmployeeDetailsDTO dto);

    List<EmployeeDetailsDTO> toDtoList(List<Employee> employees);

    List<Employee> toEntityList(List<EmployeeDetailsDTO> dtos);
}
