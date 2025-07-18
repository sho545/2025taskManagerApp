// package com.example.demo.application.controller;

// import com.example.demo.generated.application.controller.TasksApi;
// import com.example.demo.generated.application.dto.TaskDto;
// import com.example.demo.generated.application.dto.TaskRequest;
// import com.example.demo.domain.model.Task;
// import com.example.demo.domain.service.TaskService;

// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.RestController;

// import java.time.ZoneId;
// import java.util.Date;
// import java.util.List;
// import java.util.stream.Collectors;

// @RestController
// public class TasksApiController implements TasksApi {

//     private final TaskService taskService;

//     public TasksApiController(TaskService taskService) {
//         this.taskService = taskService;
//     }

//     @Override
//     public ResponseEntity<List<TaskDto>> tasksGet(){
//         List<Task> tasks = taskService.findAll();
//         List<TaskDto> dtoList = tasks.stream().map(this::toDto).collect(Collectors.toList());
//         return ResponseEntity.ok(dtoList);
//     }

//     @Override
//     public ResponseEntity<Void> tasksIdDelete(Long id){
//         boolean isDeleted = taskService.delete(id) ;
//         return isDeleted ? ResponseEntity.noContent().build()
//                          : ResponseEntity.notFound().build();
//     }

//     @Override
//     public ResponseEntity<TaskDto> tasksIdGet(Long id){
//         return taskService.findById(id).map(this::toDto).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
//     }

//     //タスク更新
//     @Override
//     public ResponseEntity<TaskDto> tasksIdPut(Long id, TaskRequest taskRequest){
//         Task task = this.toEntity(taskRequest);
//         return taskService.update(id, task).map(this::toDto).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
//     }

//     //タスク作成
//     @Override
//     public ResponseEntity<TaskDto> tasksPost(TaskRequest taskRequest){
//         Task task = this.toEntity(taskRequest);
//         Task createdTask = taskService.create(task);
//         TaskDto dto = this.toDto(createdTask);
//         return new ResponseEntity<>(dto, HttpStatus.CREATED) ;
//     }

//     private TaskDto toDto(Task task){
//         TaskDto dto = new TaskDto() ;
//         dto.setId(task.getId()); 
//         dto.setTitle(task.getTitle());
//         dto.setDescription(task.getDescription());
//         dto.setCompleted(task.isCompleted());
//         if(task.getDueDate() != null){
//             dto.setDueDate(task.getDueDate().toInstant()
//                                 .atZone(ZoneId.systemDefault())
//                                 .toOffsetDateTime());
//         }
//         return dto ;
//     }

//     private Task toEntity(TaskRequest dto){
//         Task entity = new Task();
//         entity.setTitle(dto.getTitle());
//         entity.setDescription(dto.getDescription());
//         entity.setCompleted(dto.getCompleted());
//         if(dto.getDueDate() != null){
//             entity.setDueDate(Date.from(dto.getDueDate().toInstant()));
//         }
//         return entity;
//     }
// }

