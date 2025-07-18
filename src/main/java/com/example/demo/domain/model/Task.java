package com.example.demo.domain.model;

import java.util.Date;
import lombok.*;

@Getter
@Setter
public class Task {
    private Long id ;
    private String title ;
    private String description ;
    private boolean isCompleted ;
    private Date dueDate ;
}