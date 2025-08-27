package com.example.demo.domain.service;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.model.Task;
import com.example.demo.domain.repository.TaskRepository;
import com.example.demo.exception.ResourceNotFoundException;

@Service
public class TaskService {

  private final TaskRepository taskRepository;

  public TaskService(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  public List<Task> findAll() {
    return taskRepository.findAll();
  }

  public Task findById(Long id) {

    Task task = taskRepository.findById(id);

    if (task != null) {
      return task;
    } else {
      throw new ResourceNotFoundException("id" + id + "のタスクはありません");
    }

  }

  @Transactional
  public Task create(Task task) {
    OffsetDateTime now = OffsetDateTime.now();
    // 期限が現在の日時より前（過去）でないかチェック
    if (task.getDueDate().isBefore(now)) {
      throw new IllegalArgumentException("期限に過去の日時は設定できません。");
    }
    task.setCompleted(false);
    return taskRepository.save(task);
  }

  @Transactional
  public Task update(Long id, Task task) {
    Task existingTask = taskRepository.findById(id);
    if (existingTask != null) {
      if (task.getDueDate().isBefore(existingTask.getDueDate())) {
        throw new IllegalArgumentException("期限を過去の日時に戻すことはできません。");
      }
      task.setId(id);
      return taskRepository.save(task);
    } else {
      throw new ResourceNotFoundException("id" + id + "のタスクは存在しません");
    }
  }

  @Transactional
  public void delete(Long id) {
    if (taskRepository.findById(id) != null) {
      taskRepository.deleteById(id);
    } else {
      throw new ResourceNotFoundException("id" + id + "のタスクは存在しません");
    }
  }

}
