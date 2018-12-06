package com.successfactors.sfmooc.dao.impl;

import com.successfactors.sfmooc.dao.UserDAO;
import com.successfactors.sfmooc.domain.User;
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
        String query = "select id, nickname, avatarUrl, status, department, signature, seat, " +
                "blog, github from user where id = ?";
        List<User> users = jdbcTemplate.query(query, new Object[]{userId}, new UserRowMapper());
        if(users != null && !users.isEmpty()){
            return users.get(0);
        }
        return null;
    }

    @Override
    public List<User> getUsersByOrder() {
        String query = "select * from user where `order` is not null order by `order` asc " ;
        List<User> users = jdbcTemplate.query(query, new UserRowMapper());
        return users;
    }

    class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User();
            user.setId(resultSet.getString("id"));
            user.setNickName(resultSet.getString("nickname"));
            user.setAvatarUrl(resultSet.getString("avatarUrl"));
            user.setStatus(resultSet.getInt("status"));
            user.setDepartment(resultSet.getString("department"));
            user.setSignature(resultSet.getString("signature"));
            user.setSeat(resultSet.getString("seat"));
            user.setBlog(resultSet.getString("blog"));
            user.setGithub(resultSet.getString("github"));
            return  user;
        }
    }

}
