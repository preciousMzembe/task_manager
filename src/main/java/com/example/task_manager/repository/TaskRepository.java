package com.example.task_manager.repository;

import com.example.task_manager.dto.TaskDto;
import com.example.task_manager.models.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<TaskEntity, Integer> {
    List<TaskEntity> findByUserId(int userId);
}
