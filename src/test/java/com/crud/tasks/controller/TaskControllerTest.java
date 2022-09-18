package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService service;

    @MockBean
    private TaskMapper mapper;

    @Test
    void shouldFetchTasks() throws Exception {
        //Given
        List<TaskDto> tasksDto = new ArrayList<>();
        List<Task> tasks = new ArrayList<>();
        TaskDto taskDto = new TaskDto(424L, "Task 1", "Task content");
        Task task = new Task(424L, "Task 1", "Task content");
        tasksDto.add(taskDto);
        tasks.add(task);
        when(service.getAllTasks()).thenReturn(tasks);
        when(mapper.mapToTaskDtoList(tasks)).thenReturn(tasksDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(424)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title", Matchers.is("Task 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].content", Matchers.is("Task content")));
    }

    @Test
    void shouldFetchTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(424L, "Task 1", "Task content");
        Task task = new Task(424L, "Task 1", "Task content");
        when(mapper.mapToTaskDto(task)).thenReturn(taskDto);
        when(service.getTask(424L)).thenReturn(task);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks/424")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(424)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("Task 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("Task content")));
    }

    @Test
    void deleteTask() throws Exception {
        //Given
        Task task = new Task(424L, "Task 1", "Task content");
        TaskDto taskDto = new TaskDto(424L, "Task 1", "Task content");
        when(mapper.mapToTask(taskDto)).thenReturn(task);
        when(service.saveTask(any(Task.class))).thenReturn(task);
        doNothing().when(service).deleteTask(any(Long.class));

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/v1/tasks/424")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void shouldCreateTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(424L, "Task 1", "Task content");
        Task task = new Task(424L, "Task 1", "Task content");
        when(mapper.mapToTask(taskDto)).thenReturn(task);
        when(service.saveTask(any(Task.class))).thenReturn(task);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void shouldUpdateTask() throws Exception {
        //Given
        Task task = new Task(424L, "updated task 1", "updated task content");
        TaskDto taskDto = new TaskDto(424L, "updated task 1", "updated task content");
        TaskDto savedTaskDto = new TaskDto(424L, "updated task 1", "updated task content");
        Task savedTask = new Task(424L, "updated task 1", "updated task content");
        when(mapper.mapToTask(taskDto)).thenReturn(task);
        when(service.saveTask(any(Task.class))).thenReturn(savedTask);
        when(mapper.mapToTaskDto(savedTask)).thenReturn(savedTaskDto);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(savedTaskDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(424)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("updated task 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("updated task content")));
    }
}