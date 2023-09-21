package com.douzone.surveymanagement.common.utils;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MultipartFileTypeHandler extends BaseTypeHandler<MultipartFile> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, MultipartFile multipartFile, JdbcType jdbcType) throws SQLException {
        try {
            byte[] bytes = multipartFile.getBytes();
            ps.setBytes(i, bytes);
        } catch (IOException e) {
            // 예외 처리: 예외를 로깅하거나 에러 메시지를 반환하거나 적절한 조치를 취합니다.
            e.printStackTrace(); // 예외 로깅 예시
            // 또는 다른 처리를 수행할 수 있습니다.
            throw new SQLException("Failed to get bytes from MultipartFile: " + e.getMessage());
        }
    }

    @Override
    public MultipartFile getNullableResult(ResultSet rs, String columnName) throws SQLException {
        // Implement the conversion from byte[] to MultipartFile if needed.
        return null;
    }

    @Override
    public MultipartFile getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        // Implement the conversion from byte[] to MultipartFile if needed.
        return null;
    }

    @Override
    public MultipartFile getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        // Implement the conversion from byte[] to MultipartFile if needed.
        return null;
    }
}
