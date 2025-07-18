package com.example.demo.generated.infrastructure.entity;

import jakarta.annotation.Generated;
import java.util.Date;

public class TaskEntity {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-18T10:25:42.9769594+09:00", comments="Source field: TASKS.ID")
    private Long id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-18T10:25:42.9791887+09:00", comments="Source field: TASKS.TITLE")
    private String title;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-18T10:25:42.9791887+09:00", comments="Source field: TASKS.DESCRIPTION")
    private String description;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-18T10:25:42.9791887+09:00", comments="Source field: TASKS.IS_COMPLETED")
    private Boolean isCompleted;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-18T10:25:42.9791887+09:00", comments="Source field: TASKS.DUE_DATE")
    private Date dueDate;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-18T10:25:42.9781886+09:00", comments="Source field: TASKS.ID")
    public Long getId() {
        return id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-18T10:25:42.9781886+09:00", comments="Source field: TASKS.ID")
    public void setId(Long id) {
        this.id = id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-18T10:25:42.9791887+09:00", comments="Source field: TASKS.TITLE")
    public String getTitle() {
        return title;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-18T10:25:42.9791887+09:00", comments="Source field: TASKS.TITLE")
    public void setTitle(String title) {
        this.title = title;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-18T10:25:42.9791887+09:00", comments="Source field: TASKS.DESCRIPTION")
    public String getDescription() {
        return description;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-18T10:25:42.9791887+09:00", comments="Source field: TASKS.DESCRIPTION")
    public void setDescription(String description) {
        this.description = description;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-18T10:25:42.9791887+09:00", comments="Source field: TASKS.IS_COMPLETED")
    public Boolean getIsCompleted() {
        return isCompleted;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-18T10:25:42.9791887+09:00", comments="Source field: TASKS.IS_COMPLETED")
    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-18T10:25:42.9791887+09:00", comments="Source field: TASKS.DUE_DATE")
    public Date getDueDate() {
        return dueDate;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-07-18T10:25:42.9791887+09:00", comments="Source field: TASKS.DUE_DATE")
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
}