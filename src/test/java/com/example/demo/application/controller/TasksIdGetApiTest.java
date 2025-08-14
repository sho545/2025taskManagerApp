package com.example.demo.application.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.demo.generated.application.dto.TaskDto;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@TestExecutionListeners(value = {
    DependencyInjectionTestExecutionListener.class,
    TransactionDbUnitTestExecutionListener.class
})
public class TasksIdGetApiTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  // tasksIdGetのテスト
  @Test
  @DatabaseSetup("/datasets/tasks/two-tasks.xml")
  @ExpectedDatabase(value = "/datasets/tasks/two-tasks.xml", table = "TASKS")
  void tasksGet_shouldReturnTask_whenIdExists() throws Exception {
    // mockMvc.perform(get("/tasks/100"))
    // .andExpect(status().isOk())
    // .andExpect(jsonPath("$.id", is(100)))
    // .andExpect(jsonPath("$.title", is("DBUnitのタスク1")))
    // .andExpect(jsonPath("$.description", is("これはセットアップされました。")))
    // .andExpect(jsonPath("$.completed", is(false)))
    // .andExpect(jsonPath("$.dueDate", is("2025-10-01T12:00:00+09:00")));
    MvcResult result = mockMvc.perform(get("/tasks/100"))
        .andExpect(status().isOk())
        .andReturn();

    String responseJson = result.getResponse().getContentAsString();
    TaskDto taskDto = objectMapper.readValue(responseJson, TaskDto.class);

    assertThat(taskDto.getId()).isEqualTo(100);
    assertThat(taskDto.getTitle()).isEqualTo("DBUnitのタスク1");
    assertThat(taskDto.getDescription()).isEqualTo("これはセットアップされました。");
    assertThat(taskDto.getCompleted()).isFalse();

    // タイムゾーンに依存せずに UTC で比較
    OffsetDateTime expectedUtc = OffsetDateTime.of(2025, 10, 1, 12, 0, 0, 0, ZoneOffset.UTC);
    assertThat(taskDto.getDueDate().toInstant()).isEqualTo(expectedUtc.toInstant());
  }

  // tasksIdGetでタスクがなかった時のテスト
  @Test
  @DatabaseSetup("/datasets/tasks/no-task.xml")
  void tasksGet_shouldReturnNotFound_whenIdDoesNotExist() throws Exception {
    mockMvc.perform(get("/tasks/999"))
        .andExpect(status().isNotFound());
  }

}
