package com.example.todo.repository;

import com.example.todo.dto.UserDto;
import com.example.todo.entity.User;
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
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User insert(UserDto user) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(this.jdbcTemplate);
        insert.withTableName("users").usingGeneratedKeyColumns("id");
        Timestamp now = new Timestamp(System.currentTimeMillis());

        Map<String, Object> params = new HashMap<>();
        params.put("name", user.getName());
        params.put("email", user.getEmail());
        params.put("create_dt", now);
        params.put("mod_dt", now);

        Number key = insert.executeAndReturnKey(new MapSqlParameterSource(params));

        return new User(key.longValue(), user.getName(), user.getEmail(), now, now);
    }

    @Override
    public int update(Long id, String name, String email) {
        return jdbcTemplate.update("update users set name = ?, email = ? where id = ?", name, email, id);
    }

    @Override
    public User findById(Long id) {
        List<User> res = jdbcTemplate.query("select * from users where id = ?", userMapper(), id);
        return res.stream().findAny().orElseThrow(()->new RuntimeException("Null"));
    }


    private RowMapper<User> userMapper() {
        return new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new User(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getDate("create_dt"),
                        rs.getDate("mod_dt")
                );
            }
        };


    }
}
