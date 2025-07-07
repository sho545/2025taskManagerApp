package com.example.demo.application.helper;

import java.time.ZoneId;

import com.example.demo.application.dto.TaskDto;
import com.example.demo.domain.model.Task;

public class TaskMapper {
  //インスタンス化されないようにコンストラクタをprivate化
  private TaskMapper(){}

  public static TaskDto toDto(Task task){
    TaskDto dto = new TaskDto() ;
    dto.setId(task.getId()); 
    dto.setTitle(task.getTitle());
    dto.setCompleted(task.getIsCompleted());
    if(task.getDueDate() != null){
      dto.setDueDate(task.getDueDate().toInstant()
                         .atZone(ZoneId.systemDefault())
                         .toOffsetDateTime());
    }
    return dto ;
  }
}
