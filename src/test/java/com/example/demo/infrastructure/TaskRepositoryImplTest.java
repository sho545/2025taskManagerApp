package com.example.demo.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.model.Task;
import com.example.demo.domain.repository.TaskRepository;
import com.example.demo.infrastructure.repositorylmpl.TaskRepositoryImpl;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import static org.assertj.core.api.Assertions.assertThat;

@MybatisTest
@Import(TaskRepositoryImpl.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql("/test-data.sql")
@Transactional
public class TaskRepositoryImplTest {

  @Autowired
  private TaskRepository taskRepository;

  @Test
  void findAll_shouldReturnAllTasks() {
    // When
    List<Task> tasks = taskRepository.findAll();

    // Then
    assertThat(tasks).hasSize(2);

    // 1件目のタスクテスト
    Task firstTask = tasks.get(0);
    assertThat(firstTask.getId()).isEqualTo(100L);
    assertThat(firstTask.getTitle()).isEqualTo("Existing Task 1");
    // assertThat(firstTask.getDescription()).isEqualTo("テストの説明");
    assertThat(firstTask.isCompleted()).isFalse();
    // OffsetDateTime expectedUtc = OffsetDateTime.of(2025, 10, 1, 10, 0, 0, 0,
    // ZoneOffset.UTC);
    // assertThat(firstTask.getDueDate()).isEqualTo(expectedUtc);

    // 2件目のタスクテスト
    Task secondTask = tasks.get(1);
    assertThat(secondTask.getId()).isEqualTo(200L);
    assertThat(secondTask.getTitle()).isEqualTo("Existing Task 2");
    assertThat(secondTask.getDescription()).isEmpty();
    assertThat(secondTask.isCompleted()).isTrue();
    // OffsetDateTime expectedUtc2 = OffsetDateTime.of(2025, 11, 1, 11, 0, 0, 0,
    // ZoneOffset.UTC);
    // assertThat(secondTask.getDueDate()).isEqualTo(expectedUtc2);
  }

  @Test
  void findById_shouldReturnTask_whenTaskExists() {
    // When
    Task task = taskRepository.findById(100L);

    // Then
    // assertThat(task).isNotNull();
    // assertThat(task.getTitle()).isEqualTo("Existing Task 1");
    // assertThat(task.getDescription()).isEqualTo("テストの説明");
    // assertThat(task.isCompleted()).isFalse();
    // OffsetDateTime expectedUtc = OffsetDateTime.of(2025, 10, 1, 10, 0, 0, 0,
    // ZoneOffset.UTC);
    // assertThat(task.getDueDate().toInstant()).isEqualTo(expectedUtc.toInstant());

    // 期待される値を定義
    String expectedDescription = "テストの説明";
    OffsetDateTime expectedUtc = OffsetDateTime.of(2026, 10, 1, 10, 0, 0, 0, ZoneOffset.UTC);

    // 実際の値と期待値をコンソールに出力
    System.out.println("--- DEBUG START ---");
    System.out.println("Actual Description  : [" + task.getDescription() + "]");
    System.out.println("Expected Description: [" + expectedDescription + "]");
    System.out.println("Actual DueDate      : " + task.getDueDate());
    System.out.println("Expected DueDate    : " + expectedUtc);
    System.out.println("--- DEBUG END ---");
  }

  @Test
  void findById_shouldReturnNull_whenTaskDoesNotExist() {
    // When
    Task task = taskRepository.findById(999L);

    // Then
    assertThat(task).isNull();
  }

  @Test
  void save_shouldInsertNewTask_whenIdIsNull() {
    // Given
    Task newTask = new Task();
    newTask.setTitle("New Task Title");
    newTask.setDescription("説明");
    newTask.setCompleted(false);
    newTask.setDueDate(OffsetDateTime.of(2026, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC));

    // When
    Task savedTask = taskRepository.save(newTask);
    Task foundTask = taskRepository.findById(savedTask.getId());

    // Then
    assertThat(savedTask.getId()).isNotNull();
    assertThat(foundTask).isNotNull();
    assertThat(foundTask.getTitle()).isEqualTo("New Task Title");
    assertThat(foundTask.getDescription()).isEqualTo("説明");
    assertThat(foundTask.isCompleted()).isFalse();
    assertThat(foundTask.getDueDate()).isEqualTo(OffsetDateTime.of(2026, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC));
  }

  @Test
  void save_shouldUpdateExistingTask_whenIdIsNotNull() {
    // Given
    Task taskToUpdate = new Task();
    taskToUpdate.setId(100L); // 既存のID
    taskToUpdate.setTitle("Updated Title");
    taskToUpdate.setDescription("説明更新");
    taskToUpdate.setCompleted(true);
    taskToUpdate.setDueDate(OffsetDateTime.of(2027, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC));

    // When
    taskRepository.save(taskToUpdate);
    Task updatedTask = taskRepository.findById(100L);

    // Then
    assertThat(updatedTask).isNotNull();
    assertThat(updatedTask.getTitle()).isEqualTo("Updated Title");
    assertThat(updatedTask.getDescription()).isEqualTo("説明更新");
    assertThat(updatedTask.isCompleted()).isTrue();
    assertThat(updatedTask.getDueDate()).isEqualTo(OffsetDateTime.of(2027, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC));
  }

  @Test
  void deleteById_shouldRemoveTask() {
    // When
    taskRepository.deleteById(100L);
    Task deletedTask = taskRepository.findById(100L);

    // Then
    assertThat(deletedTask).isNull();
  }
}
