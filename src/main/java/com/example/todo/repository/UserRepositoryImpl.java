package com.example.todo.repository;

import com.example.todo.dto.UserDto;
import com.example.todo.entity.User;
import lombok.NonNull;
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
    public User insert(@NonNull User user) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(this.jdbcTemplate);
        insert.withTableName("users").usingGeneratedKeyColumns("id");
        LocalDateTime now = LocalDateTime.now();

        Map<String, Object> params = new HashMap<>();
        params.put("name", user.getName());
        params.put("email", user.getEmail());
        params.put("create_dt", now);
        params.put("mod_dt", now);

        Number key = insert.executeAndReturnKey(new MapSqlParameterSource(params));

        return new User(key.longValue(), user.getName(), user.getEmail(), now, now);
    }

    @Override
    public int update(@NonNull Long id, @NonNull UserDto user) {
        Assert.notNull(user.getName(), "user.name must not be null");
        Assert.notNull(user.getEmail(), "user.email must not be null");
        Assert.notNull(user.getModDt(), "user.modDt must not be null");
        return jdbcTemplate.update("update users set name = ?, email = ?, mod_dt = ? where id = ?", user.getName(), user.getEmail(), LocalDateTime.now(), id);
    }

    @Override
    public User findById(@NonNull Long id) {
        List<User> res = jdbcTemplate.query("select * from users where id = ?", userMapper(), id);
        return res.stream().findAny().orElseThrow(()->new RuntimeException("Null"));
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
