package com.assignment.soln.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assignment.soln.model.entity.PerformanceReview;

import com.assignment.soln.model.entity.Employee;
import java.util.List;

public interface PerformanceReviewRepository extends JpaRepository<PerformanceReview, Long> {
    List<PerformanceReview> findTop3ByEmployeeOrderByReviewDateDesc(Employee employee);
}
