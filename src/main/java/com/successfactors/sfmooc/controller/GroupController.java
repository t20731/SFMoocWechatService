package com.successfactors.sfmooc.controller;

import com.successfactors.sfmooc.domain.Group;
import com.successfactors.sfmooc.service.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/group")
public class GroupController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GroupService groupService;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public List<Group> getGroups(){
        return groupService.getGroups();
    }


}
