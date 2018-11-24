package com.successfactors.sfmooc.service.impl;

import com.successfactors.sfmooc.dao.CheckinDAO;
import com.successfactors.sfmooc.dao.SessionDAO;
import com.successfactors.sfmooc.domain.FetchParams;
import com.successfactors.sfmooc.domain.Session;
import com.successfactors.sfmooc.domain.SessionVO;
import com.successfactors.sfmooc.domain.UserSession;
import com.successfactors.sfmooc.service.CacheService;
import com.successfactors.sfmooc.service.CheckinService;
import com.successfactors.sfmooc.service.RankingService;
import com.successfactors.sfmooc.service.SessionService;
import com.successfactors.sfmooc.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class SessionServiceImpl implements SessionService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CheckinService checkinService;

    @Autowired
    private SessionDAO sessionDAO;

    @Override
    public int register(String userId, Integer sessionId) {
        return sessionDAO.register(userId, sessionId);
    }

    @Override
    public int editSession(Session session) {
        if(session.getId() == null || session.getId() == 0){
            String imageSrc = checkinService.generateTileImageSrc();
            logger.info("Generate image src is: " + imageSrc);
            session.setTileImageSrc(imageSrc);
        }
        return sessionDAO.editSession(session);
    }

    @Override
    public List<Session> getSessionList(FetchParams fetchParams) {
        return sessionDAO.getSessionList(fetchParams);
    }

    @Override
    public UserSession getSessionById(Integer sessionId, String userId) {
        return sessionDAO.getSessionById(sessionId, userId);
    }

    @Override
    public int getEnrollments(Integer sessionId) {
        return sessionDAO.getEnrollments(sessionId);
    }

    @Override
    public int batchDelete(List<Integer> sessionIdList) {
        return sessionDAO.batchDelete(sessionIdList);
    }

    @Override
    public int cancel(Integer sessionId) {
        return sessionDAO.cancel(sessionId);
    }

    @Override
    public String start(String userId, Integer sessionId) {
        String checkInCode = checkinService.generateCheckinCode();
        logger.info("CheckIn code: " + checkInCode);
        checkinService.confirmCheckinCode(sessionId, checkInCode, userId);
        return checkInCode;
    }

    @Override
    public String getCheckInCode(Integer sessionId) {
        return sessionDAO.getCheckInCode(sessionId);
    }

    //    public List<Session> getSessionList() {
//       // List<Session> sessionList = (List<Session>) cacheService.getSessionCache().values();
//        String season = getCurrentSeason();
//        List<Session> sessionList = sessionDAO.getSessionList(season);
//        if(sessionList == null || sessionList.isEmpty()){
//            return sessionList;
//        }
//        Collections.sort(sessionList, new Comparator<Session>() {
//            @Override
//            public int compare(Session o1, Session o2) {
//                return o1.getSessionDate().compareTo(o2.getSessionDate());
//            }
//        });
//        if (sessionList != null) {
//            String currentDate = DateUtil.formatDate(new Date());
//            boolean findNext = false;
//            for (Session session : sessionList) {
//                String sessionDate = session.getSessionDate();
//                if (sessionDate.compareTo(currentDate) < 0) {
//                    session.setStatus(1);
//                } else {
//                    if (!findNext) {
//                        session.setStatus(2);
//                        findNext = true;
//                    } else {
//                        session.setStatus(3);
//                    }
//                }
//            }
//        }
//        return sessionList;
//    }
//
//    @Override
//    public Session getSessionByDate(String date){
//        return sessionDAO.getSessionByDate(date);
//    }
//
//    @Override
//    public Session getSessionByOwner(String owner){
//       // return userToSessionCache.get(owner);
//        return  sessionDAO.getSessionByOwner(owner);
//    }
//
//    @Override
//    public int updateCheckinCode(Integer sessionId, String checkinCode) {
//        int status = sessionDAO.updateCheckinCode(sessionId, checkinCode);
//        if(status > 0){
//            Session session = cacheService.getSessionFromCacheById(sessionId);
//            session.setCheckinCode(checkinCode);
//            cacheService.updateSessionCache(session);
//        }
//        return status;
//    }
//
//    @Override
//    public boolean isSessionOwnerOfToday(String userId) {
//        if(userId == null || userId.isEmpty()){
//            return false;
//        }
//        String today = DateUtil.formatDate(new Date());
//        Session session = getSessionByOwner(userId);
//        if(session != null && today.equals(session.getSessionDate())){
//            return true;
//        }
//        return false;
//    }
//
    @Override
    public Set<String> getAttendeeList(Integer sessionId) {
        List<String> userList = sessionDAO.getAttendeeList(sessionId);
        if(userList != null){
            return new LinkedHashSet<>(userList);
        }
        return null;
    }

    @Override
    public int updateLuckyNumber(Integer sessionId, Integer luckyNumber) {
        return sessionDAO.updateLuckyNumber(sessionId, luckyNumber);
    }
//
//    @Override
//    public List<SessionVO> loadHistorySessions() {
//        return sessionDAO.loadHistorySessions();
//    }
//
//    @Override
//    public String getCurrentSeason() {
//        return sessionDAO.getCurrentSeason();
//    }
}
