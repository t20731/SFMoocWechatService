package com.successfactors.sfmooc.controller;

import com.successfactors.sfmooc.domain.*;
import com.successfactors.sfmooc.service.DirectionService;
import com.successfactors.sfmooc.service.LocationService;
import com.successfactors.sfmooc.service.SessionService;
import com.successfactors.sfmooc.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/session")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private DirectionService directionService;

    @Autowired
    private LocationService locationService;

    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public Result init() {
        SessionVO sessionVO = new SessionVO();
        List<Direction> directions = directionService.getAll();
        List<Location> locations = locationService.getAll();
        sessionVO.setDirections(directions);
        sessionVO.setLocations(locations);
        return new Result(1, Constants.SUCCESS, sessionVO);
    }

    @RequestMapping(value = "/all", method = RequestMethod.POST)
    public Result loadAll(@RequestBody FetchParams fetchParams) {
        SessionVO sessionVO = new SessionVO();
        List<Direction> directions = directionService.getAll();
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
//    @RequestMapping(value = "/usercount", method = RequestMethod.GET)
//    public Result getNumOfAttendee(){
//        Set<String> userList = sessionService.getAttendeeList();
//        return new Result(0, Constants.SUCCESS, userList == null ? 0 : userList.size());
//    }

}
