package com.successfactors.sfmooc.service;

import com.successfactors.sfmooc.domain.User;
import com.successfactors.sfmooc.domain.Group;
import java.util.List;

public interface UserService {
    String getOpenId(String code);
    public int addUser(User user);
    User getUserById(String userId);
    List<User> getUsersByOrder();
    int editUserInfo(User user);
    List<Group> getUserGroup(String userId);
}
