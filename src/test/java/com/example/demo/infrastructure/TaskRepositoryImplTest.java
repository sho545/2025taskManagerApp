package com.example.demo.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.OffsetDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.domain.model.Task;
import com.example.demo.generated.infrastructure.entity.TaskEntity;
import com.example.demo.generated.infrastructure.mapper.TaskEntityMapper;
import com.example.demo.infrastructure.repositorylmpl.TaskRepositoryImpl;

@ExtendWith(MockitoExtension.class)
public class TaskRepositoryImplTest {

  @Mock
  private TaskEntityMapper taskMapper; // Mapperをモック化

  @InjectMocks
  private TaskRepositoryImpl taskRepositoryImpl; // テスト対象のリポジトリ実装

  @Test // findAllメソッドのテスト
  void findAll_shouldReturnListOfTask() {
    // 1. Given (準備): テストの前提条件を設定
    TaskEntity task1 = new TaskEntity();
    OffsetDateTime now1 = OffsetDateTime.now();
    task1.setId(1L);
    task1.setTitle("Task 1");
    task1.setDescription("説明");
    task1.setIsCompleted(false);
    task1.setDueDate(now1);

    TaskEntity task2 = new TaskEntity();
    OffsetDateTime now2 = OffsetDateTime.now();
    task2.setId(2L);
    task2.setTitle("Task 2");
    task2.setIsCompleted(true);
    task2.setDueDate(now2);
    // モックの動作を定義する
    // ここでは、taskEntityのリストを返すように設定する
    when(taskMapper.selectMany(any())).thenReturn(List.of(task1, task2));

    // 2. When (実行): テスト対象のメソッドを呼び出す
    List<Task> tasks = taskRepositoryImpl.findAll();

    // 3. Then (検証): 実行結果が期待通りか検証
    assertThat(tasks).isNotEmpty();
    assertThat(tasks).hasSize(2);
    verify(taskMapper).selectMany(any());
    // 他の検証ロジックを追加することも可能
    assertThat(tasks.get(0).getTitle()).isEqualTo("Task 1");
    assertThat(tasks.get(0).getDescription()).isEqualTo("説明");
    assertThat(tasks.get(0).isCompleted()).isFalse();
    assertThat(tasks.get(0).getDueDate()).isEqualTo(now1);

    assertThat(tasks.get(1).getTitle()).isEqualTo("Task 2");
    assertThat(tasks.get(1).isCompleted()).isTrue();
    assertThat(tasks.get(1).getDueDate()).isEqualTo(now2);
  }

  @Test // tasksリストが空の場合のfindAllメソッドのテスト
  void findAll_shouldReturnEmptyList() {
    // 1. Given (準備): テストの前提条件を設定
    // モックの動作を定義する
    when(taskMapper.selectMany(any())).thenReturn(List.of());

    // 2. When (実行): テスト対象のメソッドを呼び出す
    List<Task> tasks = taskRepositoryImpl.findAll();

    // 3. Then (検証): 実行結果が期待通りか検証
    assertThat(tasks).isEmpty();
    verify(taskMapper).selectMany(any());
  }

  @Test // findByIdメソッドのテスト
  void findById_shouldReturnTask() {
    // 1. Given (準備): テストの前提条件を設定
    TaskEntity taskEntity = new TaskEntity();
    OffsetDateTime now = OffsetDateTime.now();
    taskEntity.setId(1L);
    taskEntity.setTitle("Task 1");
    taskEntity.setDescription("説明");
    taskEntity.setIsCompleted(false);
    taskEntity.setDueDate(now);

    // モックの動作を定義する
    when(taskMapper.selectByPrimaryKey(1L)).thenReturn(java.util.Optional.of(taskEntity));

    // 2. When (実行): テスト対象のメソッドを呼び出す
    Task task = taskRepositoryImpl.findById(1L);

    // 3. Then (検証): 実行結果が期待通りか検証
    assertThat(task).isNotNull();
    assertThat(task.getId()).isEqualTo(1L);
    assertThat(task.getTitle()).isEqualTo("Task 1");
    assertThat(task.getDescription()).isEqualTo("説明");
    assertThat(task.isCompleted()).isFalse();
    assertThat(task.getDueDate()).isEqualTo(now);
    verify(taskMapper).selectByPrimaryKey(1L);
  }

  @Test // idが存在しない場合のfindByIdメソッドのテスト
  void findById_shouldReturnNull() {
    // 1. Given (準備): テストの前提条件を設定
    // モックの動作を定義する
    when(taskMapper.selectByPrimaryKey(999L)).thenReturn(java.util.Optional.empty());

    // 2. When (実行): テスト対象のメソッドを呼び出す
    Task task = taskRepositoryImpl.findById(999L);

    // 3. Then (検証): 実行結果が期待通りか検証
    assertThat(task).isNull();
    verify(taskMapper).selectByPrimaryKey(999L);
  }

