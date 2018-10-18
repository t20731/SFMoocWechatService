package com.successfactors.t2.dao;

import com.successfactors.t2.domain.Question;

import java.util.List;

public interface QuestionDAO {

    int editQuestion(Question question);
    List<Question> loadQuestions(Integer sessionId, Integer status);
    int deleteQuestion(Integer sessionId, Integer questionId);
    int publish(Integer sessionId);
}
