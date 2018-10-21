package com.successfactors.sfmooc.controller;

import com.successfactors.sfmooc.domain.*;
import com.successfactors.sfmooc.service.DirectionService;
import com.successfactors.sfmooc.service.SessionService;
import com.successfactors.sfmooc.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/session")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private DirectionService directionService;

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
        List<Session> sessions = sessionService.getSessionList(fetchParams);
        return new Result(1, Constants.SUCCESS, sessions);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public Result editSession(@RequestBody Session session) {
        int status = sessionService.editSession(session);
        return new Result(status, Constants.SUCCESS);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result getSessionById(@PathVariable("id") Integer id) {
        Session session = sessionService.getSessionById(id);
        if (session == null) {
            return new Result(0, Constants.NO_DATA);
        } else {
            return new Result(0, Constants.SUCCESS, session);
        }
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
