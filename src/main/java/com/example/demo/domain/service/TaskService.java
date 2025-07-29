package com.example.demo.domain.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.model.Task;
import com.example.demo.domain.repository.TaskRepository;

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
      throw new NoSuchElementException("id" + id + "のタスクはありません");
    }

  }

  @Transactional
  public Task create(Task task) {
    task.setCompleted(false);
    return taskRepository.save(task);
  }

  @Transactional
  public Task update(Long id, Task task) {
    if (taskRepository.findById(id) != null) {
      task.setId(id);
      return taskRepository.save(task);
    } else {
      throw new NoSuchElementException("id" + id + "のタスクは存在しません");
    }
  }

  @Transactional
  public void delete(Long id) {
    taskRepository.deleteById(id);
  }

}
