package com.successfactors.sfmooc.dao;

import com.successfactors.sfmooc.domain.Group;
import com.successfactors.sfmooc.domain.User;
import java.util.List;

public interface UserDAO {
    public int addUser(User user);
    User getUserById(String userId);
    List<User> getUsersByOrder();
    List<Group> getUserGroup(String userId);
}
