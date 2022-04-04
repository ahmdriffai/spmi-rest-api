package id.ac.fksp.spmi.service.impl;

import id.ac.fksp.spmi.entity.Announcement;
import id.ac.fksp.spmi.exception.AnnouncementException;
import id.ac.fksp.spmi.payload.request.AnnouncementListRequest;
import id.ac.fksp.spmi.payload.request.AnnouncementSaveRequest;
import id.ac.fksp.spmi.payload.request.AnnouncementUpdateRequest;
import id.ac.fksp.spmi.payload.response.AnnouncementResponse;
import id.ac.fksp.spmi.repository.AnnouncementRepository;
import id.ac.fksp.spmi.service.AnnouncementService;
import id.ac.fksp.spmi.util.S3BucketStorageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service @Transactional
public class AnnouncementSercviceImpl implements AnnouncementService {

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private S3BucketStorageUtil s3BucketStorageUtil;

    @Override
    public List<AnnouncementResponse> listAnnouncement(AnnouncementListRequest request) {
        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize(), Sort.by("createdAt").descending());
        Page<Announcement> announcementPage = announcementRepository.findAll(pageRequest);
        List<Announcement> announcements = announcementPage.get().collect(Collectors.toList());

        return announcements.stream().map(this::convertResponse).collect(Collectors.toList());
    }

    @Override
    public AnnouncementResponse saveAnnouncement(AnnouncementSaveRequest request) {

        String imageLink = "noLink";
        String pdfLink = "noLink";
        //upload image
        if (!request.getImage().isEmpty()){
            imageLink = s3BucketStorageUtil.uploadFile("announcement/img", request.getImage());
        }

        //upload image
        if (!request.getPdf().isEmpty()){
            pdfLink = s3BucketStorageUtil.uploadFile("announcement/pdf", request.getPdf());
        }


        Announcement announcement = new Announcement();
        announcement.setTitle(request.getTitle());
        announcement.setContent(request.getContent());
        announcement.setImageLink(imageLink);
        announcement.setPdfLink(pdfLink);
        announcement.setCreatedAt(new Date());
        announcementRepository.save(announcement);

        return convertResponse(announcement);
    }

    @Override
    public AnnouncementResponse getAnnouncement(Long id) {
        Optional<Announcement> optionalAnnouncement = announcementRepository.findById(id);
        if (optionalAnnouncement.isEmpty()){
            throw new AnnouncementException("Data tidak ditemukan");
        }

        return convertResponse(optionalAnnouncement.get());
    }

    @Override
    public AnnouncementResponse updateAnnouncement(Long id, AnnouncementUpdateRequest request) {
        Optional<Announcement> optionalAnnouncement = announcementRepository.findById(id);
        if (optionalAnnouncement.isEmpty()){
            throw new AnnouncementException("ID data tidak ditemukan");
        }

        String imageLink = "noLink";
        String pdfLink = "noLink";
        //upload image
        if (!request.getImage().isEmpty()){
            imageLink = s3BucketStorageUtil.uploadFile("announcement/img", request.getImage());
        }

        //upload image
        if (!request.getPdf().isEmpty()){
            pdfLink = s3BucketStorageUtil.uploadFile("announcement/pdf", request.getPdf());
        }

        // update data
        Announcement announcement = optionalAnnouncement.get();
        announcement.setTitle(request.getTitle());
        announcement.setContent(announcement.getContent());
        announcement.setImageLink(imageLink);
        announcement.setPdfLink(pdfLink);
//
//        //delete file from s3
//        var image = announcement.getImageLink().split("/");
//        var pdf = announcement.getPdfLink().split("/");
//        boolean file = s3BucketStorageUtil.deleteFile(image[image.length - 1]);
//        boolean file1 = s3BucketStorageUtil.deleteFile(pdf[pdf.length - 1]);
//
//        if (!(file && file1)){
//            throw new AnnouncementException("gagal update file");
//        }

        Announcement save = announcementRepository.save(announcement);

        return convertResponse(save);
    }

    private AnnouncementResponse convertResponse(Announcement announcement){
        return new AnnouncementResponse(
                announcement.getId(),
                announcement.getTitle(),
                announcement.getContent(),
                announcement.getImageLink(),
                announcement.getPdfLink(),
                announcement.getCreatedAt()
        );
    }
}
