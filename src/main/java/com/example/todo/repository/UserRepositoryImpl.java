package com.example.todo.repository;

import com.example.todo.dto.response.UserDto;
import com.example.todo.entity.User;
import com.example.todo.exception.DuplicateKeyException;
import com.example.todo.exception.UserNotFoundException;
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

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User insert(User user) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(this.jdbcTemplate);
        if(!isUnique("email", user.getEmail())) {
            throw new DuplicateKeyException("이미 사용중인 이메일입니다.");
        }

        insert.withTableName("users").usingGeneratedKeyColumns("id");
        LocalDateTime now = LocalDateTime.now().withNano(0);

        Map<String, Object> params = new HashMap<>();
        params.put("name", user.getName());
        params.put("email", user.getEmail());
        params.put("create_dt", now);
        params.put("mod_dt", now);

        Number key = insert.executeAndReturnKey(new MapSqlParameterSource(params));
        return new User(key.longValue(), user.getName(), user.getEmail(), now, now);
    }

    @Override
    public int update(Long id, UserDto user) {
        return jdbcTemplate.update("update users set name = ?, email = ?, mod_dt = ? where id = ?", user.getName(), user.getEmail(), LocalDateTime.now(), id);
    }

    @Override
    public User findById(Long id) {
        try {
            return jdbcTemplate.queryForObject("select * from users where id = ?", userMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException("사용자" + id + "가 존재하지않습니다.");
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

    private boolean isUnique(String column, String value) {
        String sql = "select count(*) " +
                "       from users " +
                "      where 1 = 1" +
                "        and " + column + " = ?";
        int i = jdbcTemplate.queryForObject(sql, Integer.class,value);
        return i == 0;
    }
}
