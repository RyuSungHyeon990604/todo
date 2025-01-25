package com.example.todo.repository;

import com.example.todo.dto.UserDto;
import com.example.todo.entity.User;
import com.example.todo.exception.DbException;
import lombok.NonNull;
import org.springframework.dao.DataAccessException;
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
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User insert(User user) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(this.jdbcTemplate);
        try {
            insert.withTableName("users").usingGeneratedKeyColumns("id");
        } catch (DataAccessException e) {
            throw new DbException(e.getMessage());
        }
        LocalDateTime now = LocalDateTime.now();

        Map<String, Object> params = new HashMap<>();
        params.put("name", user.getName());
        params.put("email", user.getEmail());
        params.put("create_dt", now);
        params.put("mod_dt", now);
        try {
            Number key = insert.executeAndReturnKey(new MapSqlParameterSource(params));
            return new User(key.longValue(), user.getName(), user.getEmail(), now, now);
        } catch (DataAccessException e) {
            throw new DbException(e.getMessage());
        }

    }

    @Override
    public int update(Long id, UserDto user) {
        try{
            return jdbcTemplate.update("update users set name = ?, email = ?, mod_dt = ? where id = ?", user.getName(), user.getEmail(), LocalDateTime.now(), id);
        } catch (DataAccessException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public User findById(Long id) {
        try {
            return jdbcTemplate.queryForObject("select * from users where id = ?", userMapper(), id);
        } catch (DataAccessException e) {
            throw new DbException(e.getMessage());
        }
    }


    private RowMapper<User> userMapper() {
        return new RowMapper<User>() {
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
        };


    }
}
