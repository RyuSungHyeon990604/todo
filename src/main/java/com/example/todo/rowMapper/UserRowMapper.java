package com.example.todo.rowMapper;

import com.example.todo.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getTimestamp("create_dt").toLocalDateTime(),
                rs.getTimestamp("mod_dt").toLocalDateTime()
        );
    }
}
