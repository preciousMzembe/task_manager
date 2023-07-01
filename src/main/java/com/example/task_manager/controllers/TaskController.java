package com.example.task_manager.controllers;

import com.example.task_manager.dto.TaskDto;
import com.example.task_manager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todo/task/")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("all")
    public ResponseEntity<List<TaskDto>> getTasks(){
        return ResponseEntity.ok(taskService.getTasks());
    }

    @PostMapping("add")
    public ResponseEntity<TaskDto> addTask(@RequestBody TaskDto taskDto){
        return ResponseEntity.ok(taskService.addTask(taskDto));
    }

    @PutMapping("update/{taskId}")
    public ResponseEntity<TaskDto> updateTask(@RequestBody TaskDto taskDto, @PathVariable("taskId") int taskId){

        return ResponseEntity.ok(taskService.updateTask(taskId, taskDto));
    }

    @DeleteMapping("delete/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable("taskId") int taskId){
        taskService.deleteTask(taskId);
        return ResponseEntity.ok("task deleted");
    }

    @PutMapping("mark/{taskId}")
    public ResponseEntity<TaskDto> markTask(@PathVariable("taskId") int taskId){
        return ResponseEntity.ok(taskService.markTask(taskId));
    }
}
