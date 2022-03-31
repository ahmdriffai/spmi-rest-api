//package id.ac.fksp.spmi.util;
//
//import com.amazonaws.AmazonServiceException;
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.model.CannedAccessControlList;
//import com.amazonaws.services.s3.model.ObjectMetadata;
//import com.amazonaws.services.s3.model.PutObjectRequest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Map;
//import java.util.Optional;
//
//@Service
//public class FileStorageUtil {
//    @Autowired
//    private AmazonS3 s3Client;
//
//    @Value("${s3.space.bucket}")
//    private String doSpaceBucket;
//
//
//    public void save(String path,
//                     String fileName,
//                     Optional<Map<String, String>> optionalMetadata,
//                     InputStream inputStream){
//        ObjectMetadata metadata = new ObjectMetadata();
//        optionalMetadata.ifPresent(map -> {
//            if (!map.isEmpty()){
//                map.forEach(metadata::addUserMetadata);
//            }
//        });
//
//        try{
//            s3Client.putObject(new PutObjectRequest(path,fileName,inputStream,metadata).withCannedAcl(CannedAccessControlList.PublicRead));
////            s3Client.putObject(path, fileName, inputStream, metadata);
//        }catch (AmazonServiceException exception){
//            throw new IllegalStateException("Failed to store file to s3 ", exception);
//        }
//    }
//
//    public void deleteFile(Long id) throws IOException {
//
//    }
//
//    private void saveImageToServer(MultipartFile multipartFile, String key) throws IOException {
//        ObjectMetadata metadata = new ObjectMetadata();
//        metadata.setContentLength(multipartFile.getInputStream().available());
//        if (multipartFile.getContentType() != null && !"".equals(multipartFile.getContentType())){
//            metadata.setContentType(multipartFile.getContentType());
//        }
//
//        s3Client.putObject(new PutObjectRequest(doSpaceBucket, key,multipartFile.getInputStream(),metadata)
//                .withCannedAcl(CannedAccessControlList.PublicRead));
//    }
//}
//
