package com.example.demo.application.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
@TestExecutionListeners(listeners = {
    DependencyInjectionTestExecutionListener.class,
    TransactionDbUnitTestExecutionListener.class
})
public class TasksIdDeleteApiTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  @DatabaseSetup("/datasets/tasks/one-task.xml")
  @ExpectedDatabase(value = "/datasets/tasks/no-task.xml", table = "TASKS")
  void tasksIdDelete_shouldDeleteTask_whenIdExists() throws Exception {
    // When & Then: 存在するID(100)でAPIを呼び出し、204 No Contentが返ることを検証
    mockMvc.perform(delete("/tasks/1"))
        .andExpect(status().isNoContent());
  }

  @Test
  @DatabaseSetup("/datasets/tasks/one-task.xml")
  @ExpectedDatabase(value = "/datasets/tasks/one-task.xml", table = "TASKS")
  void tasksIdDelete_shouldDeleteTask_whenIdDoesNotExist() throws Exception {

    mockMvc.perform(delete("/tasks/2"))
        .andExpect(status().isNotFound());
  }

}
