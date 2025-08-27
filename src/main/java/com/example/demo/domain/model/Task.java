package com.example.demo.domain.model;

import java.time.OffsetDateTime;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor // 引数なしコンストラクタを追加
@AllArgsConstructor // 全引数コンストラクタを追加
public class Task {
    private Long id;
    private String title;
    private String description;
    private boolean isCompleted;
    private OffsetDateTime dueDate;
}