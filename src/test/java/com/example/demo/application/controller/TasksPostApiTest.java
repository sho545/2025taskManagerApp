package com.example.demo.application.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.sql.Timestamp;

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

import com.example.demo.TestConfig;
import com.example.demo.generated.application.dto.TaskDto;
import com.example.demo.generated.application.dto.TaskRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestConfig.class)
@TestExecutionListeners(value = {
    DependencyInjectionTestExecutionListener.class,
    TransactionDbUnitTestExecutionListener.class
})
public class TasksPostApiTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Test
  @DatabaseSetup("/datasets/tasks/no-task.xml")
  void taskPost_shouldCreateTask_whenRequestIsValid() throws Exception {

    TaskRequest requestDto = new TaskRequest();
    requestDto.setTitle("新しいタスク");
    OffsetDateTime futureTime = OffsetDateTime.now().plusMinutes(1);
    requestDto.setDueDate(futureTime);

    String validJson = objectMapper.writeValueAsString(requestDto);

    MvcResult result = mockMvc.perform(post("/tasks")
        .contentType(MediaType.APPLICATION_JSON)
        .content(validJson))
        .andExpect(status().isCreated())
        .andReturn();

    String responseJson = result.getResponse().getContentAsString();
    TaskDto responseDto = objectMapper.readValue(responseJson, TaskDto.class);

    // DTOオブジェクトのフィールドを検証
    assertThat(responseDto.getId()).isNotNull();
    assertThat(responseDto.getTitle()).isEqualTo("新しいタスク");
    assertThat(responseDto.getCompleted()).isEqualTo(false);

    // OffsetDateTimeオブジェクト同士を、瞬間が同じかで比較
    assertThat(responseDto.getDueDate().toInstant()).isEqualTo(futureTime.toInstant());

    // データベースに挿入されたデータが想定されたものかを確認
    // 1. レスポンスのIDを取得
    Integer newTaskId = objectMapper.readTree(responseJson).get("id").asInt();
    // 2. 取得したIDを使ってDBを直接検証
    Map<String, Object> taskFromDb = jdbcTemplate.queryForMap("SELECT * FROM tasks WHERE id = ?", newTaskId);

    assertThat(taskFromDb.get("TITLE")).isEqualTo("新しいタスク");
    assertThat(taskFromDb.get("IS_COMPLETED")).isEqualTo(false);

    // 1. Mapからは、まず古いTimestamp型として値を取り出す
    Timestamp dueDateTimestamp = (Timestamp) taskFromDb.get("DUE_DATE");

    // 2. それをInstant経由で、新しいOffsetDateTime型に変換する
    OffsetDateTime dueDateFromDb = dueDateTimestamp.toInstant().atOffset(futureTime.getOffset());

    // 実行時のわずかな誤差を許容するため、2秒以内であればOKとする
    assertThat(dueDateFromDb).isCloseTo(futureTime, within(2, ChronoUnit.SECONDS));
  }

  // 作成失敗のテスト
  // 不正なリクエスト

  @Test
  @DatabaseSetup("/datasets/tasks/no-task.xml")
  @ExpectedDatabase(value = "/datasets/tasks/no-task.xml", table = "TASKS") // 期待結果：DBは空のまま
  void tasksPost_shouldReturnBadRequest_whenTitleIsEmpty() throws Exception {

    // 1. Given: DTOオブジェクトを直接作成
    TaskRequest requestDto = new TaskRequest();
    requestDto.setTitle(""); // テストしたい不正な値
    requestDto.setDueDate(OffsetDateTime.now().plusDays(1)); // 常に未来の日付

    // DTOをJSON文字列に変換
    String invalidJson = objectMapper.writeValueAsString(requestDto);

    mockMvc.perform(post("/tasks")
        .contentType(MediaType.APPLICATION_JSON)
        .content(invalidJson))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DatabaseSetup("/datasets/tasks/no-task.xml")
  @ExpectedDatabase(value = "/datasets/tasks/no-task.xml", table = "TASKS") // 期待結果：DBは空のまま
  void tasksPost_shouldReturnBadRequest_whenTitleIsNull() throws Exception {

    // 1. Given: DTOオブジェクトを直接作成
    TaskRequest requestDto = new TaskRequest();
    requestDto.setDueDate(OffsetDateTime.now().plusDays(1)); // 常に未来の日付

    // DTOをJSON文字列に変換
    String invalidJson = objectMapper.writeValueAsString(requestDto);

    mockMvc.perform(post("/tasks")
        .contentType(MediaType.APPLICATION_JSON)
        .content(invalidJson))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DatabaseSetup("/datasets/tasks/no-task.xml")
  @ExpectedDatabase(value = "/datasets/tasks/no-task.xml", table = "TASKS") // 期待結果：DBは空のまま
  void tasksPost_shouldReturnBadRequest_whenTitleLengthIsOver20() throws Exception {

    // 1. Given: DTOオブジェクトを直接作成
    TaskRequest requestDto = new TaskRequest();
    requestDto.setTitle("123456789012345678901"); // テストしたい不正な値
    requestDto.setDueDate(OffsetDateTime.now().plusDays(1)); // 常に未来の日付

    // DTOをJSON文字列に変換
    String invalidJson = objectMapper.writeValueAsString(requestDto);

    mockMvc.perform(post("/tasks")
        .contentType(MediaType.APPLICATION_JSON)
        .content(invalidJson))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DatabaseSetup("/datasets/tasks/no-task.xml")
  @ExpectedDatabase(value = "/datasets/tasks/no-task.xml", table = "TASKS") // 期待結果：DBは空のまま
  void tasksPost_shouldReturnBadRequest_whenDescriptionLengthIsOver50() throws Exception {

    // 1. Given: DTOオブジェクトを直接作成
    TaskRequest requestDto = new TaskRequest();
    requestDto.setTitle("test title");
    requestDto.setDescription("あいうえおかきくけこさしすせそたちつてとなにぬねのはひふへほまみむめもおやいゆえよらりるれろわいうえおあ");
    requestDto.setDueDate(OffsetDateTime.now().plusDays(1)); // 常に未来の日付

    // DTOをJSON文字列に変換
    String invalidJson = objectMapper.writeValueAsString(requestDto);

    mockMvc.perform(post("/tasks")
        .contentType(MediaType.APPLICATION_JSON)
        .content(invalidJson))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DatabaseSetup("/datasets/tasks/no-task.xml")
  @ExpectedDatabase(value = "/datasets/tasks/no-task.xml", table = "TASKS") // 期待結果：DBは空のまま
  void tasksPost_shouldReturnBadRequest_whenDescriptionContainHalfWidthCharacters() throws Exception {

    // 1. Given: DTOオブジェクトを直接作成
    TaskRequest requestDto = new TaskRequest();
    requestDto.setTitle("test title");
    requestDto.setDescription("a");
    requestDto.setDueDate(OffsetDateTime.now().plusDays(1)); // 常に未来の日付

    // DTOをJSON文字列に変換
    String invalidJson = objectMapper.writeValueAsString(requestDto);

    mockMvc.perform(post("/tasks")
        .contentType(MediaType.APPLICATION_JSON)
        .content(invalidJson))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DatabaseSetup("/datasets/tasks/no-task.xml")
  @ExpectedDatabase(value = "/datasets/tasks/no-task.xml", table = "TASKS") // 期待結果：DBは空のまま
  void tasksPost_shouldReturnBadRequest_whenDueDateIsPast() throws Exception {

    // 1. Given: DTOオブジェクトを直接作成
    TaskRequest requestDto = new TaskRequest();
    requestDto.setTitle("test title");
    requestDto.setDueDate(OffsetDateTime.now().minusDays(1));

    // DTOをJSON文字列に変換
    String invalidJson = objectMapper.writeValueAsString(requestDto);

    mockMvc.perform(post("/tasks")
        .contentType(MediaType.APPLICATION_JSON)
        .content(invalidJson))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message", is("期限に過去の日時は設定できません。")));
  }

}
