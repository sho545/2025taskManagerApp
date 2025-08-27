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
    // H2のTIMESTAMP WITH TIME ZONEは、 CustomIsoTimestampDataTypeとして扱われる
    if (sqlType == Types.TIMESTAMP_WITH_TIMEZONE) {

      return new CustomIsoTimestampDataType();
    }

    // それ以外の型は、元々のH2DataTypeFactoryの処理に任せる
    return super.createDataType(sqlType, sqlTypeName);
  }
}