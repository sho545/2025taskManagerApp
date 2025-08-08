package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test") // testプロファイルの設定を読み込む
public class SchemaVerificationTest {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Test
  void verifyTableAndColumnSchema() {
    System.out.println("--- スキーマ情報の検証を開始します ---");

    // 1. テーブル名の確認 (大文字・小文字を含めて)
    String tableName = jdbcTemplate.queryForObject(
        "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'PUBLIC' AND UPPER(TABLE_NAME) = 'TASKS'",
        String.class);
    System.out.println("✅ データベース上のテーブル名: " + tableName);
    assertThat(tableName).isEqualTo("TASKS");

    // 2. カラム名とデータ型の一覧を確認
    List<Map<String, Object>> columns = jdbcTemplate.queryForList(
        "SELECT COLUMN_NAME, DATA_TYPE, IS_NULLABLE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = ? ORDER BY ORDINAL_POSITION",
        tableName);

    System.out.println("✅ '" + tableName + "'テーブルのカラム一覧:");
    columns.forEach(column -> {
      System.out.println(String.format(
          "- %s (データ型: %s, NULL許容: %s)",
          column.get("COLUMN_NAME"),
          column.get("DATA_TYPE"),
          column.get("IS_NULLABLE")));
    });

    System.out.println("--- スキーマ情報の検証が完了しました ---");

    // 3. 期待するカラム数と一致するかを検証
    assertThat(columns).hasSize(5);
  }
}
