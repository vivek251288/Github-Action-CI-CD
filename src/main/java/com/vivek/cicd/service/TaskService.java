package com.vivek.cicd.service;


import com.vivek.cicd.entity.Task;
import com.vivek.cicd.repository.TaskRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {

        return taskRepository.findAll();
    }

    public Task getTaskById(Long id) {

        return taskRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Task not found: " + id));
    }

    public Task createTask(Task task) {

        if (task.getStatus() == null) {
            task.setStatus("PENDING");
        }

        if (task.getPriority() == null) {
            task.setPriority("MEDIUM");
        }

        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task task) {

        Task existingTask = getTaskById(id);

        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setStatus(task.getStatus());
        existingTask.setPriority(task.getPriority());

        return taskRepository.save(existingTask);
    }

    public void deleteTask(Long id) {

        if (!taskRepository.existsById(id)) {
            throw new RuntimeException("Task not found: " + id);
        }

        taskRepository.deleteById(id);
    }

    public List<Task> searchTasks(String title) {

        return taskRepository
                .findByTitleContainingIgnoreCase(title);
    }

    public List<Task> getTasksByStatus(String status) {

        return taskRepository.findByStatus(status);
    }
}
