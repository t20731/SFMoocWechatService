package com.successfactors.t2.service;

import com.successfactors.t2.domain.Question;

import java.util.List;

public interface QuestionService {
    int editQuestion(Question question);
    List<Question> loadQuestions(Integer sessionId, int status);
    int deleteQuestion(Integer sessionId, Integer questionId);
    int publish(Integer sessionId);
}
