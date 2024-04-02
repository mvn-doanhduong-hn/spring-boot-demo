package com.example.demo1;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo1.controller.TodoController;
import com.example.demo1.dto.TodoRequest;
import com.example.demo1.entity.Todo;
import com.example.demo1.exception.ResourceNotFoundException;
import com.example.demo1.service.TodoService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(TodoController.class)
class TodoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoService service;

    @Test
    void getAllShouldReturnTodosFromServiceWhenNameIsNotNull() throws Exception {
        List<Todo> todos = List.of(
                new Todo(1L, "Todo 1"),
                new Todo(2L, "Todo 2"),
                new Todo(3L, "Todo 3")
        );
        when(service.getAll("Todo")).thenReturn(todos);
        this.mockMvc.perform(get("/todos").param("name", "Todo")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Todo 1")))
                .andExpect(content().string(containsString("Todo 2")))
                .andExpect(content().string(containsString("Todo 3")));
    }

    @Test
    void getAllShouldReturnNoContentWhenNoTodosExist() throws Exception {
        when(service.getAll(null)).thenReturn(new ArrayList<>());
        this.mockMvc.perform(get("/todos")).andDo(print()).andExpect(status().isNoContent());
    }

    @Test
    void getAllShouldReturnTodosFromServiceWhenNameIsNull() throws Exception {
        List<Todo> todos = List.of(
                new Todo(1L, "Todo 1"),
                new Todo(2L, "Todo 2"),
                new Todo(3L, "Todo 3")
        );
        when(service.getAll(null)).thenReturn(todos);
        this.mockMvc.perform(get("/todos")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Todo 1")))
                .andExpect(content().string(containsString("Todo 2")))
                .andExpect(content().string(containsString("Todo 3")));
    }

    @Test
    void getByIdShouldReturnTodoWhenIdExists() throws Exception {
        Todo todo = new Todo(1L, "Todo 1");
        when(service.findById(1L)).thenReturn(todo);
        this.mockMvc.perform(get("/todos/1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Todo 1")));
    }

    @Test
    void getByIdShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        when(service.findById(1L)).thenThrow(new ResourceNotFoundException("Todo 1 not found!"));
        this.mockMvc.perform(get("/todos/1")).andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    void createShouldReturnCreatedTodo() throws Exception {
        Todo todo = new Todo(1L, "Todo 1");
        when(service.save(any(Todo.class))).thenReturn(todo);
        this.mockMvc.perform(post("/todos").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Todo 1\"}")).andDo(print()).andExpect(status().isCreated())
                .andExpect(content().string(containsString("Todo 1")));
    }

    @Test
    void updateShouldReturnUpdatedTodo() throws Exception {
        Todo todo = new Todo(1L, "Updated Todo");
        when(service.update(eq(1L), any(TodoRequest.class))).thenReturn(todo);
        this.mockMvc.perform(put("/todos/1").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Todo\"}")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Updated Todo")));
    }

    @Test
    void deleteShouldReturnNoContent() throws Exception {
        doNothing().when(service).delete(1L);
        this.mockMvc.perform(delete("/todos/1")).andDo(print()).andExpect(status().isNoContent());
    }
}