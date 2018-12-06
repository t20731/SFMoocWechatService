package com.successfactors.sfmooc.dao.impl;

import com.successfactors.sfmooc.dao.SessionDAO;
import com.successfactors.sfmooc.domain.*;
import com.successfactors.sfmooc.utils.Constants;
import com.successfactors.sfmooc.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class SessionDAOImpl implements SessionDAO {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int delete(Integer sessionId) {
        List<Integer> questionIdList = getQuestionIdBySessionId(sessionId);
        logger.info("question id is: " + questionIdList.toString());
        if (questionIdList.size() > 0) {
            //delete option
            StringBuilder deleteOption = new StringBuilder("delete from `option` where question_id in ");
            String whereClause = generateWhereClause(questionIdList);
            deleteOption.append(whereClause);
            Object[] params = questionIdList.toArray(new Object[questionIdList.size()]);
            logger.info("deleteOption sql is : " + deleteOption);
            int optionCount = jdbcTemplate.update(deleteOption.toString(), params);
            logger.info("delete  " + optionCount + " records in option table");
            //delete question
            int questionCount = jdbcTemplate.update("delete from question where session_id = ?",
                    new Object[]{sessionId});
            logger.info("delete  " + questionCount + " records in question table");
        }
        //delete session
        int sessionCount = jdbcTemplate.update("delete from session where id = ?",
                new Object[]{sessionId});
        logger.info("delete  " + sessionCount + " records in session table");
        return sessionCount;
    }

    private List<Integer> getQuestionIdBySessionId(Integer sessionId) {
        List<Integer> questionIdList = jdbcTemplate.query("select id from question where session_id = ?",
                new Object[]{sessionId}, new RowMapper<Integer>() {
                    @Override
                    public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
                        return resultSet.getInt("id");
                    }
                });
        return questionIdList;
    }

    private String generateWhereClause(List<Integer> idList) {
        StringBuilder whereClause = new StringBuilder();
        whereClause.append("(");
        int size = idList.size();
        for (int i = 0; i < size; i++) {
            whereClause.append(i != size - 1 ? "?, " : "?");
        }
        whereClause.append(")");
        return whereClause.toString();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int register(String userId, Integer sessionId) {
        int count = getUserSessionCount(userId, sessionId);
        int result = 0;
        if (count == 0) {
            result = jdbcTemplate.update("insert into user_session_map(user_id, session_id) " +
                    "values (?, ?)", new Object[]{userId, sessionId});
        }
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int like(String userId, Integer sessionId, Integer like) {
        int count = getUserSessionCount(userId, sessionId);
        int result = 0;
        if (count == 1) {
            result = jdbcTemplate.update("update user_session_map set `like` = ? " +
                    "where user_id = ? and session_id = ?", new Object[]{like, userId, sessionId});
        }
        else {
            logger.error("Not found the user " + userId +" for this session " + sessionId +". Please check your session");
        }
        return result;
    }
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int getSessionLikeCount(Integer sessionId ){
        int count;
        List<Integer> result =  jdbcTemplate.query("select count(1) as cnt from user_session_map where " +
                "session_id = ? and `like` = 1", new Object[]{sessionId}, new RowMapper<Integer>() {
            @Nullable
            @Override
            public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getInt("cnt");
            }
        });
        if(result == null || result.size() == 0){
            count = 0;
        } else {
            count = result.get(0);
        }
        return count;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int getLike(String userId, Integer sessionId) {
        int count;
        List<Integer> result = jdbcTemplate.query("select `like` as isLike from user_session_map " +
                "where user_id = ? and session_id = ?",new Object[]{userId, sessionId},  new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getInt("isLike");
            }});
        if(result == null || result.size() == 0){
            count = 0;
        } else {
            count = result.get(0);
        }
        return count;
    }

    @Override
    public int getEnrollments(Integer sessionId) {
        return jdbcTemplate.queryForObject("select count(1) as cnt from user_session_map where " +
                "session_id = ?", new Object[]{sessionId}, new RowMapper<Integer>() {
            @Nullable
            @Override
            public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getInt("cnt");
            }
        });
    }

    private int getUserSessionCount(String userId, Integer sessionId) {
        return jdbcTemplate.queryForObject("select count(1) as cnt from user_session_map where " +
                "user_id = ? and session_id = ?", new Object[]{userId, sessionId}, new RowMapper<Integer>() {
            @Nullable
            @Override
            public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getInt("cnt");
            }
        });
    }

    @Override
    public UserSession getSessionById(Integer sessionId, String userId) {
        String query = "select s.id as sid, s.topic, s.description, s.start_date, s.end_date, s.difficulty, s.checkin_code, s.question_status, "+
                "d.name as direction, l.name as location, s.status, s.image_src, " +
                "u.id as uid, u.nickname, u.avatarUrl, s.type_id from user u, direction d, location l, session s where s.owner = u.id " +
                "and s.direction_id = d.id and s.location_id = l.id and s.id = ? ";
        List<Session> sessions = jdbcTemplate.query(query, new Object[]{sessionId}, new RowMapper<Session>() {
            @Nullable
            @Override
            public Session mapRow(ResultSet resultSet, int i) throws SQLException {
                Session session = new Session();
                session.setId(resultSet.getInt("sid"));
                session.setTopic(resultSet.getString("topic"));
                session.setDescription(resultSet.getString("description"));
                session.setStartDate(DateUtil.formatDateToMinutes(resultSet.getString("start_date")));
                session.setEndDate(DateUtil.formatDateToMinutes(resultSet.getString("end_date")));
                session.setDifficulty(resultSet.getInt("difficulty"));
                Location location = new Location();
                location.setName(resultSet.getString("location"));
                session.setLocation(location);
                Direction direction = new Direction();
                direction.setName(resultSet.getString("direction"));
                session.setDirection(direction);
                session.setTileImageSrc(resultSet.getString("image_Src"));
                session.setStatus(resultSet.getInt("status"));
                session.setTypeId(resultSet.getInt("type_id"));
                User user = new User();
                user.setId(resultSet.getString("uid"));
                user.setNickName(resultSet.getString("nickname"));
                user.setAvatarUrl(resultSet.getString("avatarUrl"));
                session.setOwner(user);
                if(user.getId() != null && user.getId().equalsIgnoreCase(userId)){
                    session.setCheckInCode(resultSet.getString("checkin_code"));
                }
                session.setQuestionStatus(resultSet.getInt("question_status"));
                return session;
            }
        });
        if (sessions == null || sessions.isEmpty()) {
            return null;
        } else {
            UserSession userSession = new UserSession();
            userSession.setSession(sessions.get(0));
            if (userId == null) {
                userSession.setUserRegistered(false);
            } else {
                int count = getUserSessionCount(userId, sessionId);
                if (count > 0) {
                    userSession.setUserRegistered(true);
                } else {
                    userSession.setUserRegistered(false);
                }
            }
            return userSession;
        }
    }

    @Override
    public String getCheckInCode(Integer sessionId) {
        return jdbcTemplate.queryForObject("select checkin_code from session where id = ?",
                new Object[]{sessionId}, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString("checkin_code");
            }
        });
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int editSession(Session session) {
        String now = DateUtil.formatDateTime(new Date());
        Integer sessionId = session.getId();
        if (sessionId != null) {
            String updateSql = "update session set topic = ?, description = ?, start_date = ?, end_date = ?, " +
                    "location_id = ?, direction_id = ?, difficulty = ?, last_modified_date = ? where id = ?";
            return jdbcTemplate.update(updateSql, new Object[]{session.getTopic(), session.getDescription(),
                    session.getStartDate(), session.getEndDate(), session.getLocation().getId(), session.getDirection().getId(),
                    session.getDifficulty(), now, session.getId()});
        } else {
            String insertSQL = "insert into session(owner, topic, description, start_date, end_date, location_id, " +
                    "direction_id, difficulty, image_src, status, created_date, last_modified_date) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            return jdbcTemplate.update(insertSQL, new Object[]{session.getOwner().getId(), session.getTopic(),
                    session.getDescription(), session.getStartDate(), session.getEndDate(), session.getLocation().getId(),
                    session.getDirection().getId(), session.getDifficulty(), session.getTileImageSrc(), 0, now, now});
        }
    }

    @Override
    public int cancel(Integer sessionId) {
        return jdbcTemplate.update("update session set status = ? where id = ?", new Object[]{-1, sessionId});
    }

    @Override
    public int start(Integer sessionId) {
        return jdbcTemplate.update("update session set status = ? where id = ?", new Object[]{1, sessionId});
    }

    public String buildSessionQuery(FetchParams fetchParams, List<Object> params) {
        String query = "select s2.id as sid, s2.topic, s2.difficulty, s2.start_date, s2.end_date, l.name as location, s2.direction_id, s2.image_src, s2.status, s2.created_date, " +
                "s2.last_modified_date, u.id as uid, u.nickname, b.total_members from user u, session s2, direction d, location l, " +
                "(select a.id, count(a.user_id) as total_members  from (select s1.id, usmap.user_id from session s1 left outer join user_session_map usmap " +
                "on s1.id = usmap.session_id) a group by a.id) b " +
                " where s2.owner = u.id and s2.direction_id = d.id and s2.location_id = l.id and s2.id = b.id ";
        StringBuilder sb = new StringBuilder(query);
        int direction = fetchParams.getDirectionId();
        if (direction > 0) {
            sb.append("and s2.direction_id = ? ");
            params.add(direction);
        }
        int difficulty = fetchParams.getDifficulty();
        if (difficulty != -1) {
            sb.append("and s2.difficulty = ? ");
            params.add(difficulty);
        }
        int status = fetchParams.getStatus();
        if (status != -1) {
            sb.append("and s2.status = ? ");
            params.add(status);
        }
        String owner = fetchParams.getOwnerId();
        if (owner != null) {
            sb.append("and s2.owner = ? ");
            params.add(owner);
        }
        String keyWord = fetchParams.getKeyWord();
        if (!StringUtils.isEmpty(keyWord)) {
            sb.append("and s2.topic like concat('%',?,'%') ");
            params.add(keyWord);
        }
        int completed = fetchParams.getCompleted();
        if (completed == 1) {
            sb.append("and s2.end_date < now() ");
        } else {
            sb.append("and s2.end_date >= now() ");
        }
        String userId = fetchParams.getUserId();
        if (userId != null) {
            sb = new StringBuilder("select s.* from (").append(sb.toString()).append(") s, user_session_map usmap1 " +
                    "where s.sid = usmap1.session_id and usmap1.user_id = ? ");
            params.add(userId);
        }
        return sb.toString();
    }

    @Override
    public List<Session> getSessionList(FetchParams fetchParams) {
        if(fetchParams.getCompleted() == 1){
            return getCompletedSessionList(fetchParams);
        }
        List<Object> params = new ArrayList<>(16);
        StringBuilder sb = new StringBuilder(buildSessionQuery(fetchParams, params));
        sb.append("order by ");
        String orderField = fetchParams.getOrderField();
        if (!StringUtils.isEmpty(orderField) && Constants.ORDER_FIELD_SET.contains(orderField)) {
            sb.append(orderField).append(" ");
            String order = fetchParams.getOrder();
            if (!StringUtils.isEmpty(order)) {
                sb.append(order);
            } else {
                sb.append("desc");
            }
            sb.append(",");
        }
        sb.append(" start_date asc limit ?, ?");
        params.add(fetchParams.getStartPage());
        params.add(fetchParams.getPageSize());
        logger.info("Session list query: " + sb.toString());
        logger.info("Params: " + params);
        return executeSessionQuery(sb.toString(), params.toArray());
    }

    private List<Session> getCompletedSessionList(FetchParams fetchParams){
        if(fetchParams.getCompleted() != 1 || fetchParams.getUserId() == null){
            return Collections.emptyList();
        }
        List<Object> params = new ArrayList<>(16);
        StringBuilder sb = new StringBuilder("select * from ( ");
        sb.append("(").append(buildSessionQuery(fetchParams, params)).append(") union ");
        fetchParams.setOwnerId(fetchParams.getUserId());
        fetchParams.setUserId(null);
        sb.append("(").append(buildSessionQuery(fetchParams, params)).append(")) cs ");
        sb.append("order by cs.end_date desc limit ?, ?");
        params.add(fetchParams.getStartPage());
        params.add(fetchParams.getPageSize());
        logger.info("Completed Session query: " + sb.toString());
        logger.info("Params: " + params);
        return executeSessionQuery(sb.toString(), params.toArray());
    }

    private List<Session> executeSessionQuery(String query, Object[] params){
        List<Session> sessionList = jdbcTemplate.query(query, params, new RowMapper<Session>() {
            @Override
            public Session mapRow(ResultSet resultSet, int i) throws SQLException {
                Session session = new Session();
                session.setId(resultSet.getInt("sid"));
                session.setTopic(resultSet.getString("topic"));
                session.setDifficulty(resultSet.getInt("difficulty"));
                session.setTileImageSrc(resultSet.getString("image_Src"));
                session.setStartDate(DateUtil.formatDateToMinutes(resultSet.getString("start_date")));
                session.setCreatedDate(DateUtil.formatDateToSecond(resultSet.getString("created_date")));
                session.setLastModifiedDate(DateUtil.formatDateToSecond(resultSet.getString("last_modified_date")));
                Location location = new Location();
                location.setName(resultSet.getString("location"));
                session.setLocation(location);
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
        return sessionList;
    }

    @Override
    public int updateCheckinCode(Integer sessionId, String checkinCode) {
        return jdbcTemplate.update("update session set checkin_code = ? where id = ?", new Object[]{checkinCode, sessionId});
    }

    @Override
    public List<String> getAttendeeList(Integer sessionId) {
        String query = "select user_id from points where checkin > 0 or host > 0 and session_id = ?";
        return jdbcTemplate.query(query, new Object[]{sessionId}, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString("user_id");
            }
        });
    }

    @Override
    public int updateLuckyNumber(Integer sessionId, Integer luckyNumber) {
        return jdbcTemplate.update("update session set lucky_number = ? where id = ?", new Object[]{luckyNumber, sessionId});
    }
}
