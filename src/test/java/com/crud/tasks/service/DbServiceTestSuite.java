package com.crud.tasks.service;

import com.crud.tasks.controller.TaskNotFoundException;
import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class DbServiceTestSuite {

    @InjectMocks
    private DbService service;

    @Mock
    private TaskRepository repository;

    @Test
    public void testGetTask() throws TaskNotFoundException {
        //given
        Task task1 = new Task(3L, "task no 1", "task1 description");
        when(repository.findById(3L)).thenReturn(Optional.of(task1));

        //when
        Task resultTask = service.getTask(3L);

        //then
        assertEquals(3L, resultTask.getId());
        assertEquals("task no 1", resultTask.getTitle());
    }

    @Test
    public void testFindAllTasks() {
        //given
        List<Task> list = new ArrayList<>();
        Task task1 = new Task(3L, "task no 1", "task1 description");
        Task task2 = new Task(5L, "task no 2", "task2  description");
        Task task3 = new Task(7L, "task no 3", "task3  description");
        list.add(task1);
        list.add(task2);
        list.add(task3);

        when(repository.findAll()).thenReturn(list);

        //when
        List<Task> resultList = service.getAllTasks();

        //then
        assertEquals(3, resultList.size());
    }

    @Test
    public void testSaveTask() {
        //given
        Task task1 = new Task(3L, "task no 1", "task1 description");

        when(repository.save(task1)).thenReturn(task1);

        //when
        Task resultTask = service.saveTask(task1);

        //then
        assertEquals(task1, resultTask);
    }

    @Test
    public void testDeleteTask() throws TaskNotFoundException {
        //given
        Task task1 = new Task(3L, "task no 1", "task1 description");
        when(repository.existsById(3L)).thenReturn(true);
        Mockito.doNothing().when(repository).deleteById(3L);

        //when
        service.saveTask(task1);
        service.deleteTask(3L);

        //then
        assertEquals(0, service.getAllTasks().size());
    }
}
