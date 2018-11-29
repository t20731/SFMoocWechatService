package com.successfactors.sfmooc.service;

import com.successfactors.sfmooc.domain.Question;

import java.util.List;

public interface QuestionService {
    int editQuestion(Question question);
    List<Question> loadQuestions(Integer sessionId, int status);
    int deleteQuestion(Integer questionId);
    int publish(Integer sessionId);
    Integer getSessionIdByQuestionId(Integer questionId);
}
