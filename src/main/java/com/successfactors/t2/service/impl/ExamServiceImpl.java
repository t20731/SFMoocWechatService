package com.successfactors.t2.service.impl;

import com.successfactors.t2.dao.ExamDAO;
import com.successfactors.t2.dao.PointsDAO;
import com.successfactors.t2.domain.Answer;
import com.successfactors.t2.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class ExamServiceImpl implements ExamService{

    @Autowired
    private ExamDAO examDAO;

    @Autowired
    private PointsDAO pointsDAO;

    @Override
    public Answer submitAnswers(Integer sessionId, Answer answer) {
        Map<Integer, String> selectedMap = answer.getAnswerMap();
        Map<Integer, String> answerMap = examDAO.getAnswers(new ArrayList<>(selectedMap.keySet()));
        int points = 0;
        if(answerMap != null){
            for(Map.Entry<Integer, String> entry : answerMap.entrySet()){
                Integer questionId = entry.getKey();
                String answerNumber = entry.getValue();
                String selected = selectedMap.get(questionId);
                if(answerNumber.equals(selected)){
                    points++;
                }
            }
        }
        if(points > 0){
            int status = pointsDAO.updatePointsForExam(sessionId, answer.getUserId(), points);
            if(status == -1){
                return null;
            }
        }
        Answer result = new Answer();
        result.setPoints(points);
        result.setAnswerMap(answerMap);
        return result;
    }
}
