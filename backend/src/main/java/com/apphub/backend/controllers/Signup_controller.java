package com.apphub.backend.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apphub.backend.Services.User_service;
import com.apphub.backend.models.Users;


@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api")
public class Signup_controller {

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Map<String, Object> user_data) {
        Users user = new Users();
     
        User_service user_service = new User_service();
        String first=(String) user_data.get("first_name");
        String last=(String) user_data.get("last_name");
        String email=(String) user_data.get("email");
        String password=(String) user_data.get("password");
        String confirm_password=(String) user_data.get("confirm_password");

       
        if(user_service.insert_user(first, last, email, password, confirm_password)==true){
             return ResponseEntity.ok(Map.of("message", "User created successfully"));
        }
        else{
            return ResponseEntity.badRequest().body(Map.of("error", "Username already exists"));
        }

        
       
    }
    
}
