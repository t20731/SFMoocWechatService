package com.successfactors.sfmooc.dao.impl;

import com.successfactors.sfmooc.dao.SessionDAO;
import com.successfactors.sfmooc.domain.Direction;
import com.successfactors.sfmooc.domain.Session;
import com.successfactors.sfmooc.domain.SessionVO;
import com.successfactors.sfmooc.domain.User;
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
public class SessionDAOImpl implements SessionDAO{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int editSession(Session session) {
        String insertSQL = "insert into session(owner, topic, description, start_date, end_date, location, " +
                "direction_id, difficulty, status, created_date) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String created_date = DateUtil.formatDateTime(new Date());
        int status = jdbcTemplate.update(insertSQL, new Object[]{session.getOwner().getId(), session.getTopic(),
                session.getDescription(), session.getStartDate(), session.getEndDate(), session.getLocation(),
                session.getDirection().getId(), 0, 1, created_date});
        return status;
    }

    @Override
    public List<Session> getSessionList() {
       String query = "select s2.id as sid, s2.topic, s2.location, s2.direction_id,  s2.status, u.id as uid, u.nickname, b.total_members " +
               " from user u, session s2,  (select a.id, count(a.user_id) as total_members  from " +
               " (select s1.id, usmap.user_id from session s1 left outer join user_session_map usmap  on  s1.id = usmap.session_id) a group by a.id) b " +
               " where s2.owner = u.id and s2.id = b.id;";
        return jdbcTemplate.query(query, new RowMapper<Session>() {
            @Override
            public Session mapRow(ResultSet resultSet, int i) throws SQLException {
                Session session = new Session();
                session.setId(resultSet.getInt("sid"));
                session.setTopic(resultSet.getString("topic"));
                session.setLocation(resultSet.getString("location"));
                Direction direction = new Direction();
                direction.setId(resultSet.getInt("direction_id"));
                session.setDirection(direction);
                session.setStatus(resultSet.getInt("status"));
                User user = new User();
                user.setId(resultSet.getString("uid"));
                user.setNickName(resultSet.getString("nickname"));
                session.setOwner(user);
                session.setEnrollments(resultSet.getInt("total_members"));
                return session;
            }
        });
    }


//    @Override
//    public List<SessionVO> loadHistorySessions(){
//        String query = "select id, owner, date, season, episode from session where question_status = 1" +
//                " order by date desc";
//        return jdbcTemplate.query(query, new RowMapper<SessionVO>() {
//            @Override
//            public SessionVO mapRow(ResultSet resultSet, int i) throws SQLException {
//                SessionVO session = new SessionVO();
//                session.setId(resultSet.getInt("id"));
//                session.setOwner(resultSet.getString("owner"));
//                session.setDate(resultSet.getString("date"));
//                String season = resultSet.getString("season");
//                String episode = resultSet.getString("episode");
//                session.setTitle(season + episode);
//                return session;
//            }
//        });
//    }
//
//
//    @Override
//    public List<Session> getSessionList(String season) {
//       String query = "select s.id, s.owner, s.date, u.nickname, u.avatarUrl, u.department from user u, session s where "+
//                " s.owner = u.id and s.season = ? order by s.date";
//        return jdbcTemplate.query(query, new Object[]{season}, new RowMapper<Session>() {
//            @Override
//            public Session mapRow(ResultSet resultSet, int i) throws SQLException {
//                Session session = new Session();
//                session.setSessionId(resultSet.getInt("id"));
//                session.setOwner(resultSet.getString("owner"));
//                session.setSessionDate(resultSet.getString("date"));
//                session.setNickname(resultSet.getString("nickname"));
//                session.setAvatarUrl(resultSet.getString("avatarUrl"));
//                session.setDepartment(resultSet.getString("department"));
//                return session;
//            }
//        });
//    }
//
//    @Override
//    public Session getSessionByDate(String date){
//       String query = "select id, owner, date, checkin_code from session where date = ?";
//       List<Session> sessions = jdbcTemplate.query(query, new Object[]{date}, new SessionRowMapper());
//       if (sessions != null && !sessions.isEmpty()){
//           return sessions.get(0);
//       }else {
//           return null;
//       }
//    }
//
//    @Override
//    public Session getSessionByOwner(String userId){
//        String season = getCurrentSeason();
//        String query = "select id, owner, date, checkin_code from session where season = ? and owner = ?";
//        List<Session> sessions = jdbcTemplate.query(query, new Object[]{season, userId}, new SessionRowMapper());
//        if (sessions != null && !sessions.isEmpty()){
//            return sessions.get(0);
//        }else {
//            return null;
//        }
//    }
//
//    @Override
//    public int updateCheckinCode(Integer sessionId, String checkinCode) {
//        return jdbcTemplate.update("update session set checkin_code = ? where id = ?", new Object[]{checkinCode, sessionId});
//    }
//
//    @Override
//    public List<String> getAttendeeList() {
//        String today = DateUtil.formatDate(new Date());
//        String query = "select user_id from points where (checkin > 0 or host > 0) and session_id in (select id from session" +
//                " where date = ?)";
//        return jdbcTemplate.query(query, new Object[]{today}, new RowMapper<String>() {
//            @Override
//            public String mapRow(ResultSet resultSet, int i) throws SQLException {
//                return resultSet.getString("user_id");
//            }
//        });
//    }
//
//    @Override
//    public int updateLuckyNumber(Integer sessionId, Integer luckyNumber) {
//        return jdbcTemplate.update("update session set lucky_number = ? where id = ?", new Object[]{luckyNumber, sessionId});
//    }
//
//    @Override
//    public String getCurrentSeason() {
//        String today = DateUtil.formatDate(new Date());
//        String query = "select season from session group by season having min(date) <= ? and max(date) >= ?";
//        List<String> seasonList = jdbcTemplate.query(query, new Object[]{today, today}, new RowMapper<String>() {
//            @Override
//            public String mapRow(ResultSet resultSet, int i) throws SQLException {
//                return resultSet.getString("season");
//            }
//        });
//        if (seasonList == null || seasonList.isEmpty()) {
//            return "S1";
//        } else {
//            return seasonList.get(0);
//        }
//    }
//
//    class SessionRowMapper implements RowMapper<Session> {
//        @Override
//        public Session mapRow(ResultSet resultSet, int i) throws SQLException {
//            Session session = new Session();
//            session.setSessionId(resultSet.getInt("id"));
//            session.setOwner(resultSet.getString("owner"));
//            session.setSessionDate(resultSet.getString("date"));
//            session.setCheckinCode(resultSet.getString("checkin_code"));
//            return session;
//        }
//    }
}