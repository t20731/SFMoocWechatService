package com.successfactors.sfmooc.dao.impl;

import com.successfactors.sfmooc.dao.LotteryDAO;
import com.successfactors.sfmooc.domain.LuckyDog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
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
    public List<LuckyDog> getLuckyDogs(Integer sessionId, Integer luckyNumber) {
        String query = "select id, nickname from user u, points p where u.id = p.user_id and session_id = ? and bet_number = ?";
        List<LuckyDog> luckyDogs = jdbcTemplate.query(query, new Object[]{sessionId, luckyNumber}, new RowMapper<LuckyDog>() {
            @Override
            public LuckyDog mapRow(ResultSet resultSet, int i) throws SQLException {
                LuckyDog luckyDog = new LuckyDog();
                luckyDog.setUserId(resultSet.getString("id"));
                luckyDog.setNickName(resultSet.getString("nickname"));
                return luckyDog;
            }
        });
        if(!CollectionUtils.isEmpty(luckyDogs)){
            for(LuckyDog luckyDog : luckyDogs){
                int point = getLotteryPointForUser(luckyDog.getUserId());
                luckyDog.setPoints(point);
            }
        }
        return luckyDogs;
    }

    private int getLotteryPointForUser(String userId){
        int count = jdbcTemplate.queryForObject("select count(1) as cnt from points where user_id = ?" +
                " and lottery > 0", new Object[]{userId}, new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getInt("cnt");
            }
        });
        if (count >= 5){
            return 1;
        }else{
            return 5-count;
        }
    }

//    @Override
//    public LotteryResult query() {
//        String today = DateUtil.formatDate(new Date());
//        String query = "select lucky_number, nickname from user u, points p, session s " +
//                "  where u.id = p.user_id and s.id = p.session_id and s.lucky_number = p.bet_number and s.date = ?";
//        LotteryResult result = new LotteryResult();
//        List<String> luckyDogs = jdbcTemplate.query(query, new Object[]{today}, new RowMapper<String>() {
//            @Override
//            public String mapRow(ResultSet resultSet, int i) throws SQLException {
//                Integer luckyNumber = resultSet.getInt("lucky_number");
//                result.setLuckyNumber(luckyNumber);
//                return resultSet.getString("nickname");
//            }
//        });
//        result.setLuckyDogs(luckyDogs);
//        return result;
//    }
}
