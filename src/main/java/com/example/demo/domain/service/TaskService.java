// package com.example.demo.domain.service;

// import java.util.List;
// import java.util.Optional;

// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// import com.example.demo.domain.model.Task;
// import com.example.demo.domain.repository.TaskRepository;

// @Service
// public class TaskService {

//   private final TaskRepository taskRepository ;

//   public TaskService(TaskRepository taskRepository){
//     this.taskRepository = taskRepository ;
//   }

//   public List<Task> findAll(){
//     return taskRepository.findAll() ;
//   }

//   public Optional<Task> findById(Long id){
//     return taskRepository.findById(id) ;
//   }

//   @Transactional
//   public Task create(Task task){
//     task.setCompleted(false);
//     return taskRepository.save(task);
//   }

//   @Transactional
//   public Optional<Task> update(Long id, Task task){
//     return taskRepository.findById(id)
//            .map(existingTask -> {
//               existingTask.setTitle(task.getTitle());
//               existingTask.setDescription(task.getDescription());
//               existingTask.setCompleted(task.isCompleted());
//               existingTask.setDueDate(task.getDueDate());
//               return taskRepository.save(existingTask);
//            });
//   }

//   @Transactional
//   public boolean delete(Long id){
//     return taskRepository.findById(id)
//            .map(task ->{
//             taskRepository.deleteById(id);
//             return true;
//            }).orElse(false) ;
//   }

// }
