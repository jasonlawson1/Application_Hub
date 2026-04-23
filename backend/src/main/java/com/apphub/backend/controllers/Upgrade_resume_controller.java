package com.apphub.backend.controllers;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.apphub.backend.Services.Upgrade_resume_service;

@RestController
@RequestMapping("/api/upgrade_resume")
public class Upgrade_resume_controller {
    private final Upgrade_resume_service upgrade_resume_service;

    public Upgrade_resume_controller(Upgrade_resume_service upgrade_resume_service) {
        this.upgrade_resume_service = upgrade_resume_service;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> upload_resume(@RequestParam("file") MultipartFile file,
            @RequestParam("userId") Long userId) {
        try {
            String url = upgrade_resume_service.upload_resume(file, userId);
            return ResponseEntity.ok(Map.of("url", url));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Failed to upload resume file"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Unexpected server error"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get_resume(@PathVariable Long id) {
        String url = upgrade_resume_service.get_presigned_url(id);
        return ResponseEntity.ok(Map.of("url", url));
    }
}
