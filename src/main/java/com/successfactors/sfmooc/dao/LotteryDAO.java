package com.successfactors.sfmooc.dao;

import com.successfactors.sfmooc.domain.LuckyDog;

import java.util.List;

public interface LotteryDAO {
     int bet(String userId, Integer sessionId, Integer number);
     List<LuckyDog> getLuckyDogs(Integer sessionId, Integer luckyNumber);
//     LotteryResult query();
}
