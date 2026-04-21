package com.apphub.backend.dto;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

public class Upgrade_resume_request {
    private String jobPosition; 
    private String fileUrl;
    private LocalDateTime uploadedAt;
    private String fileName;
    private String fileType;
    private Long fileSize;
    private MultipartFile file;


    public String getJobPosition() { return jobPosition; }
    public String getFileUrl() { return fileUrl; }
    public LocalDateTime getUploadedAt() { return uploadedAt; }
    public String getFileName() { return fileName; }
    public String getFileType() { return fileType; }
    public Long getFileSize(){return fileSize;}
    public MultipartFile getFile(){return file;}


    public void setJobPosition(String jobPosition) { this.jobPosition = jobPosition; }
    public void setFileUrl(String fileUrl) { this.fileUrl = fileUrl; }
    public void setUploadedAt(LocalDateTime uploadedAt) { this.uploadedAt = uploadedAt; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    public void setFileType(String fileType) { this.fileType = fileType; }
    public void setFileSize(Long fileSize) {this.fileSize = fileSize; }
    public void setFile(MultipartFile file){this.file = file; }
    
}
