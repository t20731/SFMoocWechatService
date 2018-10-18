package com.successfactors.t2.controller;

import com.successfactors.t2.domain.Result;
import com.successfactors.t2.domain.Session;
import com.successfactors.t2.service.SessionService;
import com.successfactors.t2.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/session")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @RequestMapping(value="/list", method = RequestMethod.GET)
    public List<Session> getSessionList(){
        return sessionService.getSessionList();
    }

    @RequestMapping(value = "/{date}", method = RequestMethod.GET)
    public Result getSessionByDate(@PathVariable("date") String date){
        Session session = sessionService.getSessionByDate(date);
        if(session == null){
            return new Result(0, Constants.NOT_AUTHORIZED);
        }else{
            return new Result(0, Constants.SUCCESS, session);
        }
    }

    @RequestMapping(value = "/usercount", method = RequestMethod.GET)
    public Result getNumOfAttendee(){
        Set<String> userList = sessionService.getAttendeeList();
        return new Result(0, Constants.SUCCESS, userList == null ? 0 : userList.size());
    }

}
