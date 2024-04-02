package com.example.demo1.service;

import com.example.demo1.dto.TodoRequest;
import com.example.demo1.entity.Todo;

import java.util.List;
import java.util.Optional;

public interface TodoService {
    List<Todo> getAll(String name);
    Todo findById(Long todoId);
    Todo save(Todo todo);
    Todo update(Long todoId, TodoRequest todoRequest);
    void delete(Long todoId);
}
