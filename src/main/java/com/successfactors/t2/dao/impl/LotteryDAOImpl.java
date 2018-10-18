package com.successfactors.t2.dao.impl;

import com.successfactors.t2.dao.LotteryDAO;
import com.successfactors.t2.domain.LotteryResult;
import com.successfactors.t2.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class LotteryDAOImpl implements LotteryDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int bet(String userId, Integer sessionId, Integer number) {
        return jdbcTemplate.update("update points set bet_number = ? where user_id = ? and session_id = ?",
                new Object[]{number, userId, sessionId});
    }

    @Override
    public List<String> getLuckyDogs(Integer sessionId, Integer luckyNumber) {
        String query = "select nickname from user u, points p where u.id = p.user_id and session_id = ? and bet_number = ?";
        return jdbcTemplate.query(query, new Object[]{sessionId, luckyNumber}, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString("nickname");
            }
        });
    }

    @Override
    public LotteryResult query() {
        String today = DateUtil.formatDate(new Date());
        String query = "select lucky_number, nickname from user u, points p, session s " +
                "  where u.id = p.user_id and s.id = p.session_id and s.lucky_number = p.bet_number and s.date = ?";
        LotteryResult result = new LotteryResult();
        List<String> luckyDogs = jdbcTemplate.query(query, new Object[]{today}, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                Integer luckyNumber = resultSet.getInt("lucky_number");
                result.setLuckyNumber(luckyNumber);
                return resultSet.getString("nickname");
            }
        });
        result.setLuckyDogs(luckyDogs);
        return result;
    }
}
