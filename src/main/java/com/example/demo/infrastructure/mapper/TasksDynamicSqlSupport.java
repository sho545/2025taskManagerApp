package com.example.demo.infrastructure.mapper;

import jakarta.annotation.Generated;
import java.sql.JDBCType;
import java.util.Date;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.SqlColumn;

public final class TasksDynamicSqlSupport {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-07T09:36:45.6974718+09:00", comments="Source Table: TASKS")
    public static final Tasks tasks = new Tasks();

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-07T09:36:45.6974718+09:00", comments="Source field: TASKS.ID")
    public static final SqlColumn<Long> id = tasks.id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-07T09:36:45.6984694+09:00", comments="Source field: TASKS.TITLE")
    public static final SqlColumn<String> title = tasks.title;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-07T09:36:45.6984694+09:00", comments="Source field: TASKS.DESCRIPTION")
    public static final SqlColumn<String> description = tasks.description;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-07T09:36:45.6984694+09:00", comments="Source field: TASKS.IS_COMPLETED")
    public static final SqlColumn<Boolean> isCompleted = tasks.isCompleted;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-07T09:36:45.6984694+09:00", comments="Source field: TASKS.DUE_DATE")
    public static final SqlColumn<Date> dueDate = tasks.dueDate;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-07T09:36:45.6974718+09:00", comments="Source Table: TASKS")
    public static final class Tasks extends AliasableSqlTable<Tasks> {
        public final SqlColumn<Long> id = column("ID", JDBCType.BIGINT);

        public final SqlColumn<String> title = column("TITLE", JDBCType.VARCHAR);

        public final SqlColumn<String> description = column("DESCRIPTION", JDBCType.VARCHAR);

        public final SqlColumn<Boolean> isCompleted = column("IS_COMPLETED", JDBCType.BOOLEAN);

        public final SqlColumn<Date> dueDate = column("DUE_DATE", JDBCType.TIMESTAMP);

        public Tasks() {
            super("TASKS", Tasks::new);
        }
    }
}