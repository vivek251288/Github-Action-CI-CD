package com.vivek.cicd.controller;


import com.vivek.cicd.entity.Task;
import com.vivek.cicd.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // GET all tasks
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {

        return ResponseEntity.ok(
                taskService.getAllTasks()
        );
    }

    // GET task by ID
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Long id) {

        return ResponseEntity.ok(
                taskService.getTaskById(id)
        );
    }

    // CREATE task
    @PostMapping
    public ResponseEntity<Task> createTask(
            @RequestBody Task task) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(taskService.createTask(task));
    }

    // UPDATE task
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(
            @PathVariable Long id,
            @RequestBody Task task) {

        return ResponseEntity.ok(
                taskService.updateTask(id, task)
        );
    }

    // DELETE task
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable Long id) {

        taskService.deleteTask(id);

        return ResponseEntity.noContent().build();
    }

    // SEARCH
    @GetMapping("/search")
    public ResponseEntity<List<Task>> search(
            @RequestParam String title) {

        return ResponseEntity.ok(
                taskService.searchTasks(title)
        );
    }

    // FILTER BY STATUS
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Task>> byStatus(
            @PathVariable String status) {

        return ResponseEntity.ok(
                taskService.getTasksByStatus(status)
        );
    }
}