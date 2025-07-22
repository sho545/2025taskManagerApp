package com.example.demo.generated.infrastructure.mapper;

import static com.example.demo.generated.infrastructure.mapper.TaskEntityDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

import com.example.demo.generated.infrastructure.entity.TaskEntity;
import jakarta.annotation.Generated;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.delete.DeleteDSLCompleter;
import org.mybatis.dynamic.sql.select.CountDSLCompleter;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.UpdateDSL;
import org.mybatis.dynamic.sql.update.UpdateDSLCompleter;
import org.mybatis.dynamic.sql.update.UpdateModel;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;
import org.mybatis.dynamic.sql.util.mybatis3.CommonCountMapper;
import org.mybatis.dynamic.sql.util.mybatis3.CommonDeleteMapper;
import org.mybatis.dynamic.sql.util.mybatis3.CommonInsertMapper;
import org.mybatis.dynamic.sql.util.mybatis3.CommonUpdateMapper;
import org.mybatis.dynamic.sql.util.mybatis3.MyBatis3Utils;

@Mapper
public interface TaskEntityMapper extends CommonCountMapper, CommonDeleteMapper, CommonInsertMapper<TaskEntity>, CommonUpdateMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-22T17:42:41.0582421+09:00", comments="Source Table: TASKS")
    BasicColumn[] selectList = BasicColumn.columnList(id, title, description, isCompleted, dueDate);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-22T17:42:41.0494668+09:00", comments="Source Table: TASKS")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="TaskEntityResult", value = {
        @Result(column="ID", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="TITLE", property="title", jdbcType=JdbcType.VARCHAR),
        @Result(column="DESCRIPTION", property="description", jdbcType=JdbcType.VARCHAR),
        @Result(column="IS_COMPLETED", property="isCompleted", jdbcType=JdbcType.BOOLEAN),
        @Result(column="DUE_DATE", property="dueDate", jdbcType=JdbcType.TIMESTAMP_WITH_TIMEZONE)
    })
    List<TaskEntity> selectMany(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-22T17:42:41.0516929+09:00", comments="Source Table: TASKS")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("TaskEntityResult")
    Optional<TaskEntity> selectOne(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-22T17:42:41.0516929+09:00", comments="Source Table: TASKS")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, taskEntity, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-22T17:42:41.0527338+09:00", comments="Source Table: TASKS")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, taskEntity, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-22T17:42:41.053695+09:00", comments="Source Table: TASKS")
    default int deleteByPrimaryKey(Long id_) {
        return delete(c -> 
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-22T17:42:41.053695+09:00", comments="Source Table: TASKS")
    @Options(useGeneratedKeys=true, keyProperty="id")
    default int insert(TaskEntity row) {
        return MyBatis3Utils.insert(this::insert, row, taskEntity, c ->
            c.map(title).toProperty("title")
            .map(description).toProperty("description")
            .map(isCompleted).toProperty("isCompleted")
            .map(dueDate).toProperty("dueDate")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-22T17:42:41.0560697+09:00", comments="Source Table: TASKS")
    default int insertMultiple(Collection<TaskEntity> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, taskEntity, c ->
            c.map(id).toProperty("id")
            .map(title).toProperty("title")
            .map(description).toProperty("description")
            .map(isCompleted).toProperty("isCompleted")
            .map(dueDate).toProperty("dueDate")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-22T17:42:41.0572014+09:00", comments="Source Table: TASKS")
    default int insertSelective(TaskEntity row) {
        return MyBatis3Utils.insert(this::insert, row, taskEntity, c ->
            c.map(id).toPropertyWhenPresent("id", row::getId)
            .map(title).toPropertyWhenPresent("title", row::getTitle)
            .map(description).toPropertyWhenPresent("description", row::getDescription)
            .map(isCompleted).toPropertyWhenPresent("isCompleted", row::getIsCompleted)
            .map(dueDate).toPropertyWhenPresent("dueDate", row::getDueDate)
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-22T17:42:41.0592805+09:00", comments="Source Table: TASKS")
    default Optional<TaskEntity> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, taskEntity, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-22T17:42:41.0602837+09:00", comments="Source Table: TASKS")
    default List<TaskEntity> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, taskEntity, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-22T17:42:41.0602837+09:00", comments="Source Table: TASKS")
    default List<TaskEntity> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, taskEntity, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-22T17:42:41.061759+09:00", comments="Source Table: TASKS")
    default Optional<TaskEntity> selectByPrimaryKey(Long id_) {
        return selectOne(c ->
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-22T17:42:41.061759+09:00", comments="Source Table: TASKS")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, taskEntity, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-22T17:42:41.0627633+09:00", comments="Source Table: TASKS")
    static UpdateDSL<UpdateModel> updateAllColumns(TaskEntity row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(id).equalTo(row::getId)
                .set(title).equalTo(row::getTitle)
                .set(description).equalTo(row::getDescription)
                .set(isCompleted).equalTo(row::getIsCompleted)
                .set(dueDate).equalTo(row::getDueDate);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-22T17:42:41.0647845+09:00", comments="Source Table: TASKS")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(TaskEntity row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(id).equalToWhenPresent(row::getId)
                .set(title).equalToWhenPresent(row::getTitle)
                .set(description).equalToWhenPresent(row::getDescription)
                .set(isCompleted).equalToWhenPresent(row::getIsCompleted)
                .set(dueDate).equalToWhenPresent(row::getDueDate);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-22T17:42:41.0657827+09:00", comments="Source Table: TASKS")
    default int updateByPrimaryKey(TaskEntity row) {
        return update(c ->
            c.set(title).equalTo(row::getTitle)
            .set(description).equalTo(row::getDescription)
            .set(isCompleted).equalTo(row::getIsCompleted)
            .set(dueDate).equalTo(row::getDueDate)
            .where(id, isEqualTo(row::getId))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-22T17:42:41.0672634+09:00", comments="Source Table: TASKS")
    default int updateByPrimaryKeySelective(TaskEntity row) {
        return update(c ->
            c.set(title).equalToWhenPresent(row::getTitle)
            .set(description).equalToWhenPresent(row::getDescription)
            .set(isCompleted).equalToWhenPresent(row::getIsCompleted)
            .set(dueDate).equalToWhenPresent(row::getDueDate)
            .where(id, isEqualTo(row::getId))
        );
    }
}