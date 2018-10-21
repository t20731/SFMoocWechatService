package com.successfactors.sfmooc.service;

import com.successfactors.sfmooc.domain.User;

public interface UserService {
    String getOpenId(String code);
    public int addUser(User user);
    User getUserById(String userId);
}
