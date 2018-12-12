package com.successfactors.sfmooc.service;

import com.successfactors.sfmooc.domain.*;
import java.util.List;

public interface UserService {
    String getOpenId(String code);
    public int addUser(User user);
    User getUserById(String userId);
    List<User> getUsersByOrder();
    List<Group> getUserGroup(String userId);
}
