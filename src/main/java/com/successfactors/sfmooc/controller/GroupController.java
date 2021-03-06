package com.successfactors.sfmooc.controller;

import com.successfactors.sfmooc.domain.Group;
import com.successfactors.sfmooc.domain.Result;
import com.successfactors.sfmooc.domain.User;
import com.successfactors.sfmooc.service.GroupService;
import com.successfactors.sfmooc.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/group")
public class GroupController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GroupService groupService;

    @RequestMapping(value = "list/{userId}", method = RequestMethod.GET)
    public List<Group> getGroups(@PathVariable("userId") String userId){
        return groupService.getGroups(userId);
    }

    @RequestMapping(value = "join", method = RequestMethod.POST)
    public Result addUserToGroup(@RequestBody Map paramsMap) {
        if (paramsMap == null) {
            return new Result(-1, Constants.ILLEGAL_ARGUMENT);
        }
        String userId = (String) paramsMap.get("userId");
        Integer groupId = (Integer) paramsMap.get("groupId");
        if (userId == null || groupId == null) {
            return new Result(-1, Constants.ILLEGAL_ARGUMENT);
        }
        int status = groupService.addUserToGroup(userId, groupId);
        if (status > 0) {
            return new Result(status, Constants.SUCCESS);
        } else {
            return new Result(status, Constants.ERROR);
        }
    }

    @RequestMapping(value = "leave", method = RequestMethod.POST)
    public Result removeUserFromGroup(@RequestBody Map paramsMap) {
        if (paramsMap == null) {
            return new Result(-1, Constants.ILLEGAL_ARGUMENT);
        }
        String userId = (String) paramsMap.get("userId");
        Integer groupId = (Integer) paramsMap.get("groupId");
        if (userId == null || groupId == null) {
            return new Result(-1, Constants.ILLEGAL_ARGUMENT);
        }
        int status = groupService.removeUserFromGroup(userId, groupId);
        if (status > 0) {
            return new Result(status, Constants.SUCCESS);
        } else {
            return new Result(status, Constants.ERROR);
        }
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public Result addGroup(@RequestBody Map paramsMap) {
        if (paramsMap == null) {
            return new Result(-1, Constants.ILLEGAL_ARGUMENT);
        }
        String groupName = (String) paramsMap.get("name");
        Integer sharePoints = (Integer) paramsMap.get("point");
        if (groupName == null) {
            return new Result(-1, Constants.ILLEGAL_ARGUMENT);
        }
        /*share point is 5 by default*/
        if(sharePoints == null) {
            sharePoints = 5;
        }
        int status = groupService.addGroup(groupName,sharePoints);
        if (status > 0) {
            return new Result(status, Constants.SUCCESS);
        } else {
            return new Result(status, Constants.ERROR);
        }
    }

    @RequestMapping(value = "listUser/{groupId}", method = RequestMethod.GET)
    public List<User> getUsersByGroupId(@PathVariable("groupId") Integer groupId){
        return groupService.getUserByGroupId(groupId);
    }

    @RequestMapping(value = "listAll", method = RequestMethod.GET)
    public List<Group> getAllGroups(){
        return groupService.getAllGroups();
    }

    @RequestMapping(value = "delete/{groupId}", method = RequestMethod.POST)
    public Result deleteGroup(@PathVariable("groupId") Integer groupId) {
        if (groupId == null || groupId == 1) {
            return new Result(-1, Constants.ILLEGAL_ARGUMENT);
        }
        return new Result(groupService.deleteGroup(groupId), Constants.SUCCESS);
    }

}
