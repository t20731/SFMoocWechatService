package com.successfactors.t2.dao;

import com.successfactors.t2.domain.LotteryResult;

import java.util.List;

public interface LotteryDAO {
     int bet(String userId, Integer sessionId, Integer number);
     List<String> getLuckyDogs(Integer sessionId, Integer luckyNumber);
     LotteryResult query();
}
