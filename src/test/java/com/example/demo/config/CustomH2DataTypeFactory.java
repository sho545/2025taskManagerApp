package com.example.demo.config;

import org.dbunit.dataset.datatype.DataType;
import org.dbunit.dataset.datatype.DataTypeException;
import org.dbunit.ext.h2.H2DataTypeFactory;

import java.sql.Types;

/**
 * DBUnit標準のH2DataTypeFactoryを拡張し、
 * TIMESTAMP WITH TIME ZONE 型を正しく扱うためのカスタムファクトリ。
 */
public class CustomH2DataTypeFactory extends H2DataTypeFactory {

  @Override
  public DataType createDataType(int sqlType, String sqlTypeName) throws DataTypeException {
    // H2のTIMESTAMP WITH TIME ZONEは、JDBCの型番号 2014 として扱われる
    if (sqlType == Types.TIMESTAMP_WITH_TIMEZONE) {
      // この型の場合は、DBUnitが扱える標準のTimestamp型として認識させる
      // return DataType.TIMESTAMP;
      return new CustomIsoTimestampDataType();
    }

    // それ以外の型は、元々のH2DataTypeFactoryの処理に任せる
    return super.createDataType(sqlType, sqlTypeName);
  }
}