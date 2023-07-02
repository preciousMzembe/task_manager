package com.example.task_manager.service.impl;

import com.example.task_manager.dto.TaskDto;
import com.example.task_manager.models.TaskEntity;
import com.example.task_manager.models.UserEntity;
import com.example.task_manager.repository.TaskRepository;
import com.example.task_manager.repository.UserRepository;
import com.example.task_manager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private UserRepository userRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<TaskDto> getTasks() {
        Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if(principle instanceof UserDetails){
            username = ((UserDetails) principle).getUsername();
        }else {
            username = principle.toString();
        }
        UserEntity user = userRepository.findByUsername(username).orElseThrow(()->new RuntimeException("Username not found"));
        return taskRepository.findByUserId(user.getId()).stream().map(taskEntity -> mapToDto(taskEntity)).collect(Collectors.toList());
    }

    @Override
    public TaskDto addTask(TaskDto taskDto) {
        TaskEntity newTask = mapToEntity(taskDto);
        newTask.setUserId(2);
        newTask.setStatus(false);
        newTask.setCreatedAt(new Date());

        taskRepository.save(newTask);

        return mapToDto(newTask);
    }

    @Override
    public TaskDto updateTask(int taskId, TaskDto taskDto) {
        TaskEntity taskEntity = taskRepository.findById(taskId).orElseThrow(()->new RuntimeException("Task not found"));
        taskEntity.setName(taskDto.getName());

        taskRepository.save(taskEntity);
        return mapToDto(taskEntity);
    }

    @Override
    public String deleteTask(int taskId) {
        TaskEntity taskEntity = taskRepository.findById(taskId).orElseThrow(()-> new RuntimeException("Task not found"));
        taskRepository.delete(taskEntity);
        return "Task deleted";
    }

    @Override
    public TaskDto markTask(int taskId) {
        TaskEntity taskEntity = taskRepository.findById(taskId).orElseThrow(()-> new RuntimeException("Task not found"));
        taskEntity.setStatus(!taskEntity.isStatus());

        taskRepository.save(taskEntity);
        return mapToDto(taskEntity);
    }

    private TaskDto mapToDto(TaskEntity taskEntity){
        TaskDto taskDto = new TaskDto();
        taskDto.setId(taskEntity.getId());
        taskDto.setName(taskEntity.getName());
        taskDto.setStatus(taskEntity.isStatus());
        taskDto.setCreatedAt(taskEntity.getCreatedAt());

        return taskDto;
    }

    private TaskEntity mapToEntity(TaskDto taskDto){
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setName(taskDto.getName());
        taskEntity.setStatus(taskDto.isStatus());

        return taskEntity;
    }
}
