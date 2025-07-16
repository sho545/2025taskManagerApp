package com.example.demo.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.demo.domain.model.Task;

public interface TaskRepository {
  List<Task> findAll() ;
  Optional<Task> findById(UUID id) ;
  Task save(Task task) ;
  void deleteById(UUID id) ;

} 
