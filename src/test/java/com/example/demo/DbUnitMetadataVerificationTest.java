package com.example.demo;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.Column;
import org.dbunit.dataset.ITableMetaData;
import org.dbunit.dataset.datatype.DataType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.demo.config.CustomH2DataTypeFactory;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
public class DbUnitMetadataVerificationTest {

  @Autowired
  private DataSource dataSource; // Springが管理するデータソースを直接取得

  @Test
  void doesDbUnitRecognizeSchemaCorrectly() throws Exception {
    // SpringのデータソースからDBUnit用のコネクションを作成
    IDatabaseConnection dbUnitConnection = new DatabaseConnection(dataSource.getConnection());

    // --- ここでDBUnitの設定をJavaコードで直接適用します ---
    DatabaseConfig config = dbUnitConnection.getConfig();

    // 1. データ型を正しく認識させる設定
    config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new CustomH2DataTypeFactory());

    // 2. カラム名の大文字/小文字を吸収させる設定
    // config.setProperty(DatabaseConfig.PROPERTY_COLUMN_SENSING, true);
    // ----------------------------------------------------

    System.out.println("--- DBUnitによるスキーマ認識の検証を開始します ---");

    // DBUnitにテーブルのメタ情報（カラム定義など）を取得させる
    ITableMetaData tableMetaData = dbUnitConnection.createDataSet().getTableMetaData("TASKS");
    Column[] columns = tableMetaData.getColumns();

    System.out.println("✅ DBUnitが認識した 'TASKS' テーブルのカラム一覧:");
    boolean dueDateFound = false;
    for (Column column : columns) {
      String columnName = column.getColumnName();
      DataType dataType = column.getDataType();
      // カラム名とそのデータ型名を出力
      System.out.println(String.format("- カラム名: %s (データ型: %s)", columnName, dataType.getClass().getSimpleName()));
      if ("DUE_DATE".equals(columnName)) {
        dueDateFound = true;
        // DUE_DATEのデータ型が未知の型(UnknownDataType)ではないことを確認
        assertThat(dataType.getClass().getSimpleName()).isNotEqualTo("UnknownDataType");
        System.out.println("  -> 'DUE_DATE'カラムを正しく認識しました。");
      }
    }

    // 'DUE_DATE' カラムがDBUnitによって認識されたかを最終確認
    assertThat(dueDateFound).isTrue();
    System.out.println("--- DBUnitによるスキーマ認識の検証が完了しました ---");
  }
}
