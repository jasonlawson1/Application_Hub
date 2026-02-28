package com.apphub.backend.controllers;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class Splash_controller {
      @GetMapping("/")
    public String home() {
        return "Splash";
    }
}
