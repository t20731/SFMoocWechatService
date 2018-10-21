package com.successfactors.sfmooc.dao;

import com.successfactors.sfmooc.domain.Question;

import java.util.List;

public interface QuestionDAO {

    int editQuestion(Question question);
    List<Question> loadQuestions(Integer sessionId, Integer status);
    int deleteQuestion(Integer sessionId, Integer questionId);
    int publish(Integer sessionId);
}
