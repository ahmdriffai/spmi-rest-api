package id.ac.fksp.spmi.controller;

import id.ac.fksp.spmi.util.S3BucketStorageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/s3-bucket")
public class S3BucketStorageController {
    @Autowired
    S3BucketStorageUtil s3BucketStorageUtil;

    @PostMapping("/file/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("fileName") String deskripsi,
                                        @RequestParam("file")MultipartFile file) {
        String folder = "fileku";

        String uploadFile = s3BucketStorageUtil.uploadFile(folder, file);

        return ResponseEntity.ok(uploadFile);
    }
}
