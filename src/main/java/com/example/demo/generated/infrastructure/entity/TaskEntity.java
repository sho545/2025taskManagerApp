package com.example.demo.generated.infrastructure.entity;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class TaskEntity {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-23T13:06:46.0154451+09:00", comments="Source field: TASKS.ID")
    private Long id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-23T13:06:46.0175235+09:00", comments="Source field: TASKS.TITLE")
    private String title;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-23T13:06:46.0175235+09:00", comments="Source field: TASKS.DESCRIPTION")
    private String description;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-23T13:06:46.0175235+09:00", comments="Source field: TASKS.IS_COMPLETED")
    private Boolean isCompleted;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-23T13:06:46.0175235+09:00", comments="Source field: TASKS.DUE_DATE")
    private OffsetDateTime dueDate;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-23T13:06:46.0164826+09:00", comments="Source field: TASKS.ID")
    public Long getId() {
        return id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-23T13:06:46.0175235+09:00", comments="Source field: TASKS.ID")
    public void setId(Long id) {
        this.id = id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-23T13:06:46.0175235+09:00", comments="Source field: TASKS.TITLE")
    public String getTitle() {
        return title;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-23T13:06:46.0175235+09:00", comments="Source field: TASKS.TITLE")
    public void setTitle(String title) {
        this.title = title;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-23T13:06:46.0175235+09:00", comments="Source field: TASKS.DESCRIPTION")
    public String getDescription() {
        return description;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-23T13:06:46.0175235+09:00", comments="Source field: TASKS.DESCRIPTION")
    public void setDescription(String description) {
        this.description = description;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-23T13:06:46.0175235+09:00", comments="Source field: TASKS.IS_COMPLETED")
    public Boolean getIsCompleted() {
        return isCompleted;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-23T13:06:46.0175235+09:00", comments="Source field: TASKS.IS_COMPLETED")
    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-23T13:06:46.0175235+09:00", comments="Source field: TASKS.DUE_DATE")
    public OffsetDateTime getDueDate() {
        return dueDate;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-23T13:06:46.0175235+09:00", comments="Source field: TASKS.DUE_DATE")
    public void setDueDate(OffsetDateTime dueDate) {
        this.dueDate = dueDate;
    }
}