  @Test // saveメソッドのテスト（新規作成）
  void save_shouldInsertNewTask() {
    // 1. Given (準備): テストの前提条件を設定
    Task task = new Task();
    OffsetDateTime now = OffsetDateTime.now();
    task.setTitle("New Task");
    task.setDescription("新しいタスクの説明");
    task.setCompleted(false);
    task.setDueDate(now);

    // モックの動作を定義する
    when(taskMapper.insert(any(TaskEntity.class))).thenReturn(1); // 挿入成功を模擬

    // 2. When (実行): テスト対象のメソッドを呼び出す
    Task savedTask = taskRepositoryImpl.save(task);

    // 3. Then (検証): 実行結果が期待通りか検証
    assertThat(savedTask).isNotNull();
    assertThat(savedTask.getTitle()).isEqualTo("New Task");
    assertThat(savedTask.getDescription()).isEqualTo("新しいタスクの説明");
    assertThat(savedTask.isCompleted()).isFalse();
    assertThat(savedTask.getDueDate()).isEqualTo(now);
    verify(taskMapper).insert(any(TaskEntity.class));
  }

  @Test // saveメソッドのテスト（更新）
  void save_shouldUpdateExistingTask() {
    // 1. Given (準備): テストの前提条件を設定
    Task task = new Task();
    task.setId(1L);
    task.setTitle("Updated Task");
    task.setDescription("更新されたタスクの説明");
    task.setCompleted(true);
    OffsetDateTime now = OffsetDateTime.now();
    task.setDueDate(now);

    // モックの動作を定義する
    when(taskMapper.updateByPrimaryKey(any(TaskEntity.class))).thenReturn(1); // 更新成功を模擬

    // 2. When (実行): テスト対象のメソッドを呼び出す
    Task updatedTask = taskRepositoryImpl.save(task);

    // 3. Then (検証): 実行結果が期待通りか検証
    assertThat(updatedTask).isNotNull();
    assertThat(updatedTask.getId()).isEqualTo(1L);
    assertThat(updatedTask.getTitle()).isEqualTo("Updated Task");
    assertThat(updatedTask.getDescription()).isEqualTo("更新されたタスクの説明");
    assertThat(updatedTask.isCompleted()).isTrue();
    assertThat(updatedTask.getDueDate()).isEqualTo(now);
    verify(taskMapper).updateByPrimaryKey(any(TaskEntity.class));
  }

  @Test // deleteByIdメソッドのテスト
  void deleteById_shouldDeleteTask() {
    // 1. Given (準備): テストの前提条件を設定
    Long taskId = 1L;

    // モックの動作を定義する
    when(taskMapper.deleteByPrimaryKey(taskId)).thenReturn(1); // 削除成功を模擬

    // 2. When (実行): テスト対象のメソッドを呼び出す
    taskRepositoryImpl.deleteById(taskId);

    // 3. Then (検証): 実行結果が期待通りか検証
    verify(taskMapper).deleteByPrimaryKey(taskId);
  }

  @Test // deleteByIdメソッドのテスト（存在しないID）
  void deleteById_shouldHandleNonExistentTask() {
    // 1. Given (準備): テストの前提条件を設定
    Long taskId = 999L;

    // モックの動作を定義する
    when(taskMapper.deleteByPrimaryKey(taskId)).thenReturn(0); // 削除失敗を模擬

    // 2. When (実行): テスト対象のメソッドを呼び出す
    taskRepositoryImpl.deleteById(taskId);

    // 3. Then (検証): 実行結果が期待通りか検証
    verify(taskMapper).deleteByPrimaryKey(taskId);
  }

  @Test // toDomainメソッドのテスト
  void toDomain_shouldConvertTaskEntityToTask() {
    // 1. Given (準備): テストの前提条件を設定
    TaskEntity entity = new TaskEntity();
    OffsetDateTime now = OffsetDateTime.now();
    entity.setId(1L);
    entity.setTitle("Task 1");
    entity.setDescription("説明");
    entity.setIsCompleted(false);
    entity.setDueDate(now);

    // 2. When (実行): テスト対象のメソッドを呼び出す
    Task task = taskRepositoryImpl.toDomain(entity);

    // 3. Then (検証): 実行結果が期待通りか検証
    assertThat(task.getId()).isEqualTo(1L);
    assertThat(task.getTitle()).isEqualTo("Task 1");
    assertThat(task.getDescription()).isEqualTo("説明");
    assertThat(task.isCompleted()).isFalse();
    assertThat(task.getDueDate()).isEqualTo(now);
  }

  @Test // toEntityメソッドのテスト
  void toEntity_shouldConvertTaskToTaskEntity() {
    // 1. Given (準備): テストの前提条件を設定
    Task task = new Task();
    OffsetDateTime now = OffsetDateTime.now();
    task.setId(1L);
    task.setTitle("Task 1");
    task.setDescription("説明");
    task.setCompleted(false);
    task.setDueDate(now);

    // 2. When (実行): テスト対象のメソッドを呼び出す
    TaskEntity entity = taskRepositoryImpl.toEntity(task);

    // 3. Then (検証): 実行結果が期待通りか検証
    assertThat(entity.getId()).isEqualTo(1L);
    assertThat(entity.getTitle()).isEqualTo("Task 1");
    assertThat(entity.getDescription()).isEqualTo("説明");
    assertThat(entity.getIsCompleted()).isFalse();
    assertThat(entity.getDueDate()).isEqualTo(now);
  }
}
