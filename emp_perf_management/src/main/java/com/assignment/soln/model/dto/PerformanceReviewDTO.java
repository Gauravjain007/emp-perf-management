package com.assignment.soln.model.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PerformanceReviewDTO {
    private Integer id;
    private LocalDate reviewDate;
    private Integer score;
    private String reviewComments;
}