package com.example.demo.application.controller;

import com.example.demo.generated.application.controller.TasksApi;
import com.example.demo.generated.application.dto.TaskDto;
import com.example.demo.generated.application.dto.TaskRequest;

import com.example.demo.domain.model.Task;
import com.example.demo.domain.service.TaskService;
import com.example.demo.exception.ResourceNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Validated
@CrossOrigin(origins = "http://localhost:5173")
public class TasksApiController implements TasksApi {

    private final TaskService taskService;

    public TasksApiController(TaskService taskService) {
        this.taskService = taskService;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleNotFound() {
        // 404 Not Found レスポンスを返す
        return ResponseEntity.notFound().build();
    }

    // 400 Bad Request を返すハンドラーを追加
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleBadRequest(IllegalArgumentException ex) {
        // エラーメッセージをレスポンスボディに含めて返す
        Map<String, String> errorBody = Map.of("message", ex.getMessage());
        return new ResponseEntity<>(errorBody, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<TaskDto>> tasksGet() {
        List<Task> tasks = taskService.findAll();
        List<TaskDto> dtoList = tasks.stream().map(this::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    @Override
    public ResponseEntity<Void> tasksIdDelete(Long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<TaskDto> tasksIdGet(Long id) {
        Task task = taskService.findById(id);
        TaskDto dto = toDto(task);
        return ResponseEntity.ok(dto);
    }

    // タスク更新
    @Override
    public ResponseEntity<TaskDto> tasksIdPut(Long id, TaskRequest taskRequest) {
        Task task = toModel(taskRequest);
        TaskDto dto = toDto(taskService.update(id, task));
        return ResponseEntity.ok(dto);
    }

    // タスク作成
    @Override
    public ResponseEntity<TaskDto> tasksPost(TaskRequest taskRequest) {
        Task task = this.toModel(taskRequest);
        Task createdTask = taskService.create(task);
        TaskDto dto = this.toDto(createdTask);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    TaskDto toDto(Task task) {
        TaskDto dto = new TaskDto();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setCompleted(task.isCompleted());
        if (task.getDueDate() != null) {
            dto.setDueDate(task.getDueDate());
        }
        return dto;
    }

    public Task toModel(TaskRequest dto) {
        Task model = new Task();
        model.setTitle(dto.getTitle());
        model.setDescription(dto.getDescription());
        model.setCompleted(dto.getCompleted() != null ? dto.getCompleted() : false);
        if (dto.getDueDate() != null) {
            model.setDueDate(dto.getDueDate());
        }
        return model;
    }
}
