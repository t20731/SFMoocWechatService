package com.successfactors.t2.dao;

import java.util.List;
import java.util.Map;

public interface ExamDAO {
    Map<Integer, String> getAnswers(List<Integer> questionIds);
}
