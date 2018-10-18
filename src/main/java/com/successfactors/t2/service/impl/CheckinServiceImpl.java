package com.successfactors.t2.service.impl;

import com.successfactors.t2.dao.CheckinDAO;
import com.successfactors.t2.domain.Session;
import com.successfactors.t2.service.CacheService;
import com.successfactors.t2.service.CheckinService;
import com.successfactors.t2.service.RankingService;
import com.successfactors.t2.service.SessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CheckinServiceImpl implements CheckinService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CheckinDAO checkinDAO;

    @Autowired
    private SessionService  sessionService;

    @Autowired
    private RankingService rankingService;

    @Autowired
    private CacheService cacheService;

    @Override
    public String generateCheckinCode() {
        Map<Integer, String> codeMap = cacheService.getCheckinCodeCache();
        int codeSize = codeMap.size();
        Random random = new Random();
        Integer number = random.nextInt(codeSize) + 1;
        logger.info("code number:" + number);
        return codeMap.get(number);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int confirmCheckinCode(Integer sessionId, String code, String userId) {
        int status = sessionService.updateCheckinCode(sessionId, code);
        status += rankingService.updatePointsForHost(sessionId, userId);
        return status;
    }
}
