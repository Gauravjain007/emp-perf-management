package com.assignment.soln.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.assignment.soln.model.dto.PerformanceReviewDTO;
import com.assignment.soln.model.entity.PerformanceReview;

@Mapper(componentModel = "spring")
public interface PerformanceReviewMapper {

    PerformanceReviewDTO toDTO(PerformanceReview performanceReview);

    @Mapping(target = "employee", ignore = true)
    PerformanceReview toEntity(PerformanceReviewDTO performanceReviewDTO);

    List<PerformanceReviewDTO> toDTOList(List<PerformanceReview> performanceReviews);

    List<PerformanceReview> toEntityList(List<PerformanceReviewDTO> performanceReviewDTOs);
}
