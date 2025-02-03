package com.example.todo.rowMapper;

import com.example.todo.entity.Todo;
import com.example.todo.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TodoRowMapper implements RowMapper<Todo> {
    @Override
    public Todo mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User(
                rs.getLong("user_id")
                ,rs.getString("user_name")
                ,rs.getString("user_email")
                ,rs.getTimestamp("user_create_dt").toLocalDateTime()
                ,rs.getTimestamp("user_mod_dt").toLocalDateTime()
        );
        return new Todo(
                rs.getLong("todo_id"),
                user,
                rs.getString("todo"),
                rs.getString("pwd"),
                rs.getTimestamp("create_dt").toLocalDateTime(),
                rs.getTimestamp("mod_dt").toLocalDateTime()
        );
    }
}
