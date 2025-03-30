package com.ileven.tareas.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ileven.tareas.dto.task.TaskRequestDTO;
import com.ileven.tareas.dto.task.TaskRequestDatatable;
import com.ileven.tareas.models.Task;
import com.ileven.tareas.models.User;
import com.ileven.tareas.repositories.TaskRepository;
import com.ileven.tareas.repositories.UserRepository;
import com.ileven.tareas.specifitcations.TaskSpecification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public Page<Task> getPaginateTask(TaskRequestDatatable request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Specification<Task> specification = TaskSpecification.filterByCriteria(request);
        return taskRepository.findAll(specification, pageable);
    }

    private Task saveTask(Task task, TaskRequestDTO request) {
        task.setName(request.getName());
        task.setDescription(request.getDescription());
        if (request.getUserId() != null) {
            User user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            task.setUser(user);
        }
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, TaskRequestDTO request) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        return saveTask(task, request);
    }

    public Task createTask(TaskRequestDTO request) {
        Task task = new Task();
        return saveTask(task, request);
    }

    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        taskRepository.delete(task);
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }
}
