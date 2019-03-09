package com.successfactors.sfmooc.service.impl;

import com.successfactors.sfmooc.dao.CheckinDAO;
import com.successfactors.sfmooc.dao.GroupDAO;
import com.successfactors.sfmooc.dao.SessionDAO;
import com.successfactors.sfmooc.service.CacheService;
import com.successfactors.sfmooc.service.CheckinService;
import com.successfactors.sfmooc.service.RankingService;
import com.successfactors.sfmooc.service.SessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class CheckinServiceImpl implements CheckinService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CheckinDAO checkinDAO;

    @Autowired
    private SessionDAO sessionDAO;

    @Autowired
    private GroupDAO groupDAO;

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
    public String generateTileImageSrc() {
        Map<Integer, String> imageMap = cacheService.getTileImageCache();
        List<Integer> imageIdList = new ArrayList<>(imageMap.keySet());
        int imageSize = imageIdList.size();
        Random random = new Random();
        Integer number = random.nextInt(imageSize);
        Integer imageId = imageIdList.get(number);
        logger.info("image id is:" + imageId);
        return imageMap.get(imageId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int confirmCheckinCode(Integer sessionId, String code, String userId) {
         sessionDAO.updateCheckinCode(sessionId, code);
         groupDAO.markSessionAsShared(userId, sessionId);
         return rankingService.updatePointsForHost(sessionId, userId);
    }
}
