package com.assignment.soln.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assignment.soln.model.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

}
