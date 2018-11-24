package com.successfactors.sfmooc.dao;

import com.successfactors.sfmooc.domain.LotteryResult;

import java.util.List;

public interface LotteryDAO {
     int bet(String userId, Integer sessionId, Integer number);
     List<String> getLuckyDogs(Integer sessionId, Integer luckyNumber);
     LotteryResult query();
}
