package com.example.todo.repository;

import com.example.todo.dto.TodoDto;
import com.example.todo.entity.Todo;
import com.example.todo.entity.User;
import lombok.NonNull;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
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
        String sql = "select t.id         as todo_id" +
                "          , t.todo       as todo" +
                "          , t.pwd        as pwd" +
                "          , t.create_dt  as create_dt" +
                "          , t.mod_dt     as mod_dt" +
                "          , u.id         as user_id" +
                "          , u.name       as user_name" +
                "          , u.email      as user_email" +
                "          , u.create_dt  as user_create_dt" +
                "          , u.mod_dt     as user_mod_dt" +
                "       from todo t" +
                "      inner join users u" +
                "              on t.user_id = u.id";
        return jdbcTemplate.query(sql, todoRowMapper());
    }

    @Override
    public Todo findById(@NonNull Long id) {
        try {
            Assert.notNull(id, "id must not be null");
            String sql = "select t.id         as todo_id" +
                    "          , t.todo       as todo" +
                    "          , t.pwd        as pwd" +
                    "          , t.create_dt  as create_dt" +
                    "          , t.mod_dt     as mod_dt" +
                    "          , u.id         as user_id" +
                    "          , u.name       as user_name" +
                    "          , u.email      as user_email" +
                    "          , u.create_dt  as user_create_dt" +
                    "          , u.mod_dt     as user_mod_dt" +
                    "       from todo t" +
                    "      inner join users u" +
                    "              on t.user_id = u.id" +
                    "      where t.id = ?";
            Todo res = jdbcTemplate.queryForObject(sql, todoRowMapper(), id);
            return res;
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("Empty Result");
        }
    }

    @Override
    public List<Todo> findAllByUserId(@NonNull Long userId) {
        String sql = "select t.id         as todo_id" +
                "          , t.todo       as todo" +
                "          , t.pwd        as pwd" +
                "          , t.create_dt  as create_dt" +
                "          , t.mod_dt     as mod_dt" +
                "          , u.id         as user_id" +
                "          , u.name       as user_name" +
                "          , u.email      as user_email" +
                "          , u.create_dt  as user_create_dt" +
                "          , u.mod_dt     as user_mod_dt" +
                "       from todo t" +
                "      inner join users u" +
                "              on t.user_id = u.id" +
                "      where u.id = ?";
        List<Todo> res = jdbcTemplate.query(sql, todoRowMapper(), userId);
        return res;
    }

    @Override
    public Todo insert(@NonNull Todo todo) {
        Assert.notNull(todo.getUser(), "todo.user must not be null");
        Assert.notNull(todo.getTodo(), "todo.todo must not be null");
        Assert.notNull(todo.getPwd(), "todo.pwd must not be null");
        Assert.notNull(todo.getCreateDt(), "todo.creat_dt must not be null");
        Assert.notNull(todo.getModDt(), "todo.mod_dt must not be null");

        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName("todo").usingGeneratedKeyColumns("id");
        LocalDateTime now = LocalDateTime.now();

        Map<String, Object> params = new HashMap<>();
        params.put("user_id", todo.getUser().getId());
        params.put("todo", todo.getTodo());
        params.put("pwd", todo.getPwd());
        params.put("create_dt", now);
        params.put("mod_dt", now);

        Number key = simpleJdbcInsert.executeAndReturnKey(new MapSqlParameterSource(params));
        return new Todo(key.longValue(), todo.getUser(), todo.getTodo(), todo.getPwd(), todo.getCreateDt(), todo.getModDt());
    }

    @Override
    public int deleteById(@NonNull Long id) {
        Assert.notNull(id, "id must not be null");

        return jdbcTemplate.update("delete from todo where id = ?", id);
    }

    @Override
    public int update(@NonNull Long id, @NonNull TodoDto todoDto) {
        Assert.notNull(todoDto.getTodo(), "todoDto.todo must not be null");
        Assert.notNull(todoDto.getModDt(), "todoDto.mod_dt must not be null");

        return jdbcTemplate.update("update todo set todo = ?, mod_dt = ? where id = ?", todoDto.getTodo(), LocalDateTime.now(), id);
    }


    private RowMapper<Todo> todoRowMapper() {
        return new RowMapper<Todo>() {
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
        };
    }

}
