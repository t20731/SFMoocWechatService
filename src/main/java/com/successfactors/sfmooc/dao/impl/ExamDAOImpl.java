package com.successfactors.sfmooc.dao.impl;

import com.successfactors.sfmooc.dao.ExamDAO;
import com.successfactors.sfmooc.domain.RankingItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class ExamDAOImpl implements ExamDAO {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Map<Integer, String> getAnswers(List<Integer> questionIds) {
        Map<Integer, String> answerMap = new HashMap<>();
        int size = questionIds.size();
        Object[] params = new Object[size];
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < size; i++){
            sb.append("?");
            params[i] = questionIds.get(i);
            if(i != size - 1){
                sb.append(",");
            }
        }
        String query = "select question_id, number from `option` where is_answer = 1 " +
                "and question_id in (" + sb.toString() + ")";
        logger.info("query: " + query);
        List<String> objects = jdbcTemplate.query(query, params, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                Integer questionId = resultSet.getInt("question_id");
                String number = resultSet.getString("number");
                answerMap.put(questionId, number);
                return number;
            }
        });
        return answerMap;
    }

    @Override
    public List<RankingItem> getExamRankingList(Integer sessionId){
        String query = "select a.id, a.nickname, a.avatarUrl, b.exam_points as exam_points " +
                "from  (select id, nickname, avatarUrl from user) a " +
                "inner join " +
                "  (select p.user_id, ifnull(exam,0) as exam_points from points p " +
                " where p.session_id = ? ) b " +
                "on a.id = b.user_id order by exam_points desc";

        return jdbcTemplate.query(query, new Object[]{sessionId}, (resultSet,  i) -> {
                RankingItem rankingItem = new RankingItem();
                rankingItem.setRank(i+1);
                rankingItem.setUserId(resultSet.getString("id"));
                rankingItem.setNickname(resultSet.getString("nickname"));
                rankingItem.setAvatarUrl(resultSet.getString("avatarUrl"));
                rankingItem.setPoints(resultSet.getInt("exam_points"));
                return rankingItem;
            });
    }
}
