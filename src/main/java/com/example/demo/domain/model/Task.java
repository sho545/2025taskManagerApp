package com.example.demo.domain.model;

import java.time.OffsetDateTime;
import lombok.*;

@Getter
@Setter
public class Task {
    private Long id ;
    private String title ;
    private String description ;
    private boolean isCompleted ;
    private OffsetDateTime dueDate ;
}