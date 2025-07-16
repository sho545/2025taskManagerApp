package com.example.demo.infrastructure.mapper;

import jakarta.annotation.Generated;
import java.sql.JDBCType;
import java.time.OffsetDateTime;
import java.util.UUID;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.SqlColumn;

public final class TaskDynamicSqlSupport {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-15T15:28:19.1945083+09:00", comments="Source Table: TASKS")
    public static final Task task = new Task();

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-15T15:28:19.1975735+09:00", comments="Source field: TASKS.ID")
    public static final SqlColumn<UUID> id = task.id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-15T15:28:19.1985698+09:00", comments="Source field: TASKS.TITLE")
    public static final SqlColumn<String> title = task.title;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-15T15:28:19.1985698+09:00", comments="Source field: TASKS.DESCRIPTION")
    public static final SqlColumn<String> description = task.description;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-15T15:28:19.1985698+09:00", comments="Source field: TASKS.IS_COMPLETED")
    public static final SqlColumn<Boolean> isCompleted = task.isCompleted;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-15T15:28:19.1985698+09:00", comments="Source field: TASKS.DUE_DATE")
    public static final SqlColumn<OffsetDateTime> dueDate = task.dueDate;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-15T15:28:19.1965715+09:00", comments="Source Table: TASKS")
    public static final class Task extends AliasableSqlTable<Task> {
        public final SqlColumn<UUID> id = column("ID", JDBCType.OTHER);

        public final SqlColumn<String> title = column("TITLE", JDBCType.VARCHAR);

        public final SqlColumn<String> description = column("DESCRIPTION", JDBCType.VARCHAR);

        public final SqlColumn<Boolean> isCompleted = column("IS_COMPLETED", JDBCType.BOOLEAN);

        public final SqlColumn<OffsetDateTime> dueDate = column("DUE_DATE", JDBCType.TIMESTAMP_WITH_TIMEZONE);

        public Task() {
            super("TASKS", Task::new);
        }
    }
}