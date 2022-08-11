package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TaskMapperTestSuite {

    @Test
    public void testMapToTask() {
        //given
        TaskDto taskDto1 = new TaskDto(123L, "task no 1", "content of task");

        //when
        TaskMapper mapper = new TaskMapper();
        Task resultTask = mapper.mapToTask(taskDto1);

        //then
        Assertions.assertEquals("task no 1", resultTask.getTitle());
        Assertions.assertEquals("content of task", resultTask.getContent());
    }

    @Test
    public void testMapToTaskDto() {
        //given
        Task task1 = new Task(123L, "task no 1", "content of task");

        //when
        TaskMapper mapper = new TaskMapper();
        TaskDto resultTask = mapper.mapToTaskDto(task1);

        //then
        Assertions.assertEquals("task no 1", resultTask.getTitle());
        Assertions.assertEquals("content of task", resultTask.getContent());
    }

    @Test
    public void testMapToTaskDtoList() {
        //given
        List<Task> taskList = new ArrayList<>();
        Task task1 = new Task(123L, "task no 1", "content of task no 1");
        Task task2 = new Task(124L, "task no 2", "content of task no 2");
        taskList.add(task1);
        taskList.add(task2);

        //when
        TaskMapper mapper = new TaskMapper();
        List<TaskDto> resultTaskList = mapper.mapToTaskDtoList(taskList);

        //then
        Assertions.assertEquals(2, resultTaskList.size());
        Assertions.assertEquals("content of task no 2", resultTaskList.get(1).getContent());
    }
}
