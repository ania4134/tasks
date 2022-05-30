package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final DbService dbService;
    private final TaskMapper taskMapper;

    @GetMapping
    public List<TaskDto> getTasks() {
        List<Task> tasks = dbService.getAllTasks();
        return taskMapper.mapToTaskDtoList(tasks);
    }

    @GetMapping(value = "{taskId}")
    public TaskDto getTask(@PathVariable Long taskId) {
        Task task = dbService.getTask(taskId);
        return taskMapper.mapToTaskDto(task);
    }

    @DeleteMapping
    public void deleteTask(long taskId) {
    }

    @PutMapping
    public TaskDto updateTask(TaskDto task) {
        return new TaskDto(1L, "Edited test title", "Test content");
    }

    @PostMapping
    public void createTask(TaskDto task) {
    }
}
