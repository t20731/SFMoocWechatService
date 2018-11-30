package com.successfactors.sfmooc.dao;

import com.successfactors.sfmooc.domain.Announcement;

import java.util.List;

public interface AnnouncementDAO {
    int editAnnouncement(Announcement announcement);
    List<Announcement> getAnnouncementList();
}
