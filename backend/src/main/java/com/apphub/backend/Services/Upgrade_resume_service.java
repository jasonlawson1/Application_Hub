package com.apphub.backend.Services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.apphub.backend.models.Upgrade_resume;
import com.apphub.backend.models.User;
import com.apphub.backend.repositories.Upgrade_resume_repository;
import com.apphub.backend.repositories.User_repository;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

@Service
public class Upgrade_resume_service {

    private final Upgrade_resume_repository upgrade_resume_repository;
    private final User_repository user_repository;
    private final S3Client s3Client;
    private final String resume_bucket = "uploaded-resume-s3";
    private final S3Presigner s3Presigner;

    public Upgrade_resume_service(Upgrade_resume_repository upgrade_resume_repository, S3Client s3Client,
            User_repository user_repository, S3Presigner s3Presigner) {
        this.upgrade_resume_repository = upgrade_resume_repository;
        this.s3Client = s3Client;
        this.user_repository = user_repository;
        this.s3Presigner = s3Presigner;
    }

    public void upload_to_s3(String fileName, byte[] file_bytes) {
        try {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(resume_bucket)
                    .key(fileName)
                    .build();

            s3Client.putObject(request,
                    RequestBody.fromInputStream(
                            new ByteArrayInputStream(file_bytes),
                            file_bytes.length));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("S3 upload failed " + e.getMessage(), e);
        }
    }

    public String upload_resume(MultipartFile file, Long userId) throws IOException {
        try {
            String fileName = "resumes/" + userId + "/" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
            byte[] file_bytes = file.getBytes();
            upload_to_s3(fileName, file_bytes);

            LocalDateTime uploadedAt = LocalDateTime.now();
            save_resume(file, fileName, userId, uploadedAt);

            return get_presigned_url_by_filename(fileName);
        } catch (IOException e) {
            throw new IOException("Failed to process file upload", e);
        }
    }

    public Upgrade_resume save_resume(MultipartFile file, String fileName, Long userId, LocalDateTime uploadedAt) {
        try {
            User user = user_repository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            String fileUrl = "https://" + resume_bucket + ".s3.amazonaws.com/" + fileName;
            String fileType = file.getContentType();
            double fileSize = Math.round((file.getSize() / 1024.0) * 100.0) / 100.0;
            Upgrade_resume resume = new Upgrade_resume(user, fileUrl, uploadedAt, fileName, fileType, fileSize);
            return upgrade_resume_repository.save(resume);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Saving resume data to table failed: " + e.getMessage());
        }
    }

    public String get_presigned_url_by_filename(String fileName) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(resume_bucket)
                .key(fileName)
                .build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(10))
                .getObjectRequest(getObjectRequest)
                .build();

        PresignedGetObjectRequest presignedRequest = s3Presigner.presignGetObject(presignRequest);
        return presignedRequest.url().toString();
    }

    public String get_presigned_url(long fileId) {
        Upgrade_resume resume = upgrade_resume_repository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("could not find resume in database"));
        return get_presigned_url_by_filename(resume.getFileName());
    }
}