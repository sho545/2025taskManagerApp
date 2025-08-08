package com.example.demo.application.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;

@SpringBootTest
@AutoConfigureMockMvc
@TestExecutionListeners(value = {
    DependencyInjectionTestExecutionListener.class,
    TransactionDbUnitTestExecutionListener.class
})
public class TasksIdGetApiTest {

  @Autowired
  private MockMvc mockMvc;

  // tasksIdGetのテスト
  @Test
  @DatabaseSetup("/datasets/tasks/two-tasks.xml")
  @ExpectedDatabase(value = "/datasets/tasks/two-tasks.xml", table = "TASKS")
  void tasksGet_shouldReturnTask_whenIdExists() throws Exception {
    mockMvc.perform(get("/tasks/100"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(100)))
        .andExpect(jsonPath("$.title", is("DBUnitのタスク1")))
        .andExpect(jsonPath("$.description", is("これはセットアップされました。")))
        .andExpect(jsonPath("$.completed", is(false)))
        .andExpect(jsonPath("$.dueDate", is("2025-10-01T12:00:00+09:00"))); // Dbから取り出すときにPCのタイムゾーンで取り出しているから日本時間として比較しています
  }

  // tasksIdGetでタスクがなかった時のテスト
  @Test
  @DatabaseSetup("/datasets/tasks/no-task.xml")
  void tasksGet_shouldReturnNotFound_whenIdDoesNotExist() throws Exception {
    mockMvc.perform(get("/tasks/999"))
        .andExpect(status().isNotFound());
  }

}
