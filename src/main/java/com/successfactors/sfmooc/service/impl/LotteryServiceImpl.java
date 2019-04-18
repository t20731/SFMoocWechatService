package com.successfactors.sfmooc.service.impl;

import com.successfactors.sfmooc.dao.LotteryDAO;
import com.successfactors.sfmooc.domain.LotteryResult;
import com.successfactors.sfmooc.domain.LuckyDog;
import com.successfactors.sfmooc.service.LotteryService;
import com.successfactors.sfmooc.service.RankingService;
import com.successfactors.sfmooc.service.SessionService;
import com.successfactors.sfmooc.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class LotteryServiceImpl implements LotteryService{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LotteryDAO lotteryDAO;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private RankingService rankingService;

    private volatile static String sysDate = DateUtil.formatDate(new Date());

    private volatile static List<Integer> luckyNumbers = new ArrayList<>();

    private volatile static int hitCount = 0;

    @Override
    public int bet(String userId, Integer sessionId, Integer number) {
        return lotteryDAO.bet(userId, sessionId, number);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public LotteryResult draw(Integer sessionId) {
        Set<String> userList = sessionService.getAttendeeList(sessionId);
        if(userList != null && userList.size() > 0){
            int number = getLuckyNumber(userList.size());
            logger.info("Lucky number is: " + number);
            if (number > 0) {
                List<LuckyDog> luckyDogs = lotteryDAO.getLuckyDogs(sessionId, number);
                if(!CollectionUtils.isEmpty(luckyDogs)){
                    sessionService.updateLuckyNumber(sessionId, number);
                    rankingService.updatePointsForLottery(sessionId, luckyDogs);
                }
                return new LotteryResult(number, luckyDogs);
            }
            return new LotteryResult(number, new ArrayList<>());
        }
        return new LotteryResult(0, new ArrayList<>());
    }

//    @Override
//    public LotteryResult query() {
//        return lotteryDAO.query();
//    }

//    private int getLuckyNumber(int size) {
//        Random random = new Random();
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < size; i++) {
//            int number = Math.abs(random.nextInt() % size) + 1;
//            sb.append(number).append(",");
//        }
//        logger.info("numbers : " + sb.toString());
//        return Math.abs(random.nextInt() % size) + 1;
//    }

    private synchronized int getLuckyNumber(int size) {
        logger.info("hit count: " + hitCount);
        logger.info("sys date: " + sysDate);
        String today = DateUtil.formatDate(new Date());
        if (!today.equals(sysDate)) {
            logger.info("current date not equals sys date");
            sysDate = today;
            luckyNumbers.clear();
            initNumberArray(luckyNumbers, size);
        } else {
            if (luckyNumbers.size() == 0 || luckyNumbers.size() != size ) {
                initNumberArray(luckyNumbers, size);
            }
        }
        logger.info("Lucky Numbers in " + sysDate + " are: " + luckyNumbers);
        return luckyNumbers.get(hitCount++ % size);
    }


    private void initNumberArray(List<Integer> numbers, int size){
        for (int i = 1; i <= size; i++) {
            numbers.add(i);
        }
        for(int i = 0; i < size; i++){
            Collections.shuffle(numbers);
        }
    }
}
