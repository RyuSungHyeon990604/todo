package com.example.todo.repository;

import com.example.todo.dto.request.TodoUpdateRequestDto;
import com.example.todo.entity.Todo;
import com.example.todo.exception.TodoNotFoundException;
import com.example.todo.rowMapper.TodoRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class TodoRepositoryImpl implements TodoRepository {

    private final JdbcTemplate jdbcTemplate;

    public TodoRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Todo> findAll(Long userId, Long page, Long pageSize, LocalDate date) {
        String defaultSql = "select t.id         as todo_id" +
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
                "      where 1 = 1 ";
        List<Object> params = new ArrayList<>();
        StringBuilder sql = new StringBuilder(defaultSql);
        if (userId != null) {
            sql.append(" and t.user_id = ?");
            params.add(userId);
        }
        if (date != null) {
            sql.append(" and date_format(t.create_dt,'%Y-%m-%d') = ?");
            params.add(date);
        }
        sql.append(" order by mod_dt desc ");
        //페이지를 설정하지않았다면 1페이지 조회
        page = page == null ? 1 : page;
        long offset = (page - 1) * pageSize;
        sql.append("limit ").append(offset).append(", ").append(pageSize);

        return jdbcTemplate.query(sql.toString(), new TodoRowMapper(), params.toArray());

    }

    @Override
    public Optional<Todo> findById(Long id) {
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
        try {
            Todo todo = jdbcTemplate.queryForObject(sql, new TodoRowMapper(), id);
            return Optional.ofNullable(todo);
        } catch (EmptyResultDataAccessException e) {
            throw new TodoNotFoundException();
        }
    }

    @Override
    public Todo insert(Todo todo) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);

        simpleJdbcInsert.withTableName("todo").usingGeneratedKeyColumns("id");

        LocalDateTime now = LocalDateTime.now().withNano(0);
        Map<String, Object> params = new HashMap<>();
        params.put("user_id", todo.getUser().getId());
        params.put("todo", todo.getTodo());
        params.put("pwd", todo.getPwd());
        params.put("create_dt", now);
        params.put("mod_dt", now);

        Number key = simpleJdbcInsert.executeAndReturnKey(new MapSqlParameterSource(params));
        return new Todo(key.longValue(), todo.getUser(), todo.getTodo(), todo.getPwd(), now, now);
    }

    @Override
    public int deleteById(Long id) {
        return jdbcTemplate.update("delete from todo where id = ?", id);
    }

    @Override
    public int update(Long todoId, TodoUpdateRequestDto todoDto) {
        return jdbcTemplate.update("update todo set todo = ?, mod_dt = ? where id = ?", todoDto.getTodo(), LocalDateTime.now().withNano(0), todoId);
    }
}
