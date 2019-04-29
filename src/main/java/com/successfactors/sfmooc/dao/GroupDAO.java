package com.successfactors.sfmooc.dao;

import com.successfactors.sfmooc.domain.Group;
import com.successfactors.sfmooc.domain.User;

import java.util.List;

public interface GroupDAO {
    List<Group> getGroups();
    boolean isUserInGroup(String userId, Integer groupId);
    int addUserToGroup(String userId, Integer groupId);
    int removeUserFromGroup(String userId, Integer groupId);
    int addGroup(String groupName, Integer sharePoints);
    int markSessionAsShared(String userId, Integer sessionId);
    List<User> getUserByGroupId(Integer groupId);
}
