package com.example.demo.application.controller;

import com.example.demo.generated.application.dto.TaskDto;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@TestExecutionListeners(value = {
    DependencyInjectionTestExecutionListener.class,
    TransactionDbUnitTestExecutionListener.class
})
public class TasksGetApiTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  @DatabaseSetup("/datasets/tasks/two-tasks.xml")
  @ExpectedDatabase(value = "/datasets/tasks/two-tasks.xml", table = "TASKS")
  public void tasksGet_shouldReturnListOfTasks() throws Exception {
    // mockMvc.perform(get("/tasks"))
    // .andExpect(status().isOk())
    // .andExpect(jsonPath("$", hasSize(2)))
    // .andExpect(jsonPath("$[0].title").value("DBUnitのタスク1"))
    // .andExpect(jsonPath("$[0].description").value("これはセットアップされました。"))
    // .andExpect(jsonPath("$[0].completed").value(false))
    // .andExpect(jsonPath("$[0].dueDate").value("2025-10-01T12:00:00Z"))
    // .andExpect(jsonPath("$[1].title").value("DBUnitのタスク2"))
    // .andExpect(jsonPath("$[1].description").value("これもセットされたデータです。"))
    // .andExpect(jsonPath("$[1].completed").value(true))
    // .andExpect(jsonPath("$[1].dueDate").value("2025-10-02T12:00:00Z"));
    MvcResult result = mockMvc.perform(get("/tasks"))
        .andExpect(status().isOk())
        .andReturn();

    // JSON を DTO に変換
    String responseJson = result.getResponse().getContentAsString();
    List<TaskDto> tasks = objectMapper.readValue(
        responseJson,
        new TypeReference<List<TaskDto>>() {
        });

    assertThat(tasks).hasSize(2);

    TaskDto task1 = tasks.get(0);
    assertThat(task1.getTitle()).isEqualTo("DBUnitのタスク1");
    assertThat(task1.getDescription()).isEqualTo("これはセットアップされました。");
    assertThat(task1.getCompleted()).isFalse();
    assertThat(task1.getDueDate().toInstant())
        .isEqualTo(OffsetDateTime.of(2025, 10, 1, 12, 0, 0, 0, ZoneOffset.UTC).toInstant());

    TaskDto task2 = tasks.get(1);
    assertThat(task2.getTitle()).isEqualTo("DBUnitのタスク2");
    assertThat(task2.getDescription()).isEqualTo("これもセットされたデータです。");
    assertThat(task2.getCompleted()).isTrue();
    assertThat(task2.getDueDate().toInstant())
        .isEqualTo(OffsetDateTime.of(2025, 10, 2, 12, 0, 0, 0, ZoneOffset.UTC).toInstant());
  }

  @Test
  @DatabaseSetup("/datasets/tasks/no-task.xml")
  @ExpectedDatabase(value = "/datasets/tasks/no-task.xml", table = "TASKS")
  public void tasksGet_shouldReturnEmptyListWhenNoTasks() throws Exception {
    mockMvc.perform(get("/tasks"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(0)));
  }

}
