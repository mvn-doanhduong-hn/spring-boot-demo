package com.example.demo1;

import com.example.demo1.dto.TodoRequest;
import com.example.demo1.entity.Todo;
import com.example.demo1.exception.ResourceNotFoundException;
import com.example.demo1.repository.TodoRepository;
import com.example.demo1.service.impl.TodoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TodoServiceUnitTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    TodoServiceImpl todoService;

    @Test
    void findByIdShouldReturnTodoWhenIdExists() {
        Todo todo = new Todo(1L, "Todo 1");
        when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));
        var actualTodo = todoService.findById(1L);
        assertEquals(todo, actualTodo);
        verify(todoRepository).findById(1L);
    }

    @Test
    void getByIdShouldThrowExceptionWhenIdDoesNotExist() {
        when(todoRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> todoService.findById(1L));
        verify(todoRepository).findById(1L);
    }

    @Test
    void createShouldReturnCreatedTodo() {
        Todo todo = new Todo(1L, "Todo 1");
        when(todoRepository.save(any(Todo.class))).thenReturn(todo);
        var actualTodo = todoService.save(todo);
        assertThat(actualTodo).isNotNull();
        assertEquals(todo, actualTodo);
        verify(todoRepository).save(any(Todo.class));
    }

    @Test
    void updateShouldReturnUpdatedTodo() {
        Todo todo = new Todo(1L, "Updated Todo");
        when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));
        when(todoRepository.save(any(Todo.class))).thenReturn(todo);
        var actualTodo = todoService.update(1L, new TodoRequest("Updated Todo"));
        assertThat(actualTodo).isNotNull();
        assertEquals(todo, actualTodo);
        verify(todoRepository).save(any(Todo.class));
    }

    @Test
    void updateShouldThrowExceptionUpdatedTodo() {
        when(todoRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () ->  todoService.update(1L, new TodoRequest("Updated Todo")));
        verify(todoRepository).findById(1L);
    }

    @Test
    void deleteShouldDeleteTodo() {
        doNothing().when(todoRepository).deleteById(1L);
        todoService.delete(1L);
        verify(todoRepository, times(1)).deleteById(1L);
    }
}
