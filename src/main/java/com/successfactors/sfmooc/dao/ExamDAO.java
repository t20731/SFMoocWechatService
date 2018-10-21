package com.successfactors.sfmooc.dao;

import java.util.List;
import java.util.Map;

public interface ExamDAO {
    Map<Integer, String> getAnswers(List<Integer> questionIds);
}
