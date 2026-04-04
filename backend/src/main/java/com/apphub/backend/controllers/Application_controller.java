package com.apphub.backend.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.apphub.backend.Services.Application_service;

import com.apphub.backend.dto.Application_Request;
import com.apphub.backend.models.Application;


@RestController
@RequestMapping("/api/applications")
public class Application_controller {

    private final Application_service application_service;
   

    public Application_controller(Application_service application_service) {
        this.application_service = application_service;
        
    }

    @PostMapping
    public ResponseEntity<?> create_application(@RequestBody Application_Request request) {
        if (application_service.create_application(request)) {
            return ResponseEntity.ok(Map.of("message", "Application saved successfully"));
        } else {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of("message", "Application was not submitted"));
        }
    }

    @GetMapping
    public ResponseEntity<List<Application>> get_all_applications() {
        return ResponseEntity.ok(application_service.get_all_applications());
    }

    // Returns all applications belonging to a specific user
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> get_applications_by_userId(@PathVariable Long userId) {
        try{
            List<Application> applications = application_service.get_applications_by_user_id(userId);
            return ResponseEntity.ok(applications==null ? new ArrayList<>(): applications);

        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of(
                        "message", "Backend error while fetching applications",
                        "error", e.getMessage()
                ));
        }
      
        
    }
    
    // Returns one application so user can edit it
    @GetMapping("/{id}")
    public ResponseEntity<?> get_application_by_id(@PathVariable Long id) {
    Application application = application_service.get_application_by_id(id);

    if (application != null) {
        return ResponseEntity.ok(application);
    }

    return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(Map.of("message", "Application not found"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update_application(@PathVariable Long id, @RequestBody Application updated_application) {
        Application application = application_service.update_application(id, updated_application);

        if (application != null) {
            return ResponseEntity.ok(application);
        }

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", "Application not found"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete_application(@PathVariable Long id) {
        boolean deleted = application_service.delete_application(id);

        if (deleted) {
            return ResponseEntity.ok(Map.of("message", "Application deleted successfully"));
        }

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", "Application not found"));
    }
}