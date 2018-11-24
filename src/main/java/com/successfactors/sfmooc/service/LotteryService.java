package com.successfactors.sfmooc.service;

import com.successfactors.sfmooc.domain.LotteryResult;

public interface LotteryService {

    int bet(String userId, Integer sessionId, Integer number);

    LotteryResult draw(Integer sessionId);

    LotteryResult query();
}
