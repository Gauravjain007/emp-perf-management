package com.assignment.soln.specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.assignment.soln.model.entity.Department;
import com.assignment.soln.model.entity.Employee;
import com.assignment.soln.model.entity.EmployeeProject;
import com.assignment.soln.model.entity.PerformanceReview;
import com.assignment.soln.model.entity.Project;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;

@Component
public class EmployeeSpecification {

    private EmployeeSpecification() {
    }

    public static Specification<Employee> filterEmployees(
            Integer score,
            LocalDate reviewDate,
            List<String> departments,
            List<String> projects) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (score != null || reviewDate != null) {
                Join<Employee, PerformanceReview> reviewJoin = root.join("reviews");
                if (score != null) {
                    predicates.add(cb.equal(reviewJoin.get("score"), score));
                }
                if (reviewDate != null) {
                    predicates.add(cb.equal(reviewJoin.get("reviewDate"), reviewDate));
                }
            }

            if (departments != null && !departments.isEmpty()) {
                Join<Employee, Department> deptJoin = root.join("department");
                predicates.add(deptJoin.get("name").in(departments));
            }

            if (projects != null && !projects.isEmpty()) {
                Join<Employee, EmployeeProject> empProjectJoin = root.join("projects");
                Join<EmployeeProject, Project> projectJoin = empProjectJoin.join("project");
                predicates.add(projectJoin.get("name").in(projects));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
