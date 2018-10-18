package com.successfactors.t2.service.impl;

import com.successfactors.t2.dao.AnnouncementDAO;
import com.successfactors.t2.domain.Announcement;
import com.successfactors.t2.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnouncementServiceImpl implements AnnouncementService{

    @Autowired
    private AnnouncementDAO announcementDAO;

    @Override
    public int editAnnouncement(Announcement announcement) {
        return announcementDAO.editAnnouncement(announcement);
    }

    @Override
    public List<Announcement> getAnnouncementList() {
        return announcementDAO.getAnnouncementList();
    }
}
