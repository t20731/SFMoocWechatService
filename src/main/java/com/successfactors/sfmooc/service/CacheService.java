package com.successfactors.sfmooc.service;

import com.successfactors.sfmooc.dao.CheckinDAO;
import com.successfactors.sfmooc.dao.SessionDAO;
import com.successfactors.sfmooc.domain.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CacheService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static Map<String, Session> userToSessionCache = new ConcurrentHashMap<>();

    private static Map<Integer, Session> sessionCache = new ConcurrentHashMap<>();

    private Map<Integer, String> checkinCodeCache = new ConcurrentHashMap<>();

    @Autowired
    private SessionDAO sessionDAO;

    @Autowired
    private CheckinDAO checkinDAO;

//    public void loadDataOnStartUp(){
//        initializeSessionCache();
//        initializeCodeCache();
//    }
//
//    private void initializeSessionCache() {
//        try {
//            logger.info("Start to load data to session cache...");
//            String season = sessionDAO.getCurrentSeason();
//            List<Session> sessionList = sessionDAO.getSessionList(season);
//            if (sessionList != null && !sessionList.isEmpty()) {
//                for (Session session : sessionList) {
//                    userToSessionCache.put(session.getOwner(), session);
//                    sessionCache.put(session.getSessionId(), session);
//                }
//            }
//            logger.info("Load " + userToSessionCache.size() + " sessions to cache");
//        } catch (Exception e) {
//            logger.error("Failed to load session to cache", e);
//        }
//    }
//
//    private void initializeCodeCache() {
//        try {
//            logger.info("Start to load code into cache...");
//            Map<Integer, String> codeMap = checkinDAO.getAllCodes();
//            if (codeMap != null && !codeMap.isEmpty()) {
//                checkinCodeCache.putAll(codeMap);
//            }
//            logger.info("Load "+checkinCodeCache.size()+" codes into cache");
//        } catch (Exception e) {
//            logger.error("Fail to load code to cache", e);
//        }
//    }
//
//    public  Map<String, Session> getUserToSessionCache() {
//        return userToSessionCache;
//    }
//
//    public  Map<Integer, Session> getSessionCache() {
//        return sessionCache;
//    }
//
//    public Map<Integer, String> getCheckinCodeCache() {
//        return checkinCodeCache;
//    }
//
//    public Session getSessionFromCacheById(Integer sessionId){
//        return sessionCache.get(sessionId);
//    }
//
//    public void updateSessionCache(Session session){
//        sessionCache.put(session.getSessionId(), session);
//        userToSessionCache.put(session.getOwner(), session);
//    }

}
