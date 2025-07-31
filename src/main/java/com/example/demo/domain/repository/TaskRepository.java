package com.example.demo.domain.repository;

import java.util.List;

import com.example.demo.domain.model.Task;

public interface TaskRepository {
  List<Task> findAll() ;
  Task findById(Long id) ;
  Task save(Task task) ;
  void deleteById(Long id) ;

} 
