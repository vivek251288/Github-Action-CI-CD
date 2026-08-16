package com.vivek.cicd.controller;


import com.vivek.cicd.entity.Task;
import com.vivek.cicd.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@Tag(name = "Task Management",description = "APIs for creating, reading, updating, deleting and searching tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }


    @Operation(summary = "Get all tasks",description = "Returns all tasks from the database")
    @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully")
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {

        return ResponseEntity.ok(
                taskService.getAllTasks()
        );
    }


    @Operation(summary = "Get task by ID", description = "Returns a specific task using its ID")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Task found"),
    @ApiResponse(responseCode = "404",description = "Task not found")})
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@Parameter( description = "Task ID", example = "1" ) @PathVariable Long id) {

        return ResponseEntity.ok(
                taskService.getTaskById(id)
        );
    }


    @Operation( summary = "Create a new task", description = "Creates a new task in the PostgreSQL database" )
    @ApiResponse( responseCode = "201", description = "Task created successfully")
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(taskService.createTask(task));
    }



    @Operation(summary = "Update task", description = "Updates an existing task")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "Task updated successfully" ),
            @ApiResponse(responseCode = "404",description = "Task not found") })
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(

            @Parameter(description = "Task ID",example = "1")
            @PathVariable Long id,
            @RequestBody Task task) {

        return ResponseEntity.ok( taskService.updateTask(id, task));
    }



    @Operation(summary = "Delete task",description = "Deletes a task from the database")
    @ApiResponses({@ApiResponse( responseCode = "204",description = "Task deleted successfully"),
    @ApiResponse( responseCode = "404",description = "Task not found")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@Parameter(description = "Task ID", example = "1" )@PathVariable Long id) {
          taskService.deleteTask(id);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Search tasks",description = "Searches tasks by title")
    @ApiResponse(responseCode = "200",description = "Search completed successfully")
    @GetMapping("/search")
    public ResponseEntity<List<Task>> search(@Parameter(description = "Task title to search",example = "Payment")@RequestParam String title) {

        return ResponseEntity.ok(taskService.searchTasks(title)
        );
    }


    @Operation( summary = "Get tasks by status", description = "Returns tasks filtered by status")
    @ApiResponse(responseCode = "200",description = "Tasks retrieved successfully")
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Task>> byStatus(@Parameter(description = "Task status",example = "PENDING") @PathVariable String status) {

        return ResponseEntity.ok(taskService.getTasksByStatus(status)
        );
    }
}