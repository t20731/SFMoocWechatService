package com.successfactors.sfmooc.service.impl;

import com.successfactors.sfmooc.dao.DirectionDAO;
import com.successfactors.sfmooc.domain.Direction;
import com.successfactors.sfmooc.service.DirectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DirectionServiceImpl implements DirectionService{

    @Autowired
    private DirectionDAO directionDAO;

    @Override
    public List<Direction> getAll() {
        return directionDAO.getAll();
    }
}
