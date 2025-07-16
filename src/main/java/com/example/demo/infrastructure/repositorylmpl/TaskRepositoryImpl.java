package com.example.demo.infrastructure.repositorylmpl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.model.Task;
import com.example.demo.domain.repository.TaskRepository;
import com.example.demo.infrastructure.mapper.TaskDynamicSqlSupport;
import com.example.demo.infrastructure.mapper.TaskMapper;


@Repository 
public class TaskRepositoryImpl implements TaskRepository {

  private final TaskMapper taskMapper ;

  public TaskRepositoryImpl(TaskMapper taskMapper){
    this.taskMapper = taskMapper ;
  }

  @Override
  public List<Task> findAll() {
    SelectStatementProvider selectStatement = SqlBuilder.select(TaskMapper.selectList).from(TaskDynamicSqlSupport.task)
                                              .build().render(RenderingStrategies.MYBATIS3);
    return taskMapper.selectMany(selectStatement) ;
  }

  @Override
  public Optional<Task> findById(UUID id) {
    return taskMapper.selectByPrimaryKey(id) ;
  }

  @Override
  public Task save(Task task) {
    if(task.getId() == null){
      //新規作成
      taskMapper.insertSelective(task);
    }else{
      //更新
      taskMapper.updateByPrimaryKey(task);
    }
    return task ;
  }

  @Override
  public void deleteById(UUID id) {
    taskMapper.deleteByPrimaryKey(id) ;
  }


}
