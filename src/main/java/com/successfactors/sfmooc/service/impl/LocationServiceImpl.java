package com.successfactors.sfmooc.service.impl;

import com.successfactors.sfmooc.dao.LocationDAO;
import com.successfactors.sfmooc.domain.Location;
import com.successfactors.sfmooc.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService{

    @Autowired
    private LocationDAO locationDAO;

    @Override
    public List<Location> getAll() {
        return locationDAO.getAll();
    }
}
