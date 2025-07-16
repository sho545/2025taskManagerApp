package com.example.demo.infrastructure.mapper;

import static com.example.demo.infrastructure.mapper.TaskDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

import com.example.demo.domain.model.Task;
import jakarta.annotation.Generated;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.apache.ibatis.annotations.Mapper;
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
public interface TaskMapper extends CommonCountMapper, CommonDeleteMapper, CommonInsertMapper<Task>, CommonUpdateMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-15T15:28:19.2129949+09:00", comments="Source Table: TASKS")
    BasicColumn[] selectList = BasicColumn.columnList(id, title, description, isCompleted, dueDate);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-15T15:28:19.1995899+09:00", comments="Source Table: TASKS")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="TaskResult", value = {
        @Result(column="ID", property="id", jdbcType=JdbcType.OTHER, id=true),
        @Result(column="TITLE", property="title", jdbcType=JdbcType.VARCHAR),
        @Result(column="DESCRIPTION", property="description", jdbcType=JdbcType.VARCHAR),
        @Result(column="IS_COMPLETED", property="isCompleted", jdbcType=JdbcType.BOOLEAN),
        @Result(column="DUE_DATE", property="dueDate", jdbcType=JdbcType.TIMESTAMP_WITH_TIMEZONE)
    })
    List<Task> selectMany(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-15T15:28:19.2025969+09:00", comments="Source Table: TASKS")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("TaskResult")
    Optional<Task> selectOne(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-15T15:28:19.2035936+09:00", comments="Source Table: TASKS")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, task, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-15T15:28:19.2045922+09:00", comments="Source Table: TASKS")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, task, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-15T15:28:19.2056769+09:00", comments="Source Table: TASKS")
    default int deleteByPrimaryKey(UUID id_) {
        return delete(c -> 
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-15T15:28:19.2056769+09:00", comments="Source Table: TASKS")
    default int insert(Task row) {
        return MyBatis3Utils.insert(this::insert, row, task, c ->
            c.map(id).toProperty("id")
            .map(title).toProperty("title")
            .map(description).toProperty("description")
            .map(isCompleted).toProperty("isCompleted")
            .map(dueDate).toProperty("dueDate")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-15T15:28:19.2100021+09:00", comments="Source Table: TASKS")
    default int insertMultiple(Collection<Task> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, task, c ->
            c.map(id).toProperty("id")
            .map(title).toProperty("title")
            .map(description).toProperty("description")
            .map(isCompleted).toProperty("isCompleted")
            .map(dueDate).toProperty("dueDate")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-15T15:28:19.2110004+09:00", comments="Source Table: TASKS")
    default int insertSelective(Task row) {
        return MyBatis3Utils.insert(this::insert, row, task, c ->
            c.map(id).toPropertyWhenPresent("id", row::getId)
            .map(title).toPropertyWhenPresent("title", row::getTitle)
            .map(description).toPropertyWhenPresent("description", row::getDescription)
            .map(isCompleted).toPropertyWhenPresent("isCompleted", row::getIsCompleted)
            .map(dueDate).toPropertyWhenPresent("dueDate", row::getDueDate)
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-15T15:28:19.2149893+09:00", comments="Source Table: TASKS")
    default Optional<Task> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, task, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-15T15:28:19.2149893+09:00", comments="Source Table: TASKS")
    default List<Task> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, task, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-15T15:28:19.2159865+09:00", comments="Source Table: TASKS")
    default List<Task> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, task, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-15T15:28:19.2159865+09:00", comments="Source Table: TASKS")
    default Optional<Task> selectByPrimaryKey(UUID id_) {
        return selectOne(c ->
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-15T15:28:19.2172339+09:00", comments="Source Table: TASKS")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, task, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-15T15:28:19.2183883+09:00", comments="Source Table: TASKS")
    static UpdateDSL<UpdateModel> updateAllColumns(Task row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(id).equalTo(row::getId)
                .set(title).equalTo(row::getTitle)
                .set(description).equalTo(row::getDescription)
                .set(isCompleted).equalTo(row::getIsCompleted)
                .set(dueDate).equalTo(row::getDueDate);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-15T15:28:19.2183883+09:00", comments="Source Table: TASKS")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(Task row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(id).equalToWhenPresent(row::getId)
                .set(title).equalToWhenPresent(row::getTitle)
                .set(description).equalToWhenPresent(row::getDescription)
                .set(isCompleted).equalToWhenPresent(row::getIsCompleted)
                .set(dueDate).equalToWhenPresent(row::getDueDate);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-15T15:28:19.2204176+09:00", comments="Source Table: TASKS")
    default int updateByPrimaryKey(Task row) {
        return update(c ->
            c.set(title).equalTo(row::getTitle)
            .set(description).equalTo(row::getDescription)
            .set(isCompleted).equalTo(row::getIsCompleted)
            .set(dueDate).equalTo(row::getDueDate)
            .where(id, isEqualTo(row::getId))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-15T15:28:19.2204176+09:00", comments="Source Table: TASKS")
    default int updateByPrimaryKeySelective(Task row) {
        return update(c ->
            c.set(title).equalToWhenPresent(row::getTitle)
            .set(description).equalToWhenPresent(row::getDescription)
            .set(isCompleted).equalToWhenPresent(row::getIsCompleted)
            .set(dueDate).equalToWhenPresent(row::getDueDate)
            .where(id, isEqualTo(row::getId))
        );
    }
}