package com.ileven.tareas.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import com.ileven.tareas.dto.task.TaskRequestDTO;
import com.ileven.tareas.dto.task.TaskRequestDatatable;
import com.ileven.tareas.models.Task;
import com.ileven.tareas.services.TaskService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class TaskController {

    @Autowired
    private final TaskService taskService;

    @PersistenceContext
    private EntityManager entityManager;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("tasks")
    public ResponseEntity<Page<Task>> getPaginateTasks(@RequestBody @Valid TaskRequestDatatable request) {
        return ResponseEntity.ok(this.taskService.getPaginateTask(request));
        // return ResponseEntity.ok(this.taskService.getPaginateTask(request.getPage(), request.getSize()));
    }

    @PostMapping("task")
    public ResponseEntity<?> createTask(@RequestBody @Valid TaskRequestDTO request,
            BindingResult result) {
        try {
            List<String> errors = getValidationErrors(result);
            if (!errors.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of(
                        "status", 400,
                        "message", errors));
            }

            this.taskService.createTask(request);
            return ResponseEntity.status(200).body(Map.of(
                    "status", 200,
                    "message", "Task created successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "status", 500,
                    "message", "Error creating task: " + e.getMessage()));
        }
    }

    @PutMapping("task/{id}")
    public ResponseEntity<?> updateTask(@PathVariable String id, @RequestBody @Valid TaskRequestDTO request,
            BindingResult result) {
        try {
            List<String> errors = getValidationErrors(result);
            if (!errors.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of(
                        "status", 400,
                        "message", errors));
            }
            taskService.updateTask(Long.parseLong(id), request);
            return ResponseEntity.status(200).body(Map.of(
                    "status", 200,
                    "message", "Task updated successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "status", 500,
                    "message", "Error updating task: " + e.getMessage()));
        }
    }

    private List<String> getValidationErrors(BindingResult result) {
        return result.getAllErrors().stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());
    }

    @GetMapping("task/{id}")
    public ResponseEntity<?> getTask(@PathVariable String id) {
        try {
            Task task = taskService.getTaskById(Long.parseLong(id));
            return ResponseEntity.ok(task);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "status", 500,
                    "message", "Error retrieving task: " + e.getMessage()));
        }
    }

    @DeleteMapping("task/{id}")
    public ResponseEntity<Map<String, Object>> deleteTask(@PathVariable String id) {
        try {
            taskService.deleteTask(Long.parseLong(id));
            return ResponseEntity.status(200).body(Map.of(
                    "status", 200,
                    "message", "Task deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "status", 500,
                    "message", "Error deleting task: " + e.getMessage()));
        }
    }
}
