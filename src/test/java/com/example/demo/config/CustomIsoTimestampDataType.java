package com.example.demo.config;

import org.dbunit.dataset.datatype.AbstractDataType;
import org.dbunit.dataset.datatype.TypeCastException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * タイムゾーン付きISO8601文字列を扱うDataType
 * DBUnitのバージョン差異に強い実装
 */
public class CustomIsoTimestampDataType extends AbstractDataType {

    private static final DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    public CustomIsoTimestampDataType() {
        super("ISO_OFFSET_TIMESTAMP", Types.TIMESTAMP, OffsetDateTime.class, false);
    }

    @Override
    public boolean isDateTime() {
        return true;
    }

    @Override
    public int compare(Object o1, Object o2) throws TypeCastException {
        OffsetDateTime d1 = (OffsetDateTime) typeCast(o1);
        OffsetDateTime d2 = (OffsetDateTime) typeCast(o2);
        return d1.compareTo(d2);
    }

    @Override
    public Object typeCast(Object value) throws TypeCastException {
        if (value == null) {
            return null;
        }
        if (value instanceof OffsetDateTime) {
            return value;
        }
        if (value instanceof java.sql.Timestamp) {
            return ((java.sql.Timestamp) value).toInstant().atOffset(OffsetDateTime.now().getOffset());
        }
        if (value instanceof String) {
            try {
                return OffsetDateTime.parse((String) value, ISO_FORMATTER);
            } catch (Exception e) {
                throw new TypeCastException("Invalid ISO date string: " + value, e);
            }
        }
        throw new TypeCastException("Unsupported type for ISO timestamp: " + value, (Throwable) null);
    }

    @Override
    public Object getSqlValue(int column, ResultSet resultSet) throws SQLException {
        try {
            OffsetDateTime odt = resultSet.getObject(column, OffsetDateTime.class);
            return odt != null ? odt.withOffsetSameInstant(ZoneOffset.UTC) : null;
        } catch (AbstractMethodError | SQLException e) {
            String str = resultSet.getString(column);
            if (str != null) {
                return OffsetDateTime.parse(str, ISO_FORMATTER).withOffsetSameInstant(ZoneOffset.UTC);
            }
            return null;
        }
    }

    @Override
    public void setSqlValue(Object value, int column, PreparedStatement statement) throws SQLException {
        if (value == null) {
            statement.setNull(column, Types.TIMESTAMP);
            return;
        }
        OffsetDateTime odt;
        try {
            odt = (OffsetDateTime) typeCast(value);
            // UTC に変換
            odt = odt.withOffsetSameInstant(ZoneOffset.UTC);
        } catch (TypeCastException e) {
            throw new SQLException("Failed to cast value to OffsetDateTime", e);
        }
        try {
            statement.setObject(column, odt);
        } catch (AbstractMethodError | SQLException e) {
            statement.setString(column, ISO_FORMATTER.format(odt));
        }
    }

}
