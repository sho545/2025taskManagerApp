package com.example.demo.infrastructure.repositorylmpl;

import java.util.List;
import java.util.stream.Collectors;

import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.model.Task;
import com.example.demo.domain.repository.TaskRepository;
import com.example.demo.generated.infrastructure.entity.TaskEntity;
import com.example.demo.generated.infrastructure.mapper.TaskEntityDynamicSqlSupport;
import com.example.demo.generated.infrastructure.mapper.TaskEntityMapper;

@Repository

public class TaskRepositoryImpl implements TaskRepository {

  private final TaskEntityMapper taskMapper;

  public TaskRepositoryImpl(TaskEntityMapper taskMapper) {
    this.taskMapper = taskMapper;
  }

  @Override
  public List<Task> findAll() {
    SelectStatementProvider selectStatement = SqlBuilder.select(TaskEntityMapper.selectList)
        .from(TaskEntityDynamicSqlSupport.taskEntity)
        .build().render(RenderingStrategies.MYBATIS3);
    return taskMapper.selectMany(selectStatement).stream().map(this::toDomain).collect(Collectors.toList());
  }

  @Override
  public Task findById(Long id) {
    TaskEntity entity = null;
    return toDomain(taskMapper.selectByPrimaryKey(id).orElse(entity));
  }

  @Override
  public Task save(Task task) {

    TaskEntity entity = toEntity(task);

    if (task.getId() == null) {
      // 新規作成
      taskMapper.insert(entity);
    } else {
      // 更新
      taskMapper.updateByPrimaryKey(entity);
    }
    return toDomain(entity);
  }

  @Override
  public void deleteById(Long id) {
    taskMapper.deleteByPrimaryKey(id);
  }

  /**
   * EntityからDomainオブジェクトへ変換
   */
  public Task toDomain(TaskEntity entity) {
    if (entity == null)
      return null;
    Task domain = new Task();
    domain.setId(entity.getId());
    domain.setTitle(entity.getTitle());
    domain.setDescription(entity.getDescription());
    domain.setCompleted(entity.getIsCompleted());
    domain.setDueDate(entity.getDueDate());
    return domain;
  }

  /**
   * DomainオブジェクトからEntityへ変換
   */
  public TaskEntity toEntity(Task domain) {
    if (domain == null)
      return null;
    TaskEntity entity = new TaskEntity();
    entity.setId(domain.getId());
    entity.setTitle(domain.getTitle());
    entity.setDescription(domain.getDescription());
    entity.setIsCompleted(domain.isCompleted());
    entity.setDueDate(domain.getDueDate());
    return entity;
  }

}
