package com.ileven.tareas.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.ileven.tareas.models.Task;
import com.ileven.tareas.repositories.TaskRepository;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("tasks")
    public Page<Task> getPaginateTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return taskRepository.findAll(pageable);
    }

    @PostMapping("task")
    public ResponseEntity<Map<String, Object>> createTask(@RequestBody Task task) {
        try {
            Task newTask = new Task();
            newTask.setName(task.getName());
            newTask.setDescription(task.getDescription());
            taskRepository.save(newTask);
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
    public ResponseEntity<Map<String, Object>> updateTask(@PathVariable String id, @RequestBody Task entity) {
        try {
            Task task = taskRepository.findById(Long.parseLong(id)).orElse(null);
            if (task == null) {
                return ResponseEntity.status(404).body(Map.of(
                        "status", 404,
                        "message", "Task not found"));
            }

            task.setName(entity.getName());
            task.setDescription(entity.getDescription());
            taskRepository.save(task);
            return ResponseEntity.status(200).body(Map.of(
                    "status", 200,
                    "message", "Task updated successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "status", 500,
                    "message", "Error updating task: " + e.getMessage()));
        }
    }

    @DeleteMapping("task/{id}")
    public ResponseEntity<Map<String, Object>> deleteTask(@PathVariable String id) {
        try {
            Task task = taskRepository.findById(Long.parseLong(id)).orElse(null);
            if (task == null) {
                return ResponseEntity.status(404).body(Map.of(
                        "status", 404,
                        "message", "Task not found"));
            }

            taskRepository.delete(task);
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
