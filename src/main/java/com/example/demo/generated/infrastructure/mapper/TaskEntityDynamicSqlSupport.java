package com.example.demo.generated.infrastructure.mapper;

import jakarta.annotation.Generated;
import java.sql.JDBCType;
import java.time.OffsetDateTime;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.SqlColumn;

public final class TaskEntityDynamicSqlSupport {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-23T13:27:45.2368566+09:00", comments="Source Table: TASKS")
    public static final TaskEntity taskEntity = new TaskEntity();

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-23T13:27:45.2378541+09:00", comments="Source field: TASKS.ID")
    public static final SqlColumn<Long> id = taskEntity.id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-23T13:27:45.2387509+09:00", comments="Source field: TASKS.TITLE")
    public static final SqlColumn<String> title = taskEntity.title;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-23T13:27:45.2387509+09:00", comments="Source field: TASKS.DESCRIPTION")
    public static final SqlColumn<String> description = taskEntity.description;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-23T13:27:45.2387509+09:00", comments="Source field: TASKS.IS_COMPLETED")
    public static final SqlColumn<Boolean> isCompleted = taskEntity.isCompleted;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-23T13:27:45.2387509+09:00", comments="Source field: TASKS.DUE_DATE")
    public static final SqlColumn<OffsetDateTime> dueDate = taskEntity.dueDate;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-23T13:27:45.2378541+09:00", comments="Source Table: TASKS")
    public static final class TaskEntity extends AliasableSqlTable<TaskEntity> {
        public final SqlColumn<Long> id = column("ID", JDBCType.BIGINT);

        public final SqlColumn<String> title = column("TITLE", JDBCType.VARCHAR);

        public final SqlColumn<String> description = column("DESCRIPTION", JDBCType.VARCHAR);

        public final SqlColumn<Boolean> isCompleted = column("IS_COMPLETED", JDBCType.BOOLEAN);

        public final SqlColumn<OffsetDateTime> dueDate = column("DUE_DATE", JDBCType.TIMESTAMP_WITH_TIMEZONE);

        public TaskEntity() {
            super("TASKS", TaskEntity::new);
        }
    }
}