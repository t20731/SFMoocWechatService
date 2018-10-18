package com.successfactors.t2.service;

import com.successfactors.t2.domain.LotteryResult;

public interface LotteryService {

    int bet(String userId, Integer sessionId, Integer number);

    LotteryResult draw(String userId, Integer sessionId);

    LotteryResult query();
}
