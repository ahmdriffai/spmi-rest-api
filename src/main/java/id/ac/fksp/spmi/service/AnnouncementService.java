package id.ac.fksp.spmi.service;

import id.ac.fksp.spmi.payload.request.AnnouncementListRequest;
import id.ac.fksp.spmi.payload.request.AnnouncementSaveRequest;
import id.ac.fksp.spmi.payload.response.AnnouncementResponse;

import java.util.List;

public interface AnnouncementService {
    List<AnnouncementResponse> listAnnouncement(AnnouncementListRequest request);
    AnnouncementResponse saveAnnouncement(AnnouncementSaveRequest request);
    AnnouncementResponse getAnnouncement(Long id);
}
