package com.successfactors.sfmooc.dao.impl;

import com.successfactors.sfmooc.dao.CheckinDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CheckinDAOImpl implements CheckinDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Map<Integer, String> getAllCodes() {
        Map<Integer, String> result = new HashMap<>(1024);
        List<String> codeList = jdbcTemplate.query("select code from checkin_code", new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                String code = resultSet.getString("code");
                result.put(i+1, code);
                return code;
            }
        });
        return result;
    }
}
