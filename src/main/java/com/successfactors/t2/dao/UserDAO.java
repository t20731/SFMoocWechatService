package com.successfactors.t2.dao;

import com.successfactors.t2.domain.User;

public interface UserDAO {
    public int addUser(User user);
    User getUserById(String userId);
}
