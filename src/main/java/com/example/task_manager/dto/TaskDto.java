package com.example.task_manager.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TaskDto {
    int id;
    String name;
    boolean status;
    Date createdAt;
}
