package com.assignment.soln.model.entity;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "employee_project")
@Data
public class EmployeeProject {
    @EmbeddedId
    private EmployeeProjectId id;

    @ManyToOne
    @MapsId("employeeId")
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @MapsId("projectId")
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(name = "assigned_date")
    private LocalDate assignedDate;

    @Column(name = "role")
    private String role;

    @Embeddable
    @Data
    public static class EmployeeProjectId implements Serializable {
        @Column(name = "employee_id", nullable = false)
        private int employeeId;

        @Column(name = "project_id", nullable = false)
        private int projectId;
    }
}
