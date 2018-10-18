package com.successfactors.t2.dao.impl;

import com.successfactors.t2.dao.UserDAO;
import com.successfactors.t2.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int addUser(User user) {
        String id = user.getId();
        Integer userCnt = jdbcTemplate.queryForObject("select count(*) as cnt from user where id = ?", new Object[]{id}, new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getInt("cnt");
            }
        });
        if (userCnt == 0) {
            return jdbcTemplate.update("insert into user(id, nickname, gender, avatarUrl, status) values (?, ?, ?, ?, ?)",
                    new Object[]{id, user.getNickName(), user.getGender(), user.getAvatarUrl(), user.getStatus()});
        } else if (userCnt == 1) {
            return jdbcTemplate.update("update user set nickname = ?, gender = ?, avatarUrl = ? where id = ?",
                    new Object[]{user.getNickName(), user.getGender(), user.getAvatarUrl(), id});
        }
        return -1;
    }

    @Override
    public User getUserById(String userId) {
        String query = "select status, initial from user where id = ?";
        List<User> users = jdbcTemplate.query(query, new Object[]{userId}, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user = new User();
                user.setStatus(resultSet.getInt("status"));
                user.setInitialPoints(resultSet.getInt("initial"));
                return user;
            }
        });
        if(users != null && !users.isEmpty()){
            return users.get(0);
        }
        return null;
    }
}
