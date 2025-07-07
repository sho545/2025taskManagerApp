package com.example.demo.application.controller;

import com.example.demo.application.dto.TaskDto;
import com.example.demo.application.dto.TaskRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.context.request.NativeWebRequest;

import jakarta.validation.constraints.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import jakarta.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-07-07T11:55:36.853454400+09:00[Asia/Tokyo]", comments = "Generator version: 7.14.0")
@RestController
@RequestMapping("${openapi.taskManagement.base-path:}")
public class TasksApiController implements TasksApi {

    private final NativeWebRequest request;

    @Autowired
    public TasksApiController(NativeWebRequest request) {
        this.request = request;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Override
    public ResponseEntity<List<TaskDto>> tasksGet(){

    }

    @Override
    public ResponseEntity<void> tasksIDelete(Long id){

    }

    @Override
    public ResponseEntity<TaskDto> tasksIdGet(Long id){

    }

    @Override
    public ResponseEntity<TaskDto> tasksIdPut(Long id, TaskRequest taskRequest){

    }

    @Override
    public ResponseEntity<TaskDto> tasksPost(TaskRequest taskRequest){
        
    }


}
