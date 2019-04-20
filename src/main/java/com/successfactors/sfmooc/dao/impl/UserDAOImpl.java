package com.successfactors.sfmooc.dao.impl;

import com.successfactors.sfmooc.dao.GroupDAO;
import com.successfactors.sfmooc.dao.UserDAO;
import com.successfactors.sfmooc.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private GroupDAO groupDAO;

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
            int status = jdbcTemplate.update("insert into user(id, nickname, gender, avatarUrl, status) values (?, ?, ?, ?, ?)",
                    new Object[]{id, user.getNickName(), user.getGender(), user.getAvatarUrl(), user.getStatus()});
            status += groupDAO.addUserToGroup(id, 0);
            return status;
        } else if (userCnt == 1) {
            return jdbcTemplate.update("update user set nickname = ?, gender = ?, avatarUrl = ? where id = ?",
                    new Object[]{user.getNickName(), user.getGender(), user.getAvatarUrl(), id});
        }
        return -1;
    }

    @Override
    public User getUserById(String userId) {
        String query = "select id, nickname, gender, avatarUrl, status, department, signature, seat, " +
                "blog, github from user where id = ?";
        List<User> users = jdbcTemplate.query(query, new Object[]{userId}, new UserRowMapper());
        if(users != null && !users.isEmpty()){
            return users.get(0);
        }
        return null;
    }

    @Override
    public List<User> getUsersByOrder() {
        String query = "select u.id, u.avatarUrl, u.nickname, u.signature, ug.shared, ug.share_type from user u, user_group_map ug " +
                "where u.id = ug.user_id and ug.group_id = 2 order by share_order, join_date";
        List<User> users = jdbcTemplate.query(query, new RowMapper<User>() {
            @Nullable
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user = new User();
                user.setId(resultSet.getString("id"));
                user.setAvatarUrl(resultSet.getString("avatarUrl"));
                user.setNickName(resultSet.getString("nickname"));
                user.setSignature(resultSet.getString("signature"));
                user.setShared(resultSet.getInt("shared"));
                user.setShareType(resultSet.getString("share_type"));
                return user;
            }
        });
        return users;
    }

    @Override
    public int editUserInfo(User user) {
        return jdbcTemplate.update("update user set department = ?, signature = ?, seat = ?, blog = ?, github = ? where id = ?",
                new Object[]{user.getDepartment(), user.getSignature(), user.getSeat(), user.getBlog(), user.getGithub(), user.getId()});
    }

    @Override
    public List<Group> getUserGroup(String userId) {
        String query = "SELECT id,`name` from `group` g, user_group_map ug where g.id = ug.group_id and ug.user_id = ?" ;
        List<Group> groups = jdbcTemplate.query(query,new Object[]{userId}, new RowMapper<Group>() {
            @Override
            public Group mapRow(ResultSet resultSet, int i) throws SQLException {
                Group group = new Group();
                group.setId(resultSet.getInt("id"));
                group.setName(resultSet.getString("name"));
                return group;
            }
        });
        return groups;
    }

    class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User();
            user.setId(resultSet.getString("id"));
            user.setNickName(resultSet.getString("nickname"));
            user.setGender(resultSet.getInt("gender"));
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
