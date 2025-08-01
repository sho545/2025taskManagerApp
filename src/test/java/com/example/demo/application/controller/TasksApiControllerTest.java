package com.example.demo.application.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.hasSize;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.domain.model.Task;
import com.example.demo.domain.service.TaskService;
import com.example.demo.exception.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(TasksApiController.class)
public class TasksApiControllerTest {

  @Autowired
  private MockMvc mockMvc; // HTTPリクエストをモックするためのオブジェクト

  @Autowired
  private ObjectMapper objectMapper; // JavaオブジェクトとJSONの変換を行うためのオブジェクト

  @MockitoBean
  private TaskService taskService; // TaskServiceのモック

  @Test // タスクの一覧取得のテスト
  void tasksGet_shouldReturnListOfTasks() throws Exception {
    // 1. Given (準備): モックの設定
    Task task1 = new Task();
    task1.setId(1L);
    task1.setTitle("Task 1");
    task1.setDueDate(OffsetDateTime.of(2025, 8, 1, 10, 0, 0, 0, ZoneOffset.ofHours(9)));

    Task task2 = new Task();
    task2.setId(2L);
    task2.setTitle("Task 2");
    task2.setDueDate(OffsetDateTime.of(2025, 8, 2, 10, 0, 0, 0, ZoneOffset.ofHours(9)));

    when(taskService.findAll()).thenReturn(List.of(task1, task2));

    // 2. When (実行): HTTP GETリクエストを送信
    mockMvc.perform(get("/tasks"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].title").value("Task 1"))
        .andExpect(jsonPath("$[1].title").value("Task 2"));

    // 3. Then (検証): モックの呼び出しを検証
    verify(taskService).findAll();
  }

  @Test // タスクが空の時のタスク取得テスト
  void tasksGet_shouldReturnEmptyList_whenNoTasksExist() throws Exception {
    // 1. Given (準備): モックの設定
    when(taskService.findAll()).thenReturn(List.of());

    // 2. When (実行): HTTP GETリクエストを送信
    mockMvc.perform(get("/tasks"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$", hasSize(0)));

    // 3. Then (検証): モックの呼び出しを検証
    verify(taskService).findAll();
  }

  @Test // タスク取得のテスト
  void tasksIdGet_shouldReturnTaskById() throws Exception {
    // 1. Given (準備): モックの設定
    Task task = new Task();
    task.setId(1L);
    task.setTitle("Task 1");
    task.setDueDate(OffsetDateTime.of(2025, 8, 1, 10, 0, 0, 0, ZoneOffset.ofHours(9)));

    when(taskService.findById(1L)).thenReturn(task);

    // 2. When (実行): HTTP GETリクエストを送信
    mockMvc.perform(get("/tasks/1"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.title").value("Task 1"));

    // 3. Then (検証): モックの呼び出しを検証
    verify(taskService).findById(1L);
  }

  @Test // タスク作成のテスト
  void tasksPost_shouldCreateTask() throws Exception {
    // 1. Given (準備): モックの設定
    Task task = new Task();
    task.setId(1L);
    task.setTitle("12345678901234567890");
    task.setDueDate(OffsetDateTime.of(2025, 8, 3, 10, 0, 0, 0, ZoneOffset.ofHours(9)));

    when(taskService.create(any(Task.class))).thenReturn(task);

    // タスクリクエストのJSONを作成
    String taskRequestJson = objectMapper.writeValueAsString(task);

    // 2. When (実行): HTTP POSTリクエストを送信
    mockMvc.perform(post("/tasks")
        .contentType(MediaType.APPLICATION_JSON)
        .content(taskRequestJson))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.title").value("12345678901234567890"));

    // 3. Then (検証): モックの呼び出しを検証
    verify(taskService).create(any(Task.class));
  }

  @Test // タスク更新のテスト
  void tasksIdPut_shouldUpdateTask() throws Exception {
    // 1. Given (準備): モックの設定
    Task task = new Task();
    task.setId(1L);
    task.setTitle("Updated Task");
    task.setDueDate(OffsetDateTime.of(2025, 8, 4, 10, 0, 0, 0, ZoneOffset.ofHours(9)));

    when(taskService.update(eq(1L), any(Task.class))).thenReturn(task);

    // タスクリクエストのJSONを作成
    String taskRequestJson = objectMapper.writeValueAsString(task);

    // 2. When (実行): HTTP PUTリクエストを送信
    mockMvc.perform(put("/tasks/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(taskRequestJson))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.title").value("Updated Task"));

    // 3. Then (検証): モックの呼び出しを検証
    verify(taskService).update(eq(1L), any(Task.class));
  }

  @Test // タスク削除のテスト
  void tasksIdDelete_shouldDeleteTask() throws Exception {
    // 1. Given (準備): モックの設定
    doNothing().when(taskService).delete(1L);

    // 2. When (実行): HTTP DELETEリクエストを送信
    mockMvc.perform(delete("/tasks/1"))
        .andExpect(status().isNoContent());

    // 3. Then (検証): モックの呼び出しを検証
    verify(taskService).delete(1L);
  }

  // 失敗するテストケース

  @Test // idが見つからなかったときのタスク取得のテスト
  void tasksIdGet_shouldReturnNotFound_whenTaskDoesNotExist() throws Exception {
    // 1. Given (準備): モックの設定
    when(taskService.findById(999L)).thenThrow(new ResourceNotFoundException("id999のタスクは存在しません"));

    // 2. When (実行): HTTP GETリクエストを送信
    mockMvc.perform(get("/tasks/999"))
        .andExpect(status().isNotFound());

    // 3. Then (検証): モックの呼び出しを検証
    verify(taskService).findById(999L);
  }

  @Test // タスク作成でタイトルが空の場合のテスト
  void tasksPost_shouldReturnBadRequest_whenTitleIsEmpty() throws Exception {
    // 1. Given: titleが空（minLength: 1違反）だが、他の必須項目は有効なJSON
    String invalidJson = """
        {
            "title": "",
            "description": "有効な説明",
            "completed": false,
            "dueDate": "2025-08-01T12:00:00+09:00"
        }
        """;

    // 2. When & Then: POSTリクエストを送信し、400 Bad Requestが返ることを確認
    mockMvc.perform(post("/tasks")
        .contentType(MediaType.APPLICATION_JSON)
        .content(invalidJson))
        .andExpect(status().isBadRequest());

    // 3. Then: バリデーションで弾かれるため、Serviceのcreateメソッドは呼ばれない
    verify(taskService, never()).create(any(Task.class));
  }

  @Test // タスク作成でタイトルがない場合のテスト
  void tasksPost_shouldReturnBadRequest_whenTitleIsMissing() throws Exception {
    // 1. Given: 必須項目であるtitleが含まれていないJSON
    String invalidJson = """
        {
            "description": "タイトルがない",
            "completed": false,
            "dueDate": "2025-08-01T12:00:00+09:00"
        }
        """;

    // 2. When & Then: POSTリクエストを送信し、400 Bad Requestが返ることを確認
    mockMvc.perform(post("/tasks")
        .contentType(MediaType.APPLICATION_JSON)
        .content(invalidJson))
        .andExpect(status().isBadRequest());

    // 3. Then: Serviceのcreateメソッドは呼ばれない
    verify(taskService, never()).create(any(Task.class));
  }

  @Test // タスク作成でタイトルが20文字を超える場合のテスト
  void tasksPost_shouldReturnBadRequest_whenTitleExceedsMaxLength() throws Exception {
    // 1. Given: タイトルが20文字を超えるJSON
    String invalidJson = """
        {
            "title": "This title is way too long and should fail validation",
            "completed": false,
            "dueDate": "2025-08-01T12:00:00+09:00"
        }
        """;

    // 2. When & Then: POSTリクエストを送信し、400 Bad Requestが返ることを確認
    mockMvc.perform(post("/tasks")
        .contentType(MediaType.APPLICATION_JSON)
        .content(invalidJson))
        .andExpect(status().isBadRequest());

    // 3. Then: Serviceのcreateメソッドは呼ばれない
    verify(taskService, never()).create(any(Task.class));
  }

  @Test // タスク作成でdescriptionが51文字の場合のテスト
  void tasksPost_shouldReturnBadRequest_whenDescriptionExceedsMaxLength() throws Exception {
    // 1. Given: descriptionが50文字を超えるJSON
    String invalidJson = """
        {
            "title": "Valid Title",
            "description": "あああああああああああああああああああああああああああああああああああああああああああああああああああ",
            "completed": false,
            "dueDate": "2025-08-01T12:00:00+09:00"
        }
        """;

    // 2. When & Then: POSTリクエストを送信し、400 Bad Requestが返ることを確認
    mockMvc.perform(post("/tasks")
        .contentType(MediaType.APPLICATION_JSON)
        .content(invalidJson))
        .andExpect(status().isBadRequest());

    // 3. Then: Serviceのcreateメソッドは呼ばれない
    verify(taskService, never()).create(any(Task.class));
  }

  @Test // タスク作成でdescriptionに半角英数字が含まれている場合のテスト
  void tasksPost_shouldReturnBadRequest_whenDescriptionHasAlphanumeric() throws Exception {
    // 1. Given: descriptionに半角英数字（pattern違反）が含まれているJSON
    String invalidJson = """
        {
            "title": "有効なタイトル",
            "description": "abc 半角英数字はダメ",
            "completed": false,
            "dueDate": "2025-08-01T12:00:00+09:00"
        }
        """;

    // 2. When & Then: POSTリクエストを送信し、400 Bad Requestが返ることを確認
    mockMvc.perform(post("/tasks")
        .contentType(MediaType.APPLICATION_JSON)
        .content(invalidJson))
        .andExpect(status().isBadRequest());

    // 3. Then: Serviceのcreateメソッドは呼ばれない
    verify(taskService, never()).create(any(Task.class));
  }

  @Test // タスク作成で期日がない場合のテスト
  void tasksPost_shouldReturnBadRequest_whenDueDateIsMissing() throws Exception {
    // 1. Given: 他はすべて正しいが、dueDateの形式だけが不正なJSON
    String invalidJson = """
        {
            "title": "Valid Title",
            "completed": false
        }
        """;

    // 2. When & Then: 不正なJSONを送信し、400エラーが返ることを確認
    mockMvc.perform(post("/tasks")
        .contentType(MediaType.APPLICATION_JSON)
        .content(invalidJson))
        .andExpect(status().isBadRequest());

    // Serviceのメソッドは呼ばれないことを確認
    verify(taskService, never()).create(any(Task.class));
  }

  @Test // タスク更新でidが見つからない場合のテスト
  void tasksIdPut_shouldReturnNotFound_whenTaskDoesNotExist() throws Exception {
    // 1. Given (準備): モックの設定
    when(taskService.update(eq(999L), any(Task.class)))
        .thenThrow(new ResourceNotFoundException("id999のタスクは存在しません"));

    String validJson = """
        {
            "title": "Updated Task",
            "dueDate": "2025-08-04T10:00:00+09:00"
        }
        """;

    // 2. When (実行): HTTP PUTリクエストを送信
    mockMvc.perform(put("/tasks/999")
        .contentType(MediaType.APPLICATION_JSON)
        .content(validJson))
        .andExpect(status().isNotFound());

    // 3. Then (検証): モックの呼び出しを検証
    verify(taskService).update(eq(999L), any(Task.class));
  }

  @Test // タスク更新でタイトルがない場合のテスト
  void tasksIdPut_shouldReturnBadRequest_whenTitleIsMissing() throws Exception {
    // 1. Given (準備): タイトルがないタスクリクエスト
    String taskRequestJson = """
        {
            "dueDate": "2025-08-04T10:00:00+09:00"
        }
        """;

    // 2. When (実行): HTTP PUTリクエストを送信
    mockMvc.perform(put("/tasks/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(taskRequestJson))
        .andExpect(status().isBadRequest());

    // 3. Then (検証): モックの呼び出しを検証
    verify(taskService, never()).update(anyLong(), any(Task.class));
  }

  @Test // タスク更新でタイトルが空の場合のテスト
  void tasksIdPut_shouldReturnBadRequest_whenTitleIsEmpty() throws Exception {
    // 1. Given (準備): タイトルが空のタスクリクエスト
    Task taskWithEmptyTitle = new Task();
    taskWithEmptyTitle.setTitle("");
    taskWithEmptyTitle.setDueDate(OffsetDateTime.of(2025, 8, 3, 10, 0, 0, 0, ZoneOffset.ofHours(9)));
    String taskRequestJson = objectMapper.writeValueAsString(taskWithEmptyTitle);

    // 2. When (実行): HTTP PUTリクエストを送信
    mockMvc.perform(put("/tasks/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(taskRequestJson))
        .andExpect(status().isBadRequest());

    // 3. Then (検証): モックの呼び出しを検証
    verify(taskService, never()).update(anyLong(), any(Task.class));
  }

  @Test // タスク更新でタイトルが20文字を超える場合のテスト
  void tasksIdPut_shouldReturnBadRequest_whenTitleExceedsMaxLength() throws Exception {
    // 1. Given (準備): タイトルが20文字を超えるタスクリクエスト
    Task taskWithLongTitle = new Task();
    taskWithLongTitle.setTitle("This title is way too long and should fail validation");
    taskWithLongTitle.setDueDate(OffsetDateTime.of(2025, 8, 3, 10, 0, 0, 0, ZoneOffset.ofHours(9)));
    String taskRequestJson = objectMapper.writeValueAsString(taskWithLongTitle);

    // 2. When (実行): HTTP PUTリクエストを送信
    mockMvc.perform(put("/tasks/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(taskRequestJson))
        .andExpect(status().isBadRequest());

    // 3. Then (検証): モックの呼び出しを検証
    verify(taskService, never()).update(anyLong(), any(Task.class));
  }

  @Test // タスク更新でdescriptionが51文字の場合のテスト
  void tasksIdPut_shouldReturnBadRequest_whenDescriptionExceedsMaxLength() throws Exception {
    // 1. Given (準備): descriptionが50文字を超えるタスクリクエスト
    Task taskWithLongDescription = new Task();
    taskWithLongDescription.setTitle("Valid Title");
    taskWithLongDescription.setDescription("ああああああああああいああああああああああいああああああああああいああああああああああいあああああああ");
    taskWithLongDescription.setDueDate(OffsetDateTime.of(2025, 8, 3, 10, 0, 0, 0, ZoneOffset.ofHours(9)));
    String taskRequestJson = objectMapper.writeValueAsString(taskWithLongDescription);

    // 2. When (実行): HTTP PUTリクエストを送信
    mockMvc.perform(put("/tasks/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(taskRequestJson))
        .andExpect(status().isBadRequest());

    // 3. Then (検証): モックの呼び出しを検証
    verify(taskService, never()).update(anyLong(), any(Task.class));
  }

  @Test // タスク更新でdescriptionに半角英数字が含まれている場合のテスト
  void tasksIdPut_shouldReturnBadRequest_whenDescriptionHasAlphanumeric() throws Exception {
    // 1. Given (準備): descriptionに半角英数字が含まれているタスクリクエスト
    Task taskWithAlphanumericDescription = new Task();
    taskWithAlphanumericDescription.setTitle("Valid Title");
    taskWithAlphanumericDescription.setDescription("abc 半角英数字はダメ");
    taskWithAlphanumericDescription.setDueDate(OffsetDateTime.of(2025, 8, 3, 10, 0, 0, 0, ZoneOffset.ofHours(9)));
    String taskRequestJson = objectMapper.writeValueAsString(taskWithAlphanumericDescription);

    // 2. When (実行): HTTP PUTリクエストを送信
    mockMvc.perform(put("/tasks/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(taskRequestJson))
        .andExpect(status().isBadRequest());

    // 3. Then (検証): モックの呼び出しを検証
    verify(taskService, never()).update(anyLong(), any(Task.class));
  }

  @Test // タスク更新で期日がない場合のテスト
  void tasksIdPut_shouldReturnBadRequest_whenDueDateIsMissing() throws Exception {
    // 1. Given (準備): 他はすべて正しいが、dueDateの形式だけが不正なタスクリクエスト
    String taskRequestJson = """
        {
            "title": "Valid Title",
            "completed": false
        }
        """;

    // 2. When (実行): HTTP PUTリクエストを送信
    mockMvc.perform(put("/tasks/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(taskRequestJson))
        .andExpect(status().isBadRequest());

    // 3. Then (検証): モックの呼び出しを検証
    verify(taskService, never()).update(anyLong(), any(Task.class));
  }

  @Test // タスク削除でidが見つからない場合のテスト
  void tasksIdDelete_shouldReturnNotFound_whenTaskDoesNotExist() throws Exception {
    // 1. Given (準備): モックの設定
    doThrow(new ResourceNotFoundException("id999のタスクは存在しません")).when(taskService).delete(999L);

    // 2. When (実行): HTTP DELETEリクエストを送信
    mockMvc.perform(delete("/tasks/999"))
        .andExpect(status().isNotFound());

    // 3. Then (検証): モックの呼び出しを検証
    verify(taskService).delete(999L);
  }

}
