package com.example.demo.application.controller;

import com.example.demo.application.dto.TaskDto;
import com.example.demo.application.form.TaskRequest;
import com.example.demo.application.helper.ApiMapper;
import com.example.demo.domain.model.Task;
import com.example.demo.domain.service.TaskService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-07-07T11:55:36.853454400+09:00[Asia/Tokyo]", comments = "Generator version: 7.14.0")
@RestController
@RequestMapping("${openapi.taskManagement.base-path:}")
public class TasksApiController implements TasksApi {

    private final TaskService taskService;

    public TasksApiController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public ResponseEntity<List<TaskDto>> tasksGet(){
        List<Task> tasks = taskService.findAll();
        List<TaskDto> dtoList = tasks.stream().map(ApiMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    @Override
    public ResponseEntity<Void> tasksIdDelete(Long id){
        boolean isDeleted = taskService.delete(id) ;
        return isDeleted ? ResponseEntity.noContent().build()
                         : ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<TaskDto> tasksIdGet(Long id){
        return taskService.findById(id).map(ApiMapper::toDto).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    //タスク更新
    @Override
    public ResponseEntity<TaskDto> tasksIdPut(Long id, TaskRequest taskRequest){
        Task task = ApiMapper.toEntity(taskRequest);
        return taskService.update(id, task).map(ApiMapper::toDto).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    //タスク作成
    @Override
    public ResponseEntity<TaskDto> tasksPost(TaskRequest taskRequest){
        Task task = ApiMapper.toEntity(taskRequest);
        Task createdTask = taskService.create(task);
        TaskDto dto = ApiMapper.toDto(createdTask);
        return new ResponseEntity<>(dto, HttpStatus.CREATED) ;
    }


}
