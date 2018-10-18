package com.successfactors.t2.service;

import com.successfactors.t2.domain.Answer;

public interface ExamService {
    Answer submitAnswers(Integer sessionId, Answer answer);
}
