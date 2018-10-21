package com.successfactors.sfmooc.dao;

import com.successfactors.sfmooc.domain.User;

public interface UserDAO {
    public int addUser(User user);
    User getUserById(String userId);
}
