package com.assignment.soln.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assignment.soln.model.entity.PerformanceReview;

public interface PerformanceReviewRepository extends JpaRepository<PerformanceReview, Long> {
}
