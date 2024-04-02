package com.example.demo1.service.impl;

import com.example.demo1.dto.TodoRequest;
import com.example.demo1.entity.Todo;
import com.example.demo1.exception.ResourceNotFoundException;
import com.example.demo1.repository.TodoRepository;
import com.example.demo1.service.TodoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService  {
    private final TodoRepository todoRepository;

    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public List<Todo> getAll(String name) {
        List<Todo> todos;
        if (name == null) {
            todos = todoRepository.findAll();
        } else {
            todos = todoRepository.findByNameContaining(name);
        }
        return todos;
    }

    @Override
    public Todo findById(Long todoId) {
        return todoRepository.findById(todoId)
                .orElseThrow(() -> new ResourceNotFoundException("Todo " + todoId + " not found!"));
    }

    @Override
    public Todo save(Todo todo) {
        return todoRepository.save(todo);
    }

    @Override
    public Todo update(Long todoId, TodoRequest todoRequest) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new ResourceNotFoundException("Todo " + todoId + " not found!"));

        todo.setName(todoRequest.getName());
        return todoRepository.save(todo);
    }

    @Override
    public void delete(Long todoId) {
        todoRepository.deleteById(todoId);
    }
}
