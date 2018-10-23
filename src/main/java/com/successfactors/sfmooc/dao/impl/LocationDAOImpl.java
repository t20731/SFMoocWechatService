package com.successfactors.sfmooc.dao.impl;

import com.successfactors.sfmooc.dao.LocationDAO;
import com.successfactors.sfmooc.domain.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class LocationDAOImpl implements LocationDAO{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Location> getAll() {
        List<Location> locations = jdbcTemplate.query("select id, name from location", new RowMapper<Location>() {
            @Nullable
            @Override
            public Location mapRow(ResultSet resultSet, int i) throws SQLException {
                Location location = new Location();
                location.setId(resultSet.getInt("id"));
                location.setName(resultSet.getString("name"));
                return location;
            }
        });
        return  locations;
    }
}
