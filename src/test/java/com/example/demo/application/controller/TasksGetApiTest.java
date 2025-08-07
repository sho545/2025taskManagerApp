package com.example.demo.application.controller;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestExecutionListeners(value = {
    // 1. DIコンテナを準備するリスナー
    DependencyInjectionTestExecutionListener.class,
    TransactionDbUnitTestExecutionListener.class
})
public class TasksGetApiTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  @DatabaseSetup("/datasets/tasks/two-tasks.xml")
  @ExpectedDatabase(value = "/datasets/tasks/two-tasks.xml", table = "TASKS")
  public void tasksGet_shouldReturnListOfTasks() throws Exception {
    mockMvc.perform(get("/tasks"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].title").value("DBUnitのタスク1"))
        .andExpect(jsonPath("$[0].description").value("これはセットアップされました。"))
        .andExpect(jsonPath("$[0].completed").value(false))
        .andExpect(jsonPath("$[0].dueDate").value("2025-10-01T19:00:00+09:00"))
        .andExpect(jsonPath("$[1].title").value("DBUnitのタスク2"))
        .andExpect(jsonPath("$[1].description").value("これもセットされたデータです。"))
        .andExpect(jsonPath("$[1].completed").value(true))
        .andExpect(jsonPath("$[1].dueDate").value("2025-10-02T19:00:00+09:00"));
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
