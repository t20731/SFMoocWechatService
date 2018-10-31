package com.successfactors.sfmooc.dao.impl;

import com.successfactors.sfmooc.dao.DirectionDAO;
import com.successfactors.sfmooc.domain.Direction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class DirectionDAOImpl implements DirectionDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Direction> getAll() {
        List<Direction> directions = jdbcTemplate.query("select id, name from direction order by id asc", new RowMapper<Direction>() {
            @Nullable
            @Override
            public Direction mapRow(ResultSet resultSet, int i) throws SQLException {
                Direction direction = new Direction();
                direction.setId(resultSet.getInt("id"));
                direction.setName(resultSet.getString("name"));
                return direction;
            }
        });
        return  directions;
    }
}
