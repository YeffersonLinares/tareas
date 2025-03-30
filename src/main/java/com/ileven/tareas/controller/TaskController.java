package com.ileven.tareas.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ileven.tareas.models.Task;
import com.ileven.tareas.repositories.TaskRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;
    
    // @GetMapping("tasks")
    // public List<Task> getAllTasks() {
    //     return taskRepository.findAll();
    // }
    
}
