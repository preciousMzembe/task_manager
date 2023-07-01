package com.example.task_manager.service;

import com.example.task_manager.dto.TaskDto;

import java.util.List;

public interface TaskService {
    List<TaskDto> getTasks();
    TaskDto addTask(TaskDto taskDto);
    TaskDto updateTask(int taskId, TaskDto taskDto);
    String deleteTask(int taskId);
    TaskDto markTask(int taskId);
}
