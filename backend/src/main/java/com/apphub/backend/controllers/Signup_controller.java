package com.apphub.backend.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.apphub.backend.models.Users;
import com.apphub.backend.repositories.User_repository;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api")
public class Signup_controller {

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Map<String, Object> user_data) {
        Users user = new Users();
        //User_repository user_repository = new User_repository();
        String first=(String) user_data.get("first_name");
        String last=(String) user_data.get("last_name");
        String email=(String) user_data.get("email");
        String password=(String) user_data.get("password");
        String confirm_password=(String) user_data.get("confirm_password");

        user.setFirst(first);
        user.setLast(last);
        user.setEmail(email);
        user.setFirst(password);
        user.setConfirmPassword(confirm_password);

        //send data to server to save to database

        
        return ResponseEntity.ok(Map.of("message", "User created successfully"));
    }
    
}
