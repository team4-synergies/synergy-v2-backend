package com.synergies.synergyv2.config.S3;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class FileService {

    //bucketName
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String s3Region;

    private final AmazonS3 amazonS3;

    public void uploadFile(String fileName, boolean manager, MultipartFile file) {

        String uploadPath;
        if(manager) {
            uploadPath = bucket + "/admin/";
        }
        else {
            uploadPath = bucket + "/student/";
        }

        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());
            InputStream inputStream = file.getInputStream();

            amazonS3.putObject(new PutObjectRequest(bucket, uploadPath+fileName, inputStream, metadata)
                                .withCannedAcl(CannedAccessControlList.PublicRead));
            inputStream.close();
        } catch (AmazonServiceException | IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteFile(boolean manager, String storedFileName) throws AmazonServiceException {
        String filePath;
        if(manager)
            filePath = "/admin/"+storedFileName;
        else
            filePath = "/student/"+storedFileName;

        amazonS3.deleteObject(new DeleteObjectRequest(bucket, bucket+filePath));
    }

    public String getUrl() {
        return "https://"+bucket+".s3."+s3Region+".amazonaws.com/"+bucket;
    }
}
