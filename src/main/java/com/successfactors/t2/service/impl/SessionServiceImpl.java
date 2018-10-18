package com.successfactors.t2.service.impl;

import com.successfactors.t2.dao.SessionDAO;
import com.successfactors.t2.domain.Session;
import com.successfactors.t2.domain.SessionVO;
import com.successfactors.t2.service.CacheService;
import com.successfactors.t2.service.SessionService;
import com.successfactors.t2.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SessionServiceImpl implements SessionService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SessionDAO sessionDAO;

    @Autowired
    private CacheService cacheService;


    @Override
    public List<Session> getSessionList() {
       // List<Session> sessionList = (List<Session>) cacheService.getSessionCache().values();
        String season = getCurrentSeason();
        List<Session> sessionList = sessionDAO.getSessionList(season);
        if(sessionList == null || sessionList.isEmpty()){
            return sessionList;
        }
        Collections.sort(sessionList, new Comparator<Session>() {
            @Override
            public int compare(Session o1, Session o2) {
                return o1.getSessionDate().compareTo(o2.getSessionDate());
            }
        });
        if (sessionList != null) {
            String currentDate = DateUtil.formatDate(new Date());
            boolean findNext = false;
            for (Session session : sessionList) {
                String sessionDate = session.getSessionDate();
                if (sessionDate.compareTo(currentDate) < 0) {
                    session.setStatus(1);
                } else {
                    if (!findNext) {
                        session.setStatus(2);
                        findNext = true;
                    } else {
                        session.setStatus(3);
                    }
                }
            }
        }
        return sessionList;
    }

    @Override
    public Session getSessionByDate(String date){
        return sessionDAO.getSessionByDate(date);
    }

    @Override
    public Session getSessionByOwner(String owner){
       // return userToSessionCache.get(owner);
        return  sessionDAO.getSessionByOwner(owner);
    }

    @Override
    public int updateCheckinCode(Integer sessionId, String checkinCode) {
        int status = sessionDAO.updateCheckinCode(sessionId, checkinCode);
        if(status > 0){
            Session session = cacheService.getSessionFromCacheById(sessionId);
            session.setCheckinCode(checkinCode);
            cacheService.updateSessionCache(session);
        }
        return status;
    }

    @Override
    public boolean isSessionOwnerOfToday(String userId) {
        if(userId == null || userId.isEmpty()){
            return false;
        }
        String today = DateUtil.formatDate(new Date());
        Session session = getSessionByOwner(userId);
        if(session != null && today.equals(session.getSessionDate())){
            return true;
        }
        return false;
    }

    @Override
    public Set<String> getAttendeeList() {
        List<String> userList = sessionDAO.getAttendeeList();
        if(userList != null){
            return new LinkedHashSet<>(userList);
        }
        return null;
    }

    @Override
    public int updateLuckyNumber(Integer sessionId, Integer luckyNumber) {
        return sessionDAO.updateLuckyNumber(sessionId, luckyNumber);
    }

    @Override
    public List<SessionVO> loadHistorySessions() {
        return sessionDAO.loadHistorySessions();
    }

    @Override
    public String getCurrentSeason() {
        return sessionDAO.getCurrentSeason();
    }
}
