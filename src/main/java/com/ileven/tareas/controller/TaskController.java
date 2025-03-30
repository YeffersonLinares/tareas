package com.ileven.tareas.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ileven.tareas.models.Task;
import com.ileven.tareas.repositories.TaskRepository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;
    
    @GetMapping("tasks")
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
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
                "message", "Task created successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                "status", 500,
                "message", "Error creating task: " + e.getMessage()
            ));
        }
    }
}
