package com.example.demo.domain.service;

import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.domain.model.Task;
import com.example.demo.domain.repository.TaskRepository;
import com.example.demo.exception.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
  @Mock // 依存関係にあるTaskRepositoryをモック化する
  private TaskRepository taskRepository;

  @InjectMocks // モック（taskRepository）を注入して、テスト対象のTaskServiceをインスタンス化
  private TaskService taskService;

  // ---- 成功ケースのテスト ----

  @Test // findTasksメソッドのテスト
  void getTasks_shouldReturnListOfTasks_whenTasksExist() {
    // 1. Given (準備): テストの前提条件を設定
    Task task1 = new Task();
    task1.setId(1L);
    task1.setTitle("Task 1");
    task1.setDueDate(OffsetDateTime.of(2025, 8, 1, 10, 0, 0, 0, ZoneOffset.ofHours(9)));

    Task task2 = new Task();
    task2.setId(2L);
    task2.setTitle("Task 2");
    task2.setDueDate(OffsetDateTime.of(2025, 8, 2, 10, 0, 0, 0, ZoneOffset.ofHours(9)));

    // リポジトリのfindAllが呼ばれたら、準備したタスクのリストを返すように設定
    when(taskRepository.findAll()).thenReturn(List.of(task1, task2));

    // 2. When (実行): テスト対象のメソッドを呼び出す
    List<Task> tasks = taskService.findAll();

    // 3. Then (検証): 実行結果が期待通りか検証
    assertThat(tasks).isNotNull();
    assertThat(tasks).hasSize(2);
    assertThat(tasks.get(0).getTitle()).isEqualTo("Task 1");
    assertThat(tasks.get(1).getTitle()).isEqualTo("Task 2");
  }

  @Test // findByIdメソッドのテスト
  void getTask_shouldReturnTask_whenTaskExists() {
    // 1. Given (準備): テストの前提条件を設定
    Task task = new Task();
    task.setId(1L);
    task.setTitle("Test Task");
    OffsetDateTime dueDate = OffsetDateTime.of(2025, 8, 1, 10, 0, 0, 0, ZoneOffset.ofHours(9));
    task.setDueDate(dueDate);

    // リポジトリのfindById(1L)が呼ばれたら、準備したtaskを返すように設定
    when(taskRepository.findById(1L)).thenReturn(task);

    // 2. When (実行): テスト対象のメソッドを呼び出す
    Task foundTask = taskService.findById(1L);

    // 3. Then (検証): 実行結果が期待通りか検証
    assertThat(foundTask).isNotNull();
    assertThat(foundTask.getTitle()).isEqualTo("Test Task");
    assertThat(foundTask.getDueDate()).isEqualTo(dueDate);
  }

  @Test // createメソッドのテスト
  void create_shouldSaveTask_whenDueDateIsInTheFuture() {
    // テストで使う固定の日時を準備
    OffsetDateTime fixedDateTime = OffsetDateTime.parse("2025-08-01T12:00:00+09:00");

    // 未来の日時を持つタスク
    Task taskWithFutureDueDate = new Task();
    taskWithFutureDueDate.setTitle("Task with Future Due Date");
    taskWithFutureDueDate.setDueDate(fixedDateTime.plusSeconds(1)); // 固定時刻の1秒後

    try (MockedStatic<OffsetDateTime> mockedStatic = mockStatic(OffsetDateTime.class)) {
      // OffsetDateTime.now()が呼ばれたら、固定の日時を返すように設定
      mockedStatic.when(OffsetDateTime::now).thenReturn(fixedDateTime);

      // 正常に実行される
      taskService.create(taskWithFutureDueDate);

      // saveが呼ばれたことを確認
      verify(taskRepository).save(taskWithFutureDueDate);
    }
  }

  @Test // updateメソッドのテスト
  void update_shouldUpdateTask_whenTaskExistsAndDateIsValid() {
    // 1. Given (準備)
    // DBに既に存在するタスク
    Task existingTask = new Task();
    existingTask.setId(1L);
    existingTask.setTitle("古いタイトル");
    existingTask.setDueDate(OffsetDateTime.parse("2025-08-01T10:00:00+09:00"));

    // 更新用のデータ（期限は既存のものより後）
    Task updateData = new Task();
    updateData.setTitle("新しいタイトル");
    updateData.setDueDate(OffsetDateTime.parse("2025-08-02T10:00:00+09:00"));

    // findById(1L)が呼ばれたら、existingTaskを返す
    when(taskRepository.findById(1L)).thenReturn(existingTask);
    // save(updateData)が呼ばれたら、updateDataを返す
    when(taskRepository.save(updateData)).thenReturn(updateData);

    // 2. When (実行)
    Task result = taskService.update(1L, updateData);

    // 3. Then (検証)
    assertThat(result.getTitle()).isEqualTo("新しいタイトル");
    // IDがセットされているか確認
    assertThat(result.getId()).isEqualTo(1L);
    // saveがupdateDataオブジェクトを引数に呼ばれたことを確認
    verify(taskRepository).save(updateData);
  }

  @Test // deleteメソッドのテスト
  void deleteTask_shouldDeleteTask_whenTaskExists() {
    // 1. Given (準備): テストの前提条件を設定
    Task existingTask = new Task();
    existingTask.setId(1L);
    existingTask.setTitle("Task to be deleted");

    // リポジトリのfindByIdが呼ばれたら、既存のタスクを返すように設定
    when(taskRepository.findById(1L)).thenReturn(existingTask);

    // 2. When (実行): テスト対象のメソッドを呼び出す
    taskService.delete(1L);

    // 3. Then (検証): リポジトリのdeleteByIdが呼ばれたことを確認
    verify(taskRepository).deleteById(1L); // この行はMockitoを使用している場合に有効
  }

  // ---- 失敗ケースのテスト ----
  @Test // findTasksメソッドでタスクが存在しない場合のテスト
  void getTasks_shouldReturnEmptyList_whenNoTasksExist() {
    // 1. Given (準備): リポジトリのfindAllが空のリストを返すように設定
    when(taskRepository.findAll()).thenReturn(List.of());

    // 2. When (実行): テスト対象のメソッドを呼び出す
    List<Task> tasks = taskService.findAll();

    // 3. Then (検証): 実行結果が期待通りか検証
    assertThat(tasks).isNotNull();
    assertThat(tasks).isEmpty();
  }

  @Test // findByIdメソッドでタスクが存在しない場合のテスト
  void getTask_shouldThrowResourceNotFoundException_whenTaskDoesNotExist() {
    // 1. Given (準備): リポジトリのfindByIdがnullを返すように設定
    when(taskRepository.findById(999L)).thenReturn(null);

    // 2. When (実行): テスト対象のメソッドを呼び出す
    try {
      taskService.findById(999L);
    } catch (ResourceNotFoundException e) {
      // 3. Then (検証): 例外がスローされることを確認
      assertThat(e.getMessage()).isEqualTo("id999のタスクはありません");
      return; // 例外がスローされたのでテストは成功
    }

    // 例外がスローされなかった場合、テストは失敗
    throw new AssertionError("Expected ResourceNotFoundException was not thrown");
  }

  @Test // createメソッドで期限が過去の日時の場合のテスト
  void create_shouldThrowException_whenDueDateIsInThePast() {
    // テストで使う固定の日時を準備
    OffsetDateTime fixedDateTime = OffsetDateTime.parse("2025-08-01T12:00:00+09:00");

    // 過去の日時を持つタスク
    Task taskWithPastDueDate = new Task();
    taskWithPastDueDate.setTitle("Task with Past Due Date");
    taskWithPastDueDate.setDueDate(fixedDateTime.minusSeconds(1)); // 固定時刻の1秒前

    // try-with-resources構文でOffsetDateTimeのstaticメソッドをモック化
    try (MockedStatic<OffsetDateTime> mockedStatic = mockStatic(OffsetDateTime.class)) {
      // OffsetDateTime.now()が呼ばれたら、固定の日時を返すように設定
      mockedStatic.when(OffsetDateTime::now).thenReturn(fixedDateTime);

      // 例外がスローされることを確認
      assertThrows(IllegalArgumentException.class, () -> {
        taskService.create(taskWithPastDueDate);
      });
    }
  }

  @Test // updateメソッドでタスクが存在しない場合のテスト
  void updateTask_shouldThrowResourceNotFoundException_whenTaskDoesNotExist() {
    // 1. Given (準備): リポジトリのfindByIdがnullを返すように設定
    when(taskRepository.findById(999L)).thenReturn(null);

    // 2. When (実行): テスト対象のメソッドを呼び出す
    try {
      Task task = new Task();
      task.setTitle("Non-existent Task");
      taskService.update(999L, task);
    } catch (ResourceNotFoundException e) {
      // 3. Then (検証): 例外がスローされることを確認
      assertThat(e.getMessage()).isEqualTo("id999のタスクは存在しません");
      return; // 例外がスローされたのでテストは成功
    }

    // 例外がスローされなかった場合、テストは失敗
    throw new AssertionError("Expected ResourceNotFoundException was not thrown");
  }

  @Test // updateメソッドで期限を過去の日時に戻そうとした場合のテスト
  void updateTask_shouldThrowException_whenDueDateIsInThePast() {
    // 1. Given (準備): リポジトリのfindByIdが既存のタスクを返すように設定
    Task existingTask = new Task();
    existingTask.setId(1L);
    existingTask.setDueDate(OffsetDateTime.of(2025, 8, 1, 10, 0, 0, 0, ZoneOffset.ofHours(9)));
    when(taskRepository.findById(1L)).thenReturn(existingTask);

    // 更新するタスク（期限を過去に戻そうとする）
    Task updatedTask = new Task();
    updatedTask.setDueDate(OffsetDateTime.of(2025, 7, 31, 10, 0, 0, 0, ZoneOffset.ofHours(9)));

    // 2. When (実行): テスト対象のメソッドを呼び出す
    try {
      taskService.update(1L, updatedTask);
    } catch (IllegalArgumentException e) {
      // 3. Then (検証): 例外がスローされることを確認
      assertThat(e.getMessage()).isEqualTo("期限を過去の日時に戻すことはできません。");
      return; // 例外がスローされたのでテストは成功
    }

    // 例外がスローされなかった場合、テストは失敗
    throw new AssertionError("Expected IllegalArgumentException was not thrown");
  }

  @Test // deleteメソッドでタスクが存在しない場合のテスト
  void deleteTask_shouldThrowResourceNotFoundException_whenTaskDoesNotExist() {
    // 1. Given (準備): リポジトリのfindByIdがnullを返すように設定
    when(taskRepository.findById(999L)).thenReturn(null);

    // 2. When (実行): テスト対象のメソッドを呼び出す
    try {
      taskService.delete(999L);
    } catch (ResourceNotFoundException e) {
      // 3. Then (検証): 例外がスローされることを確認
      assertThat(e.getMessage()).isEqualTo("id999のタスクは存在しません");
      return; // 例外がスローされたのでテストは成功
    }

    // 例外がスローされなかった場合、テストは失敗
    throw new AssertionError("Expected ResourceNotFoundException was not thrown");
  }

}
