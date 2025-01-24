package com.example.todo.repository;

import com.example.todo.dto.TodoDto;
import com.example.todo.entity.Todo;
import lombok.NonNull;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

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
    public Todo findById(@NonNull Long id) {
        try{
            Assert.notNull(id,"id must not be null");
            Todo res = jdbcTemplate.queryForObject("select * from todo where id = ?", todoRowMapper(), id);
            return res;
        } catch (EmptyResultDataAccessException e) {
            throw  new RuntimeException("Empty Result");
        }
    }

    @Override
    public List<Todo> findAllByUserId(@NonNull Long userId) {
        List<Todo> res = jdbcTemplate.query("select * from todo where user_id = ?", todoRowMapper(), userId);
        return res;
    }

    @Override
    public Todo insert(@NonNull Todo todo) {
        Assert.notNull(todo.getUserId(),"todo.userId must not be null");
        Assert.notNull(todo.getTodo(),"todo.todo must not be null");
        Assert.notNull(todo.getPwd(),"todo.pwd must not be null");
        Assert.notNull(todo.getCreateDt(),"todo.creat_dt must not be null");
        Assert.notNull(todo.getModDt(),"todo.mod_dt must not be null");

        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName("todo").usingGeneratedKeyColumns("id");

        Map<String, Object> params = new HashMap<>();
        params.put("user_id", todo.getUserId());
        params.put("todo", todo.getTodo());
        params.put("pwd", todo.getPwd());
        params.put("create_dt", todo.getCreateDt());
        params.put("mod_dt", todo.getModDt());

        Number key = simpleJdbcInsert.executeAndReturnKey(new MapSqlParameterSource(params));
        return new Todo(key.longValue(), todo.getUserId(), todo.getTodo(), todo.getPwd(), todo.getCreateDt(), todo.getModDt());
    }

    @Override
    public int deleteById(@NonNull Long id) {
        Assert.notNull(id,"id must not be null");

        return jdbcTemplate.update("delete from todo where id = ?", id);
    }

    @Override
    public int update(@NonNull Long id, @NonNull TodoDto todoDto) {
        Assert.notNull(todoDto.getTodo(),"todoDto.todo must not be null");
        Assert.notNull(todoDto.getModDt(),"todoDto.mod_dt must not be null");

        return jdbcTemplate.update("update todo set todo = ?, mod_dt = ? where id = ?", todoDto.getTodo(), todoDto.getModDt(), id);
    }


    private RowMapper<Todo> todoRowMapper() {
        return new RowMapper<Todo>() {
            @Override
            public Todo mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Todo(
                        rs.getLong("id"),
                        rs.getLong("user_id"),
                        rs.getString("todo"),
                        rs.getString("pwd"),
                        rs.getTimestamp("create_dt").toLocalDateTime(),
                        rs.getTimestamp("mod_dt").toLocalDateTime()
                );
            }
        };
    }
}
