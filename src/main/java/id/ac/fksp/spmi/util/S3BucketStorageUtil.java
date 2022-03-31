package id.ac.fksp.spmi.util;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;


@Service
public class S3BucketStorageUtil {
    private Logger logger = LoggerFactory.getLogger(S3BucketStorageUtil.class);

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${s3.do.bucket}")
    private String bucketName;

    public String uploadFile(String folder, MultipartFile file) {
        String fileName = null;
        try {
            String path = String.format("%s/%s", bucketName, folder);

            String filenameExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());
            fileName = String.format("%s-%s.%s", file.getName(), UUID.randomUUID(), filenameExtension);


            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentLength(file.getInputStream().available());
            amazonS3.putObject(new PutObjectRequest(path, fileName, file.getInputStream(), metadata).withCannedAcl(CannedAccessControlList.PublicRead));


        } catch (IOException ioException) {
            logger.error("IOException: " + ioException.getMessage());
        } catch (AmazonServiceException serviceException) {
            logger.error("AmazonServiceException: " + serviceException.getMessage());
            throw serviceException;
        } catch (AmazonClientException clientException) {
            logger.error("AmazonClientException: " + clientException.getMessage());
        }
        String spaceLink = "https://storage-fksp.sgp1.digitaloceanspaces.com";
        return String.format("%s/%s/%s", spaceLink,folder, fileName);
    }
}
