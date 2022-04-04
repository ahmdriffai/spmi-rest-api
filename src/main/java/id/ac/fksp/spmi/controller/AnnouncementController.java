package id.ac.fksp.spmi.controller;

import id.ac.fksp.spmi.payload.request.AnnouncementListRequest;
import id.ac.fksp.spmi.payload.request.AnnouncementSaveRequest;
import id.ac.fksp.spmi.payload.request.AnnouncementUpdateRequest;
import id.ac.fksp.spmi.payload.response.AnnouncementResponse;
import id.ac.fksp.spmi.payload.response.ApiResponse;
import id.ac.fksp.spmi.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/announcements")
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @PostMapping
    public ResponseEntity<?> saveAnnouncement(
            @RequestParam("title") @Valid String title,
            @RequestParam("content") @Valid String content,
            @RequestParam("image") @Valid MultipartFile image,
            @RequestParam("pdf") @Valid MultipartFile pdf
    ){

        AnnouncementSaveRequest request = new AnnouncementSaveRequest(
                title,content,image,pdf
        );

        AnnouncementResponse response = announcementService.saveAnnouncement(request);

        return ResponseEntity.ok(new ApiResponse<AnnouncementResponse>(
                true,
                "save success",
                response
        ));

    }

    @GetMapping
    public ResponseEntity<?> listAnnouncements(
            @RequestParam(value = "page",defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size
    ){
        AnnouncementListRequest request = new AnnouncementListRequest(page,size);
        List<AnnouncementResponse> responses = announcementService.listAnnouncement(request);

        return ResponseEntity.ok(new ApiResponse<List<AnnouncementResponse>>(
                true,
                "list announcement",
                responses
        ));
    }

    @GetMapping("/{announcementId}")
    public ResponseEntity<?> getAnnouncement(@PathVariable Long announcementId){
        AnnouncementResponse announcement = announcementService.getAnnouncement(announcementId);
        return ResponseEntity.ok(new ApiResponse<AnnouncementResponse>(
                true,
                "succes get user by id : " + announcement.getTitle(),
                announcement
        ));
    }

    @PutMapping("/{announcementId}")
    public ResponseEntity<?> updateAnnouncement(
            @PathVariable Long announcementId,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("image") MultipartFile image,
            @RequestParam("pdf") MultipartFile pdf
    ){

        @Valid
        AnnouncementUpdateRequest request = new AnnouncementUpdateRequest(title,content,image,pdf);

        AnnouncementResponse response = announcementService.updateAnnouncement(announcementId,request);

        return ResponseEntity.ok(new ApiResponse<AnnouncementResponse>(
                true,
                "succes update announcement",
                response
        ));
    }
}
