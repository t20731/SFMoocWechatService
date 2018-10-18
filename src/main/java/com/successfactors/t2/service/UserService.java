package com.successfactors.t2.service;

import com.successfactors.t2.domain.User;

public interface UserService {
    String getOpenId(String code);
    public int addUser(User user);
    User getUserById(String userId);
}
