package com.example.todo.repository;

import com.example.todo.dto.TodoDto;
import com.example.todo.entity.Todo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TodoRepositoryImpl implements TodoRepository {

    private final JdbcTemplate jdbcTemplate;

    public TodoRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Todo> findAll() {
        return jdbcTemplate.query("select * from todo", todoRowMapper());
    }

    @Override
    public Todo findById(Long id) {
        List<Todo> res = jdbcTemplate.query("select * from todo where id = ?", todoRowMapper(), id);
        return res.stream().findAny().orElseThrow();
    }

    @Override
    public List<Todo> findAllByUserId(Long userId) {
        List<Todo> res = jdbcTemplate.query("select * from todo where user_id = ?", todoRowMapper(), userId);
        return res;
    }

    @Override
    public Todo insert(Long userId, TodoDto todo) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName("todo").usingGeneratedKeyColumns("id");
        Timestamp now = new Timestamp(System.currentTimeMillis());

        Map<String, Object> params = new HashMap<>();
        params.put("user_id", userId);
        params.put("todo", todo.getTodo());
        params.put("pwd", todo.getPwd());
        params.put("create_dt", now);
        params.put("mod_dt", now);

        Number key = simpleJdbcInsert.executeAndReturnKey(new MapSqlParameterSource(params));
        return new Todo(key.longValue(), userId, todo.getTodo(), now, now);

    }

    @Override
    public int deleteById(Long id) {
        return jdbcTemplate.update("delete from todo where id = ?", id);
    }

    @Override
    public int update(Long id, String todo) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        return jdbcTemplate.update("update todo set todo = ?, mod_dt = ? where id = ?", todo, now, id);
    }


    private RowMapper<Todo> todoRowMapper() {
        return new RowMapper<Todo>() {
            @Override
            public Todo mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Todo(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("todo"),
                        rs.getDate("create_dt"),
                        rs.getDate("mod_dt")
                );
            }
        };
    }
}
