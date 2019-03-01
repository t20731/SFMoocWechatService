package com.successfactors.sfmooc.controller;

import com.successfactors.sfmooc.domain.*;
import com.successfactors.sfmooc.service.DirectionService;
import com.successfactors.sfmooc.service.LocationService;
import com.successfactors.sfmooc.service.SessionService;
import com.successfactors.sfmooc.service.UserService;
import com.successfactors.sfmooc.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/session")
public class SessionController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SessionService sessionService;

    @Autowired
    private DirectionService directionService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public Result init() {
        SessionVO sessionVO = new SessionVO();
        List<Direction> directions = directionService.getAll();
        List<Location> locations = locationService.getAll();
        sessionVO.setDirections(directions);
        sessionVO.setLocations(locations);
        return new Result(1, Constants.SUCCESS, sessionVO);
    }

    @RequestMapping(value = "/init/{userId}", method = RequestMethod.GET)
    public Result initWithUserId(@PathVariable ("userId") String id) {
        SessionVO sessionVO = new SessionVO();
        List<Direction> directions = directionService.getAll();
        List<Location> locations = locationService.getAll();
        List<Group> groups = userService.getUserGroup(id);
        sessionVO.setDirections(directions);
        sessionVO.setLocations(locations);
        sessionVO.setGroups(groups);
        return new Result(1, Constants.SUCCESS, sessionVO);
    }


    @RequestMapping(value = "/all", method = RequestMethod.POST)
    public Result loadAll(@RequestBody FetchParams fetchParams) {
        SessionVO sessionVO = new SessionVO();
        List<Direction> directions = directionService.getAll();
        if(!CollectionUtils.isEmpty(directions)){
            if(!"All".equalsIgnoreCase(directions.get(0).getName())){
                fetchParams.setDirectionId(directions.get(0).getId());
            }
        }
        FetchParams fetchParamsHot = new FetchParams();
        fetchParamsHot.setPageNum(1);
        fetchParamsHot.setPageSize(3);
        fetchParamsHot.setOrderField(Constants.TOTAL_MEMBERS);
        List<Session> hotSessions = sessionService.getSessionList(fetchParamsHot);
        sessionVO.setHotSessions(hotSessions);
        List<Session> sessions = sessionService.getSessionList(fetchParams);
        sessionVO.setDirections(directions);
        sessionVO.setSessions(sessions);
        return new Result(1, Constants.SUCCESS, sessionVO);
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Result getSessionList(@RequestBody FetchParams fetchParams) {
        if(fetchParams != null && !StringUtils.isEmpty(fetchParams.getOrderField())){
            if(!Constants.ORDER_FIELD_SET.contains(fetchParams.getOrderField())){
                return new Result(-1, Constants.ILLEGAL_ARGUMENT);
            }
        }
        List<Session> sessions = sessionService.getSessionList(fetchParams);
        return new Result(1, Constants.SUCCESS, sessions);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public Result editSession(@RequestBody Session session) {
        logger.info("session start_date: " + session.getStartDate());
        logger.info("session end_date: " + session.getEndDate());
        int status = sessionService.editSession(session);
        return new Result(status, Constants.SUCCESS);
    }

    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public Result getSessionById(@RequestBody Enrollment enrollment) {
        if(enrollment == null || enrollment.getSessionId() == null ||
                enrollment.getSessionId() == 0){
            return new Result(-1, Constants.ILLEGAL_ARGUMENT);
        }
        UserSession session = sessionService.getSessionById(enrollment.getSessionId(), enrollment.getUserId());
        if (session == null) {
            return new Result(0, Constants.NO_DATA);
        } else {
            int enrollments = sessionService.getEnrollments(enrollment.getSessionId());
            session.getSession().setEnrollments(enrollments);
            int sessionCount = sessionService.getSessionLikeCount(enrollment.getSessionId());
            session.getSession().setLikeCount(sessionCount);
            if(session.isUserRegistered()){
                int userLike = sessionService.getLike( enrollment.getUserId(),enrollment.getSessionId());
                session.setUserLike(userLike);
            }
            return new Result(1, Constants.SUCCESS, session);
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Result register(@RequestBody Enrollment enrollment) {
        if(StringUtils.isEmpty(enrollment.getUserId()) || enrollment.getSessionId() == null ||
                enrollment.getSessionId() == 0){
            return new Result(-1, Constants.ILLEGAL_ARGUMENT);
        }
        int status = sessionService.register(enrollment.getUserId(), enrollment.getSessionId());
        Map<String, Integer> retObj = new HashMap<>(1);
        int enrollments = sessionService.getEnrollments(enrollment.getSessionId());
        retObj.put("enrollments", enrollments);
        if(status == 0){
            return new Result(status, Constants.REGISTERED, retObj);
        }
        return new Result(status, Constants.SUCCESS, retObj);
    }

    @RequestMapping(value = "/unregister", method = RequestMethod.POST)
    public Result unRegister(@RequestBody Map paramsMap) {
        if (paramsMap == null) {
            return new Result(-1, Constants.ILLEGAL_ARGUMENT);
        }
        String userId = (String) paramsMap.get("userId");
        Integer sessionId = (Integer) paramsMap.get("sessionId");
        if (StringUtils.isEmpty(userId) || sessionId == 0) {
            return new Result(-1, Constants.ILLEGAL_ARGUMENT);
        }
        int status = sessionService.unRegister(userId, sessionId);
        if (status > 0) {
            return new Result(status, Constants.SUCCESS);
        } else {
            return new Result(status, Constants.NO_DATA);
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable ("id") Integer id){
        if(id == null || id == 0){
            return new Result(-1, Constants.ILLEGAL_ARGUMENT);
        }
        int enrollments = sessionService.getEnrollments(id);
        if(enrollments > 0) {
            return new Result(-1, Constants.ILLEGAL_ARGUMENT);
        }
        int status = sessionService.delete(id);
        return new Result(status, Constants.SUCCESS);
    }

    @RequestMapping(value = "/cancel/{id}", method = RequestMethod.GET)
    public Result cancel(@PathVariable ("id") Integer id){
        if(id == null || id == 0){
            return new Result(-1, Constants.ILLEGAL_ARGUMENT);
        }
        int enrollments = sessionService.getEnrollments(id);
        if(enrollments == 0) {
            return new Result(-1, Constants.ILLEGAL_ARGUMENT);
        }
        int status = sessionService.cancel(id);
        return new Result(status, Constants.SUCCESS);
    }

    @RequestMapping(value = "/start", method = RequestMethod.POST)
    public Result start(@RequestBody Map params){
        if(params == null){
            return new Result(-1, Constants.ILLEGAL_ARGUMENT);
        }
        String userId = (String) params.get("userId");
        Integer sessionId = (Integer) params.get("sessionId");
        if (StringUtils.isEmpty(userId) || sessionId == null || sessionId == 0) {
            return new Result(-1, Constants.ILLEGAL_ARGUMENT, null);
        }
        int enrollments = sessionService.getEnrollments(sessionId);
        if(enrollments < Constants.MIN_MEMBERS){
            return new Result(-1, Constants.NOT_AUTHORIZED);
        }
        Map<String, Object> retObj = sessionService.start(userId, sessionId);
        return new Result(1, Constants.SUCCESS, retObj);
    }

    @RequestMapping(value = "/usercount/{sessionId}", method = RequestMethod.GET)
    public Result getNumOfAttendee(@PathVariable("sessionId") Integer sessionId){
        Set<String> userList = sessionService.getAttendeeList(sessionId);
        return new Result(0, Constants.SUCCESS, userList == null ? 0 : userList.size());
    }

    @RequestMapping(value = "/like", method = RequestMethod.POST)
    public Result like(@RequestBody Map params){
        if(params == null){
            return new Result(-1, Constants.ILLEGAL_ARGUMENT);
        }
        String userId = (String) params.get("userId");
        Integer sessionId = (Integer) params.get("sessionId");
        Integer like = (Integer) params.get("like");

        if (StringUtils.isEmpty(userId) || sessionId == null || sessionId == 0 ) {
            return new Result(-1, Constants.ILLEGAL_ARGUMENT, null);
        }
        if (like != 0 && like != 1){
            return new Result(-1, Constants.ILLEGAL_ARGUMENT, null);
        }
        Set<String> userList = sessionService.getAttendeeList(sessionId);
        if (userList != null && userList.contains(userId)) {
            int result = sessionService.like(userId, sessionId, like);
            if(result == 0){
                return new Result(-1, Constants.ERROR);
            }
            Map<String, Object> retObj = new HashMap<>(1);
            retObj.put("updatedRowCount", result);
            retObj.put("returnedLike", like);
            return new Result(1, Constants.SUCCESS, retObj);
        } else {
            return new Result(0, Constants.ERROR, 0);
        }
    }

    @RequestMapping(value = "/getlike", method = RequestMethod.POST)
    public Result getlike(@RequestBody Map params){
        if(params == null){
            return new Result(-1, Constants.ILLEGAL_ARGUMENT);
        }
        String userId = (String) params.get("userId");
        Object sessionIdObj= params.get("sessionId");
        Integer sessionId = 0;
        if (StringUtils.isEmpty(userId) || sessionIdObj == null  ) {
            return new Result(-1, Constants.ILLEGAL_ARGUMENT, null);
        }
        if (sessionIdObj instanceof String){
            sessionId = Integer.valueOf((String) sessionIdObj);
        }
        if(sessionIdObj instanceof Integer){
            sessionId = (Integer) sessionIdObj;
        }

        int like = sessionService.getLike(userId, sessionId);
        if(like == -1){
            return new Result(-1, Constants.ERROR);
        } else {
            return new Result(1, Constants.SUCCESS, like);
        }
    }

    @RequestMapping(value = "/registeredUsers/{sessionId}", method = RequestMethod.GET)
    public Result getRegisteredUsers(@PathVariable ("sessionId") Integer sessionId){
        if(sessionId == null || sessionId == 0){
            return new Result(-1, Constants.ILLEGAL_ARGUMENT);
        }
        List<String> userList = sessionService.getRegisteredUsers(sessionId);
        return new Result(1, Constants.SUCCESS, userList);
    }
//    @RequestMapping(value="/list", method = RequestMethod.GET)
//    public List<Session> getSessionList(){
//        return sessionService.getSessionList();
//    }
//
//    @RequestMapping(value = "/{date}", method = RequestMethod.GET)
//    public Result getSessionByDate(@PathVariable("date") String date){
//        Session session = sessionService.getSessionByDate(date);
//        if(session == null){
//            return new Result(0, Constants.NOT_AUTHORIZED);
//        }else{
//            return new Result(0, Constants.SUCCESS, session);
//        }
//    }
//

}
