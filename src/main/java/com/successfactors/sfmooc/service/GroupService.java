package com.successfactors.sfmooc.service;

import com.successfactors.sfmooc.domain.Group;
import com.successfactors.sfmooc.domain.User;

import java.util.List;

public interface GroupService {
    List<Group> getGroups(String userId);
    int addUserToGroup(String userId, Integer groupId);
    int removeUserFromGroup(String userId, Integer groupId);
    int addGroup(String groupName, Integer sharePoints);
    List<User> getUserByGroupId(Integer groupId);
}
