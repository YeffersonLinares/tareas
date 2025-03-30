package com.ileven.tareas.specifitcations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.ileven.tareas.dto.task.TaskRequestDatatable;
import com.ileven.tareas.models.Task;

import jakarta.persistence.criteria.Predicate;

public class TaskSpecification {
    public static Specification<Task> filterByCriteria(TaskRequestDatatable request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.getName() != null && !request.getName().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + request.getName() + "%"));
            }

            if (request.getUserId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("user").get("id"), request.getUserId()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
