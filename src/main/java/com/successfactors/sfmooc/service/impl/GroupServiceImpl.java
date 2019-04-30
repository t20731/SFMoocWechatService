package com.successfactors.sfmooc.service.impl;

import com.successfactors.sfmooc.dao.GroupDAO;
import com.successfactors.sfmooc.domain.Group;
import com.successfactors.sfmooc.domain.User;
import com.successfactors.sfmooc.service.GroupService;
import com.successfactors.sfmooc.utils.Constants;
import com.successfactors.sfmooc.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GroupServiceImpl implements GroupService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GroupDAO groupDAO;

    @Autowired
    private Environment env;

    @Override
    public List<Group> getGroups(String userId) {
        List<Group> groups = groupDAO.getGroups();
        String openGroups = env.getProperty("open.enroll.groups");
        String[] groupArray = openGroups.split(",");
        Set<String> groupNameSet = new HashSet<>(Arrays.asList(groupArray));
        if(userId != null && !Constants.DEFAULT_USER_ID.equalsIgnoreCase(userId)) {
            for (Group group : groups) {
                if (groupNameSet.contains(group.getName())) {
                    boolean isUserInGroup = groupDAO.isUserInGroup(userId, group.getId());
                    group.setCanJoin(!isUserInGroup);
                }
            }
        }
        return groups;
    }

    public List<Group> getAllGroups() { return groupDAO.getGroupsWithUserNum();}

    @Override
    public int addUserToGroup(String userId, Integer groupId) {
        return groupDAO.addUserToGroup(userId, groupId);
    }

    @Override
    public int removeUserFromGroup(String userId, Integer groupId) {  return groupDAO.removeUserFromGroup(userId,groupId); }

    @Override
    public int addGroup(String groupName, Integer sharePoints) {
        return groupDAO.addGroup(groupName,sharePoints);
    }

    @Override
    public List<User> getUserByGroupId(Integer groupId) {
        return groupDAO.getUserByGroupId(groupId);
    }

}
