package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskControllerTestSuite {

    @InjectMocks
    private TaskController controller;

    @Mock
    private DbService service;

    @Mock
    private TaskMapper mapper;

    @Test
    public void testGetTask() throws TaskNotFoundException {
        //given
        Task task = new Task(3L, "task no 1", "task1 description");
        TaskDto taskDto = new TaskDto(3L, "task no 1", "task1 description");
        when(mapper.mapToTaskDto(task)).thenReturn(taskDto);
        when(service.getTask(3L)).thenReturn(task);

        //when
        ResponseEntity<TaskDto> resultTask = controller.getTask(3L);

        //then
        Assertions.assertEquals("task no 1",resultTask.getBody().getTitle());
        Assertions.assertEquals("200 OK",resultTask.getStatusCode().toString());
    }

}
