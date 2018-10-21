package com.successfactors.sfmooc.service;

import com.successfactors.sfmooc.domain.Answer;

public interface ExamService {
    Answer submitAnswers(Integer sessionId, Answer answer);
}
