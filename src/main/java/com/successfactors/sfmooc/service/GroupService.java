package com.successfactors.sfmooc.service;

import com.successfactors.sfmooc.domain.Group;

import java.util.List;

public interface GroupService {
    List<Group> getGroups(String userId);
    int addUserToGroup(String userId, Integer groupId);
}
