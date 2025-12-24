package com.assignment.soln.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assignment.soln.model.entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

}
