package com.example.demo.domain.repository;

import java.util.List;
import java.util.Optional;

import com.example.demo.domain.model.Task;

public interface TaskRepository {
  List<Task> findAll() ;
  Optional<Task> findById(Long id) ;
  Task save(Task task) ;
  boolean deleteById(Long id) ;

} 
