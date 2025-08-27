package com.example.demo.application.controller;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.domain.model.Task;
import com.example.demo.domain.service.TaskService;
import com.example.demo.generated.application.dto.TaskDto;
import com.example.demo.generated.application.dto.TaskRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TasksApiControllerTest {

  @Mock
  private TaskService taskService; // Serviceをモック化

  @InjectMocks
  private TasksApiController tasksApiController; // テスト対象のController

  @Test // tasksGetメソッドのテスト
  void tasksGet_shouldReturnListOfTaskDto() {
    // 1. Given (準備): テストの前提条件を設定
    // モックの動作を定義する
    Mockito.when(taskService.findAll()).thenReturn(List.of(
        new Task(1L, "Task 1", "説明", false, OffsetDateTime.now()),
        new Task(2L, "Task 2", "", true, OffsetDateTime.now())));

    // 2. When (実行): テスト対象のメソッドを呼び出す
    ResponseEntity<List<TaskDto>> response = tasksApiController.tasksGet();

    // 3. Then (検証): 実行結果が期待通りか検証
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).hasSize(2);
    assertThat(response.getBody().get(0).getTitle()).isEqualTo("Task 1");
    assertThat(response.getBody().get(1).getTitle()).isEqualTo("Task 2");
    verify(taskService).findAll();
  }

  @Test // taskIdDeleteメソッドのテスト
  void tasksIdDelete_shouldReturnNoContent() {
    // 1. Given (準備): テストの前提条件を設定
    Long taskId = 1L;

    // 2. When (実行): テスト対象のメソッドを呼び出す
    ResponseEntity<Void> response = tasksApiController.tasksIdDelete(taskId);

    // 3. Then (検証): 実行結果が期待通りか検証
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    verify(taskService).delete(taskId);
  }

  @Test // handleNotFoundメソッドのテスト
  void handleNotFound_shouldReturnNotFound() {
    // 1. Given (準備): テストの前提条件を設定
    // ResourceNotFoundExceptionを発生させる

    // 2. When (実行): テスト対象のメソッドを呼び出す
    ResponseEntity<Object> response = tasksApiController.handleNotFound();

    // 3. Then (検証): 実行結果が期待通りか検証
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  @Test // handleBadRequestメソッドのテスト
  void handleBadRequest_shouldReturnBadRequest() {
    // 1. Given (準備): テストの前提条件を設定
    IllegalArgumentException exception = new IllegalArgumentException("Invalid argument");

    // 2. When (実行): テスト対象のメソッドを呼び出す
    ResponseEntity<Map<String, String>> response = tasksApiController.handleBadRequest(exception);

    // 3. Then (検証): 実行結果が期待通りか検証
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody()).containsEntry("message", "Invalid argument");
  }

  @Test // tasksIdGetメソッドのテスト
  void tasksIdGet_shouldReturnTaskDto() {
    // 1. Given (準備): テストの前提条件を設定
    Long taskId = 1L;
    Task task = new Task(taskId, "Task 1", "説明", false, OffsetDateTime.now());
    Mockito.when(taskService.findById(taskId)).thenReturn(task);

    // 2. When (実行): テスト対象のメソッドを呼び出す
    ResponseEntity<TaskDto> response = tasksApiController.tasksIdGet(taskId);

    // 3. Then (検証): 実行結果が期待通りか検証
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody().getTitle()).isEqualTo("Task 1");
    verify(taskService).findById(taskId);
  }

  @Test // tasksIdPutメソッドのテスト
  void tasksIdPut_shouldReturnUpdatedTaskDto() {
    // 1. Given (準備): テストの前提条件を設定
    Long taskId = 1L;
    TaskRequest taskRequest = new TaskRequest();
    taskRequest.setTitle("Updated Task");
    taskRequest.setDescription("説明更新");
    taskRequest.setDueDate(OffsetDateTime.now());
    Task updatedTask = new Task(taskId, "Updated Task", "説明更新", false, OffsetDateTime.now());
    Mockito.when(taskService.update(eq(taskId), Mockito.any(Task.class))).thenReturn(updatedTask);

    // 2. When (実行): テスト対象のメソッドを呼び出す
    ResponseEntity<TaskDto> response = tasksApiController.tasksIdPut(taskId, taskRequest);

    // 3. Then (検証): 実行結果が期待通りか検証
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody().getTitle()).isEqualTo("Updated Task");
    verify(taskService).update(eq(taskId), Mockito.any(Task.class));
  }

  @Test // tasksPostメソッドのテスト
  void tasksPost_shouldReturnCreatedTaskDto() {
    // 1. Given (準備): テストの前提条件を設定
    TaskRequest taskRequest = new TaskRequest();
    taskRequest.setTitle("New Task");
    taskRequest.setDescription("説明");
    taskRequest.setDueDate(OffsetDateTime.now());
    Task createdTask = new Task(1L, "New Task", "説明", false, OffsetDateTime.now());
    Mockito.when(taskService.create(Mockito.any(Task.class))).thenReturn(createdTask);

    // 2. When (実行): テスト対象のメソッドを呼び出す
    ResponseEntity<TaskDto> response = tasksApiController.tasksPost(taskRequest);

    // 3. Then (検証): 実行結果が期待通りか検証
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    assertThat(response.getBody().getTitle()).isEqualTo("New Task");
    verify(taskService).create(Mockito.any(Task.class));
  }

  @Test // toDtoメソッドのテスト
  void toDto_shouldConvertTaskToTaskDto() {
    // 1. Given (準備): テストの前提条件を設定
    OffsetDateTime now = OffsetDateTime.now();
    Task task = new Task(1L, "Task 1", "説明", false, now);

    // 2. When (実行): テスト対象のメソッドを呼び出す
    TaskDto dto = tasksApiController.toDto(task);

    // 3. Then (検証): 実行結果が期待通りか検証
    assertThat(dto.getId()).isEqualTo(1L);
    assertThat(dto.getTitle()).isEqualTo("Task 1");
    assertThat(dto.getDescription()).isEqualTo("説明");
    assertThat(dto.getCompleted()).isFalse();
    assertThat(dto.getDueDate()).isEqualTo(now);
  }

  @Test // toModelメソッドのテスト
  void toModel_shouldConvertTaskDtoToTask() {
    // 1. Given (準備): テストの前提条件を設定
    TaskRequest dto = new TaskRequest();
    OffsetDateTime now = OffsetDateTime.now();
    dto.setTitle("Task 1");
    dto.setDescription("説明");
    dto.setCompleted(false);
    dto.setDueDate(now);

    // 2. When (実行): テスト対象のメソッドを呼び出す
    Task task = tasksApiController.toModel(dto);

    // 3. Then (検証): 実行結果が期待通りか検証
    assertThat(task.getId()).isNull();
    assertThat(task.getTitle()).isEqualTo("Task 1");
    assertThat(task.getDescription()).isEqualTo("説明");
    assertThat(task.isCompleted()).isFalse();
    assertThat(task.getDueDate()).isEqualTo(now);
  }

  @Test // descriptionがnullの場合のtoModelメソッドのテスト
  void toModel_shouldHandleNullDescription() {
    // 1. Given (準備): テストの前提条件を設定
    TaskRequest dto = new TaskRequest();
    OffsetDateTime now = OffsetDateTime.now();
    dto.setTitle("Task 1");
    dto.setCompleted(false);
    dto.setDueDate(now);

    // 2. When (実行): テスト対象のメソッドを呼び出す
    Task task = tasksApiController.toModel(dto);

    // 3. Then (検証): 実行結果が期待通りか検証
    assertThat(task.getDescription()).isNull();
    assertThat(task.getTitle()).isEqualTo("Task 1");
    assertThat(task.isCompleted()).isFalse();
    assertThat(task.getDueDate()).isEqualTo(now);
  }

  @Test // completedがnullの場合のtoModelメソッドのテスト
  void toModel_shouldHandleNullCompleted() {
    // 1. Given (準備): テストの前提条件を設定
    TaskRequest dto = new TaskRequest();
    OffsetDateTime now = OffsetDateTime.now();
    dto.setTitle("Task 1");
    dto.setDescription("説明");
    dto.setDueDate(now);

    // 2. When (実行): テスト対象のメソッドを呼び出す
    Task task = tasksApiController.toModel(dto);

    // 3. Then (検証): 実行結果が期待通りか検証
    assertThat(task.isCompleted()).isFalse(); // デフォルト値はfalse
    assertThat(task.getTitle()).isEqualTo("Task 1");
    assertThat(task.getDescription()).isEqualTo("説明");
    assertThat(task.getDueDate()).isEqualTo(now);
  }

}
