package com.successfactors.t2.dao;

import com.successfactors.t2.domain.Announcement;

import java.util.List;

public interface AnnouncementDAO {
    int editAnnouncement(Announcement announcement);
    List<Announcement> getAnnouncementList();
}
