package com.vivek.cicd.repository;



import com.vivek.cicd.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByStatus(String status);

    List<Task> findByTitleContainingIgnoreCase(String title);
}