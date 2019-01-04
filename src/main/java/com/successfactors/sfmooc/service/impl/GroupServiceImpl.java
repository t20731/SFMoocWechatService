package com.successfactors.sfmooc.service.impl;

import com.successfactors.sfmooc.dao.GroupDAO;
import com.successfactors.sfmooc.domain.Group;
import com.successfactors.sfmooc.service.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GroupDAO groupDAO;

    @Override
    public List<Group> getGroups() {
        return groupDAO.getGroups();
    }

}
