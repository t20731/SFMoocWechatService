package com.successfactors.sfmooc.dao.impl;

import com.successfactors.sfmooc.dao.GroupDAO;
import com.successfactors.sfmooc.domain.Group;
import com.successfactors.sfmooc.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class GroupDAOImpl implements GroupDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Group>  getGroups() {
        String query = "select id,name from `group` order by id ASC";
        List<Group> groups = jdbcTemplate.query(query, new RowMapper<Group>() {
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

    @Override
    public boolean isUserInGroup(String userId, Integer groupId) {
        int count = jdbcTemplate.queryForObject("select count(1) as cnt from user_group_map " +
                "where user_id = ? and group_id = ?", new Object[]{userId, groupId}, new RowMapper<Integer>(){
            @Override
            public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getInt("cnt");
            }
        });
        return count == 1 ? true : false;
    }

    @Override
    public int addUserToGroup(String userId, Integer groupId) {
        String now = DateUtil.formatDateTime(new Date());
        return jdbcTemplate.update("replace into user_group_map(user_id, group_id, join_date) values (?, ?, ?)",
                new Object[]{userId, groupId, now});
    }

    @Override
    public int addGroup(String groupName, Integer sharePoints) {
        String insertSQL = "insert into `group` (name,share_points) values (?, ?)";
        return jdbcTemplate.update(insertSQL, new Object[]{groupName,sharePoints});
      }

    public int markSessionAsShared(String userId, Integer sessionId) {
        return jdbcTemplate.update("update user_group_map set shared = ? where user_id = ? and group_id = (" +
                        "select type_id from session where id = ?)",
                new Object[]{1, userId, sessionId});
    }
}
