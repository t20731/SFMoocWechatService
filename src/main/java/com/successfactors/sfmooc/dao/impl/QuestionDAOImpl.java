package com.successfactors.sfmooc.dao.impl;

import com.successfactors.sfmooc.dao.QuestionDAO;
import com.successfactors.sfmooc.domain.Option;
import com.successfactors.sfmooc.domain.Question;
import com.successfactors.sfmooc.utils.Constants;
import com.successfactors.sfmooc.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.sql.*;
import java.util.*;
import java.util.Date;

@Repository
public class QuestionDAOImpl implements QuestionDAO {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int editQuestion(Question question) {
        Integer questionId = question.getId();
        if (questionId == null) {
            logger.info("add question");
            Integer questionCount = getQuestionCount(question.getSessionId());
            logger.info("existing " + questionCount + " question(s)");
            if (questionCount < Constants.QUESTION_THRESHOLD) {
                return addQuestion(question);
            } else {
                return -1;
            }
        } else {
            logger.info("update question " + questionId);
            return updateQuestion(question);
        }
    }

    @Override
    public List<Question> loadQuestions(Integer sessionId, Integer status) {
        Map<Integer, Question> questionMap = new LinkedHashMap<>();
        StringBuilder query = new StringBuilder("select s.start_date, q.id as qid, q.content as q_content, o.id as oid, o.number, o.content as o_content, o.is_answer " +
                "from session s, question q, `option` o where s.id = q.session_id and " +
                "q.id = o.question_id and s.id = ? ");
        List<Object> params = new ArrayList<>();
        params.add(sessionId);
        if(status == 1){
            query.append("and s.question_status = ? ");
            params.add(status);
        }
        query.append("order by q.id, o.number");
        logger.info("load questions sql: " + query.toString());
        jdbcTemplate.query(query.toString(), params.toArray(), new RowMapper<Question>() {
            @Override
            public Question mapRow(ResultSet resultSet, int i) throws SQLException {
                Integer questionId = resultSet.getInt("qid");
                Question question = questionMap.get(questionId);
                if (question == null) {
                    question = new Question();
                    question.setId(questionId);
                    question.setSessionId(sessionId);
                    question.setContent(resultSet.getString("q_content"));
                    question.setStatus(status);
                    questionMap.put(questionId, question);
                }
                List<Option> options = question.getOptions();
                if (options == null) {
                    options = new ArrayList<>();
                    question.setOptions(options);
                }
                Option option = new Option();
                option.setId(resultSet.getInt("oid"));
                option.setQuestionId(questionId);
                option.setNumber(resultSet.getString("number"));
                option.setContent(resultSet.getString("o_content"));
                if(status == 0){
                    option.setIsAnswer(resultSet.getInt("is_answer"));
                }
                options.add(option);
                return question;
            }
        });
        return new ArrayList<>(questionMap.values());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int deleteQuestion(Integer questionId) {
        logger.info("Start to delete question: " + questionId);
        String deleteOptionSql = "delete from `option` where question_id = ?";
        int optionCount = jdbcTemplate.update(deleteOptionSql, questionId);
        logger.info("delete " + optionCount + " records in option table");
        String deleteQuestionSql = "delete from question where id = ? and status = 0";
        int questionCount = jdbcTemplate.update(deleteQuestionSql, new Object[]{questionId});
        logger.info("delete  " + questionCount + " records in question table");
        return questionCount;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int publish(Integer sessionId) {
        int questionStatus = getQuestionStatus(sessionId);
        if(questionStatus == 1){
            return -1;
        }
        String updateSessionSql = "update session set question_status = 1 where id = ?";
        int status = jdbcTemplate.update(updateSessionSql, new Object[]{sessionId});
        String updateQuestionSql = "update question set status = 1 where session_id = ?";
        status += jdbcTemplate.update(updateQuestionSql, new Object[]{sessionId});
        return status;
    }

    private int getQuestionCount(Integer sessionId) {
        return jdbcTemplate.queryForObject("select count(1) as cnt from question where session_id = ?",
                new Object[]{sessionId}, new RowMapper<Integer>() {
                    @Override
                    public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
                        return resultSet.getInt("cnt");
                    }
                });
    }

    private int getQuestionStatus(Integer sessionId) {
        return jdbcTemplate.queryForObject("select question_status as status from session where id = ?",
                new Object[]{sessionId}, new RowMapper<Integer>() {
                    @Override
                    public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
                        return resultSet.getInt("status");
                    }
                });
    }

    private int updateQuestion(Question question) {
        int status = jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                String updateSql = "update question set content = ? where id = ?";
                PreparedStatement ps = connection.prepareStatement(updateSql);
                int index = 1;
                ps.setString(index++, question.getContent());
                ps.setInt(index, question.getId());
                return ps;
            }
        });
        String deleteSql = "delete from `option` where question_id = ?";
        int deleteCount = jdbcTemplate.update(deleteSql, question.getId());
        logger.info("delete " + deleteCount + " records in option table");
        insertOptions(question, question.getId());
        return status;
    }

    private int addQuestion(Question question) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int status = jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                String insertSql = "insert into question(session_id, content) values (?, ?)";
                PreparedStatement ps = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
                int index = 1;
                ps.setInt(index++, question.getSessionId());
                ps.setString(index, question.getContent());
                return ps;
            }
        }, keyHolder);
        int generatedId = keyHolder.getKey().intValue();
        if (generatedId > 0) {
            insertOptions(question, generatedId);
        }
        return status;
    }

    private int[] insertOptions(Question question, Integer questionId) {
        if (!CollectionUtils.isEmpty(question.getOptions())) {
            String insertSql = "insert into `option`(question_id, number, content, is_answer) values (?, ?, ?, ?)";
            List<Object[]> bacthArgs = new ArrayList<>();
            for (Option option : question.getOptions()) {
                Object[] args = new Object[]{questionId, option.getNumber(), option.getContent(), option.getIsAnswer()};
                bacthArgs.add(args);
            }
            int[] insertCount = jdbcTemplate.batchUpdate(insertSql, bacthArgs);
            logger.info("insert " + insertCount.length + " records into option table");
            return insertCount;
        }
        return new int[]{};
    }
}
