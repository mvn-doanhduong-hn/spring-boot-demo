package com.example.demo1.controller;

import com.example.demo1.dto.TodoRequest;
import com.example.demo1.entity.Todo;
import com.example.demo1.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/todos")
    public ResponseEntity<List<Todo>> getAll(@RequestParam(required = false) String name) {
        List<Todo> todos = todoService.getAll(name);
        if (todos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(todos, HttpStatus.OK);
    }

    @GetMapping("/todos/{id}")
    public ResponseEntity<Todo> getById(@PathVariable("id") long id) {
        Todo todo = todoService.findById(id);
        return new ResponseEntity<>(todo, HttpStatus.OK);
    }

    @PostMapping("/todos")
    public ResponseEntity<Todo> create(@Valid @RequestBody TodoRequest todoRequest) {
        Todo todo = todoService
                .save(new Todo(todoRequest.getName()));
        return new ResponseEntity<>(todo, HttpStatus.CREATED);
    }

    @PutMapping("/todos/{id}")
    public ResponseEntity<Todo> update(@PathVariable("id") long id, @Valid @RequestBody TodoRequest todoRequest) {
        Todo todo = todoService.update(id, todoRequest);
        return new ResponseEntity<>(todo, HttpStatus.OK);
    }

    @DeleteMapping("/todos/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") long id) {
        todoService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
