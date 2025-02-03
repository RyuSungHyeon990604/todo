package com.example.todo.repository;

import com.example.todo.dto.request.UserUpdateRequestDto;
import com.example.todo.entity.User;
import com.example.todo.exception.UserNotFoundException;
import com.example.todo.rowMapper.UserRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<User> insert(User user) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(this.jdbcTemplate);
        insert.withTableName("users").usingGeneratedKeyColumns("id");
        LocalDateTime now = LocalDateTime.now().withNano(0);

        Map<String, Object> params = new HashMap<>();
        params.put("name", user.getName());
        params.put("email", user.getEmail());
        params.put("create_dt", now);
        params.put("mod_dt", now);

        Number key = insert.executeAndReturnKey(new MapSqlParameterSource(params));
        return Optional.of(new User(key.longValue(), user.getName(), user.getEmail(), now, now));
    }

    @Override
    public int update(Long id, UserUpdateRequestDto user) {
        return jdbcTemplate.update("update users set name = ?, email = ?, mod_dt = ? where id = ?", user.getName(), user.getEmail(), LocalDateTime.now().withNano(0), id);
    }

    @Override
    public Optional<User> findById(Long id) {
        User user = jdbcTemplate.queryForObject("select * from users where id = ?", new UserRowMapper(), id);
        return Optional.ofNullable(user);
    }
}
