package com.assignment.soln.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assignment.soln.model.entity.EmployeeProject;

public interface EmployeeProjectRepository extends JpaRepository<EmployeeProject, Long> {

}
