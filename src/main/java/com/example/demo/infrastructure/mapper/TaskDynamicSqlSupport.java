package com.example.demo.infrastructure.mapper;

import jakarta.annotation.Generated;
import java.sql.JDBCType;
import java.util.Date;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.SqlColumn;

public final class TaskDynamicSqlSupport {

    private TaskDynamicSqlSupport(){}

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-08T13:11:20.3173304+09:00", comments="Source Table: TASKS")
    public static final Task task = new Task();

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-08T13:11:20.3173304+09:00", comments="Source field: TASKS.ID")
    public static final SqlColumn<Long> id = task.id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-08T13:11:20.3183271+09:00", comments="Source field: TASKS.TITLE")
    public static final SqlColumn<String> title = task.title;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-08T13:11:20.3183271+09:00", comments="Source field: TASKS.DESCRIPTION")
    public static final SqlColumn<String> description = task.description;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-08T13:11:20.3183271+09:00", comments="Source field: TASKS.IS_COMPLETED")
    public static final SqlColumn<Boolean> isCompleted = task.isCompleted;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-08T13:11:20.3183271+09:00", comments="Source field: TASKS.DUE_DATE")
    public static final SqlColumn<Date> dueDate = task.dueDate;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-08T13:11:20.3173304+09:00", comments="Source Table: TASKS")
    public static final class Task extends AliasableSqlTable<Task> {
        public final SqlColumn<Long> id = column("ID", JDBCType.BIGINT);

        public final SqlColumn<String> title = column("TITLE", JDBCType.VARCHAR);

        public final SqlColumn<String> description = column("DESCRIPTION", JDBCType.VARCHAR);

        public final SqlColumn<Boolean> isCompleted = column("IS_COMPLETED", JDBCType.BOOLEAN);

        public final SqlColumn<Date> dueDate = column("DUE_DATE", JDBCType.TIMESTAMP);

        public Task() {
            super("TASKS", Task::new);
        }
    }
}