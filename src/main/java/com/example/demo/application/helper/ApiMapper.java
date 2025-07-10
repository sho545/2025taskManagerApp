package com.example.demo.application.helper;

import java.time.ZoneId;
import java.util.Date;

import com.example.demo.application.dto.TaskDto;
import com.example.demo.application.form.TaskRequest;
import com.example.demo.domain.model.Task;

public class ApiMapper {
  //インスタンス化されないようにコンストラクタをprivate化
  private ApiMapper(){}

  public static TaskDto toDto(Task task){
    TaskDto dto = new TaskDto() ;
    dto.setId(task.getId()); 
    dto.setTitle(task.getTitle());
    dto.setDescription(task.getDescription());
    dto.setCompleted(task.getIsCompleted());
    if(task.getDueDate() != null){
      dto.setDueDate(task.getDueDate().toInstant()
                         .atZone(ZoneId.systemDefault())
                         .toOffsetDateTime());
    }
    return dto ;
  }

  public static Task toEntity(TaskRequest dto){
    Task entity = new Task();
    entity.setTitle(dto.getTitle());
    entity.setDescription(dto.getDescription());
    entity.setIsCompleted(dto.getCompleted());
    if(dto.getDueDate() != null){
      entity.setDueDate(Date.from(dto.getDueDate().toInstant()));
    }
    return entity;
  }
}
