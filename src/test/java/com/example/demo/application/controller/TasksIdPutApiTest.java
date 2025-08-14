package com.example.demo.application.controller;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.demo.generated.application.dto.TaskDto;
import com.example.demo.generated.application.dto.TaskRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.config.TestConfig;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestConfig.class)
@DbUnitConfiguration(databaseConnection = "dbUnitDatabaseConnection")
@TestExecutionListeners(value = {
    DependencyInjectionTestExecutionListener.class,
    TransactionDbUnitTestExecutionListener.class
})
public class TasksIdPutApiTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Test
  @DatabaseSetup("/datasets/tasks/two-tasks.xml")
  @ExpectedDatabase(value = "/datasets/tasks/updated-two-tasks.xml", table = "TASKS")
  void tasksIdPut_shouldUpdateTask_whenRequestValid() throws Exception {

    TaskRequest requestDto = new TaskRequest();
    requestDto.setTitle("DBUnitのタスク1");
    requestDto.setDescription("更新されました。");
    requestDto.setCompleted(true);
    OffsetDateTime expectedUtc = OffsetDateTime.of(2025, 10, 1, 12, 0, 0, 0, ZoneOffset.UTC);
    requestDto.setDueDate(expectedUtc);

    String requestJson = objectMapper.writeValueAsString(requestDto);

    // When & Then
    MvcResult result = mockMvc.perform(put("/tasks/100") // 存在するID=100を更新
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestJson))
        .andExpect(status().isOk())
        .andReturn();

    String responseJson = result.getResponse().getContentAsString();
    TaskDto responseDto = objectMapper.readValue(responseJson, TaskDto.class);

    assertThat(responseDto.getId()).isNotNull();
    assertThat(responseDto.getTitle()).isEqualTo("DBUnitのタスク1");
    assertThat(responseDto.getDescription()).isEqualTo("更新されました。");
    assertThat(responseDto.getCompleted()).isEqualTo(true);
    assertThat(responseDto.getDueDate().toInstant()).isEqualTo(expectedUtc.toInstant());
  }

  // 不正なリクエスト
  @Test
  @DatabaseSetup("/datasets/tasks/two-tasks.xml")
  @ExpectedDatabase(value = "/datasets/tasks/two-tasks.xml", table = "TASKS")
  void tasksIdPut_shouldReturnNotFound_whenIdDoesNotExist() throws Exception {
    // Given: 有効なリクエストボディ
    TaskRequest requestDto = new TaskRequest();
    requestDto.setTitle("このデータは保存されない");
    requestDto.setDueDate(OffsetDateTime.now().plusDays(1));
    String requestJson = objectMapper.writeValueAsString(requestDto);

    // When & Then: 存在しないID=999でAPIを呼び出し、404エラーを検証
    mockMvc.perform(put("/tasks/999")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestJson))
        .andExpect(status().isNotFound());
  }

  // バリデーションテスト
  @Test
  @DatabaseSetup("/datasets/tasks/two-tasks.xml")
  @ExpectedDatabase(value = "/datasets/tasks/two-tasks.xml", table = "TASKS")
  void tasksIdPut_shouldReturnBadRequest_whenTitleIsEmpty() throws Exception {

    Map<String, Object> taskFromDb = jdbcTemplate.queryForMap("SELECT * FROM tasks WHERE id = 100");
    OffsetDateTime dueDateFromDb = (OffsetDateTime) taskFromDb.get("DUE_DATE");
    // OffsetDateTime dueDateFromDb =
    // dueDateTimestamp.toInstant().atOffset(ZoneOffset.ofHours(9));
    // DTOオブジェクトを直接作成
    TaskRequest requestDto = new TaskRequest();
    requestDto.setTitle(""); // テストしたい不正な値
    requestDto.setDueDate(dueDateFromDb);

    // DTOをJSON文字列に変換
    String invalidJson = objectMapper.writeValueAsString(requestDto);

    mockMvc.perform(put("/tasks/100")
        .contentType(MediaType.APPLICATION_JSON)
        .content(invalidJson))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DatabaseSetup("/datasets/tasks/two-tasks.xml")
  @ExpectedDatabase(value = "/datasets/tasks/two-tasks.xml", table = "TASKS")
  void tasksIdPutt_shouldReturnBadRequest_whenTitleIsNull() throws Exception {

    Map<String, Object> taskFromDb = jdbcTemplate.queryForMap("SELECT * FROM tasks WHERE id = 100");
    OffsetDateTime dueDateFromDb = (OffsetDateTime) taskFromDb.get("DUE_DATE");
    // OffsetDateTime dueDateFromDb =
    // dueDateTimestamp.toInstant().atOffset(ZoneOffset.ofHours(9));
    // DTOオブジェクトを直接作成
    TaskRequest requestDto = new TaskRequest();
    requestDto.setDueDate(dueDateFromDb);

    // DTOをJSON文字列に変換
    String invalidJson = objectMapper.writeValueAsString(requestDto);

    mockMvc.perform(put("/tasks/100")
        .contentType(MediaType.APPLICATION_JSON)
        .content(invalidJson))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DatabaseSetup("/datasets/tasks/two-tasks.xml")
  @ExpectedDatabase(value = "/datasets/tasks/two-tasks.xml", table = "TASKS")
  void tasksIdPutt_shouldReturnBadRequest_whenTitleLengthIsOver20() throws Exception {

    Map<String, Object> taskFromDb = jdbcTemplate.queryForMap("SELECT * FROM tasks WHERE id = 100");
    OffsetDateTime dueDateFromDb = (OffsetDateTime) taskFromDb.get("DUE_DATE");
    // OffsetDateTime dueDateFromDb =
    // dueDateTimestamp.toInstant().atOffset(ZoneOffset.ofHours(9));
    // DTOオブジェクトを直接作成
    TaskRequest requestDto = new TaskRequest();
    requestDto.setTitle("123456789012345678901"); // テストしたい不正な値
    requestDto.setDueDate(dueDateFromDb);

    // DTOをJSON文字列に変換
    String invalidJson = objectMapper.writeValueAsString(requestDto);

    mockMvc.perform(put("/tasks/100")
        .contentType(MediaType.APPLICATION_JSON)
        .content(invalidJson))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DatabaseSetup("/datasets/tasks/two-tasks.xml")
  @ExpectedDatabase(value = "/datasets/tasks/two-tasks.xml", table = "TASKS")
  void tasksIdPutt_shouldReturnBadRequest_whenDescriptionLengthIsOver50() throws Exception {

    Map<String, Object> taskFromDb = jdbcTemplate.queryForMap("SELECT * FROM tasks WHERE id = 100");
    OffsetDateTime dueDateFromDb = (OffsetDateTime) taskFromDb.get("DUE_DATE");
    // OffsetDateTime dueDateFromDb =
    // dueDateTimestamp.toInstant().atOffset(ZoneOffset.ofHours(9));
    // DTOオブジェクトを直接作成
    TaskRequest requestDto = new TaskRequest();
    requestDto.setTitle("test"); // テストしたい不正な値
    requestDto.setDescription("あいうえおかきくけこさしすせそたちつてとなにぬねのはひふへほまみむめもおやいゆえよらりるれろわいうえおあ");
    requestDto.setDueDate(dueDateFromDb);

    // DTOをJSON文字列に変換
    String invalidJson = objectMapper.writeValueAsString(requestDto);

    mockMvc.perform(put("/tasks/100")
        .contentType(MediaType.APPLICATION_JSON)
        .content(invalidJson))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DatabaseSetup("/datasets/tasks/two-tasks.xml")
  @ExpectedDatabase(value = "/datasets/tasks/two-tasks.xml", table = "TASKS")
  void tasksIdPutt_shouldReturnBadRequest_whenDescriptionContainHalfWidthCharacters() throws Exception {

    Map<String, Object> taskFromDb = jdbcTemplate.queryForMap("SELECT * FROM tasks WHERE id = 100");
    OffsetDateTime dueDateFromDb = (OffsetDateTime) taskFromDb.get("DUE_DATE");
    // OffsetDateTime dueDateFromDb =
    // dueDateTimestamp.toInstant().atOffset(ZoneOffset.ofHours(9));
    // DTOオブジェクトを直接作成
    TaskRequest requestDto = new TaskRequest();
    requestDto.setTitle("test"); // テストしたい不正な値
    requestDto.setDescription("abc");
    requestDto.setDueDate(dueDateFromDb);

    // DTOをJSON文字列に変換
    String invalidJson = objectMapper.writeValueAsString(requestDto);

    mockMvc.perform(put("/tasks/100")
        .contentType(MediaType.APPLICATION_JSON)
        .content(invalidJson))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DatabaseSetup("/datasets/tasks/two-tasks.xml")
  @ExpectedDatabase(value = "/datasets/tasks/two-tasks.xml", table = "TASKS")
  void tasksIdPutt_shouldReturnBadRequest_whenDueDateIsNull() throws Exception {

    // DTOオブジェクトを直接作成
    TaskRequest requestDto = new TaskRequest();
    requestDto.setTitle("test");

    // DTOをJSON文字列に変換
    String invalidJson = objectMapper.writeValueAsString(requestDto);

    mockMvc.perform(put("/tasks/100")
        .contentType(MediaType.APPLICATION_JSON)
        .content(invalidJson))
        .andExpect(status().isBadRequest());
  }

  // 元の期日より前を選択したときのテスト
  @Test
  @DatabaseSetup("/datasets/tasks/two-tasks.xml")
  @ExpectedDatabase(value = "/datasets/tasks/two-tasks.xml", table = "TASKS")
  void tasksIdPut_shouldReturnBadRequest_whenDueDateIsPast() throws Exception {

    Map<String, Object> taskFromDb = jdbcTemplate.queryForMap("SELECT * FROM tasks WHERE id = 100");
    OffsetDateTime dueDateFromDb = (OffsetDateTime) taskFromDb.get("DUE_DATE");
    OffsetDateTime pastDueDate = dueDateFromDb.minusSeconds(1);
    // OffsetDateTime dueDateFromDb =
    // dueDateTimestamp.toInstant().atOffset(ZoneOffset.ofHours(9)).minusSeconds(1);

    // DTOオブジェクトを直接作成
    TaskRequest requestDto = new TaskRequest();
    requestDto.setTitle("test"); // テストしたい不正な値
    requestDto.setDueDate(pastDueDate);

    // DTOをJSON文字列に変換
    String invalidJson = objectMapper.writeValueAsString(requestDto);

    mockMvc.perform(put("/tasks/100")
        .contentType(MediaType.APPLICATION_JSON)
        .content(invalidJson))
        .andExpect(status().isBadRequest());
  }

}
