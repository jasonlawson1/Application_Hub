package com.apphub.backend.models;

import java.time.LocalDateTime;



import jakarta.persistence.*;

@Entity
@Table(name="Uploaded_Resumes")
public class Upgrade_resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String jobPosition;

    @Column(nullable = false)
    private String fileUrl;


    private LocalDateTime uploadedAt;
    private String fileName;
    private String fileType;
    private double fileSize;
    private String upgradedResumeFileName;

    public Upgrade_resume() {
    }

    public Upgrade_resume(User user, String jobPosition, String fileUrl, LocalDateTime uploadedAt, String fileName, String fileType, double fileSize) {
        this.user = user;
        this.jobPosition = jobPosition;
        this.fileUrl = fileUrl;
        this.uploadedAt = uploadedAt;
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileSize = fileSize;
    }

    public Upgrade_resume(User user, String fileUrl, LocalDateTime uploadedAt, String fileName, String fileType, double fileSize) {
        this.user = user;
        this.fileUrl = fileUrl;
        this.uploadedAt = uploadedAt;
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileSize = fileSize;
    }

    public Long getId() {
        return id;
    }
    public User getUser() {
        return user;
    }
    public String getJobPosition() {
        return jobPosition;
    }
    public String getFileUrl() {
        return fileUrl;
    }
    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }
    public String getFileName() {
        return fileName;
    }
    public String getFileType() {
        return fileType;
    }

    public double getFileSize(){
        return fileSize;
    }

    public String getUpgradedResumeFileName(){
        return upgradedResumeFileName;
    }
   


 
    public void setUser(User user) {
        this.user = user;
    }
    public void setJobPosition(String jobPosition) {
        this.jobPosition = jobPosition;
    }
    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public void setFileSize(Long fileSize){
        this.fileSize = fileSize;
    }
    public void setUpgradedResumeFileName(String upgradedResumeFileName){
        this.upgradedResumeFileName = upgradedResumeFileName;
    }
}