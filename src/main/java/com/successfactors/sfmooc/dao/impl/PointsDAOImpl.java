package com.successfactors.sfmooc.dao.impl;

import com.successfactors.sfmooc.dao.PointsDAO;
import com.successfactors.sfmooc.domain.Points;
import com.successfactors.sfmooc.domain.RankingItem;
import com.successfactors.sfmooc.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PointsDAOImpl implements PointsDAO{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int getTotalPoints(String userId) {
        String query = "select sum(checkin)+sum(host)+sum(ifnull(exam,0))+sum(ifnull(lottery,0)) as total_points from points p, session s " +
                " where p.session_id = s.id and p.user_id = ?";
        return jdbcTemplate.queryForObject(query, new Object[]{userId}, new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getInt("total_points");
            }
        });
    }

    @Override
    public List<RankingItem> getUserRankingList(String season){
        String query = "select a.id, a.nickname, a.avatarUrl, ifnull(b.session_points, 0)  as total_points " +
                  "from  (select id, nickname, avatarUrl from user) a " +
                  "left outer join " +
                  "  (select p.user_id, sum(checkin)+sum(host)+sum(ifnull(exam,0))+sum(ifnull(lottery, 0)) as session_points from points p, session s " +
                  " where p.session_id = s.id group by p.user_id) b " +
                  "on a.id = b.user_id order by total_points desc";

        return jdbcTemplate.query(query, new RowMapper<RankingItem>() {
            @Override
            public RankingItem mapRow(ResultSet resultSet, int i) throws SQLException {
                RankingItem rankingItem = new RankingItem();
                rankingItem.setRank(i+1);
                rankingItem.setUserId(resultSet.getString("id"));
                rankingItem.setNickname(resultSet.getString("nickname"));
                rankingItem.setAvatarUrl(resultSet.getString("avatarUrl"));
                rankingItem.setPoints(resultSet.getInt("total_points"));
                return rankingItem;
            }
        });
    }

    @Override
    public List<Points> getPointsDetailForUser(String userId) {
        String query = "select start_date, checkin, host, exam, lottery from points p, session s " +
                "where p.session_id = s.id and p.user_id = ? order by start_date desc";
        return jdbcTemplate.query(query, new Object[]{userId}, new RowMapper<Points>() {
            @Override
            public Points mapRow(ResultSet resultSet, int i) throws SQLException {
               Points points = new Points();
               points.setDate(DateUtil.formatToDate(resultSet.getString("start_date")));
               points.setCheckin(resultSet.getInt("checkin"));
               points.setHost(resultSet.getInt("host"));
               points.setExam(resultSet.getInt("exam"));
               points.setLottery(resultSet.getInt("lottery"));
               return points;
            }
        });
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updatePointsForHost(Integer sessionId, String userId) {
        int sessionCount = getSessionCount(sessionId, userId);
        if (sessionCount == 0) {
            return jdbcTemplate.update("insert into points(user_id, session_id, host) values (?, ?, ?)",
                    new Object[]{userId, sessionId, 5});
        } else {
            return jdbcTemplate.update("update points set host = 5 where user_id = ? and session_id = ? ",
                    new Object[]{userId, sessionId});
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updatePointsForCheckin(Integer sessionId, String userId) {
        int sessionCount = getSessionCount(sessionId, userId);
        if (sessionCount == 0) {
            return jdbcTemplate.update("insert into points(user_id, session_id, checkin) values (?, ?, ?)",
                    new Object[]{userId, sessionId, 1});
        } else {
            return jdbcTemplate.update("update points set checkin = 1 where user_id = ? and session_id = ? ",
                    new Object[]{userId, sessionId});
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updatePointsForLottery(Integer sessionId, Integer luckyNumber) {
        int result = jdbcTemplate.update("update points set lottery = 0 where session_id = ? and bet_number != ?",
                new Object[]{sessionId, luckyNumber});

        List<String> luckyUsers = getLuckyUsers(sessionId,luckyNumber);
        for (String luckyUser : luckyUsers){
            int point = getLotteryPointForUser(luckyUser);
            result = jdbcTemplate.update("update points set lottery = ? where session_id = ? and bet_number = ?",
                    new Object[]{point, sessionId, luckyNumber});
        }
        return result;
    }

    private int getSessionCount(Integer sessionId, String userId){
        return jdbcTemplate.queryForObject("select count(1) as cnt from points where session_id = ?" +
                " and user_id = ?", new Object[]{sessionId, userId}, new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getInt("cnt");
            }
        });
    }

    @Override
    public Points getPointsById(Integer sessionId, String userId){
        List<Points> pointsList = jdbcTemplate.query("select user_id, session_id, checkin, host, exam from points where session_id = ?" +
                " and user_id = ?", new Object[]{sessionId, userId}, new RowMapper<Points>() {
            @Override
            public Points mapRow(ResultSet resultSet, int i) throws SQLException {
                Points point = new Points();
                point.setUserId(resultSet.getString("user_id"));
                point.setSessionId(resultSet.getInt("session_id"));
                point.setCheckin(resultSet.getInt("checkin"));
                point.setHost(resultSet.getInt("host"));
                Object exam = resultSet.getObject("exam");
                if (exam == null) {
                    point.setExam(null);
                } else {
                    point.setExam((Integer) exam);
                }
                return point;
            }
        });
        return pointsList.size() == 0 ? null : pointsList.get(0);
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updatePointsForExam(Integer sessionId, String userId, int points) {
        Points point = getPointsById(sessionId, userId);
        if (point == null) {
            return jdbcTemplate.update("insert into points(user_id, session_id, exam) values (?, ?, ?)",
                    new Object[]{userId, sessionId, points});
        } else {
            if (point.getExam() == null) {
                String updateSql = "update points set exam = ? where session_id = ? and user_id = ?";
                return jdbcTemplate.update(updateSql, new Object[]{points, sessionId, userId});
            } else {
                return -1;
            }
        }
    }

    private List<String> getLuckyUsers(Integer sessionId, Integer luckyNumber){
        return jdbcTemplate.query("select user_id from points where session_id = ?" +
                " and bet_number = ?", new Object[]{sessionId, luckyNumber}, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString("user_id");
            }
        });
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
}
