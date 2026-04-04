package com.apphub.backend.controllers;

import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.apphub.backend.Services.Events_service;
import com.apphub.backend.dto.Events_request;
import com.apphub.backend.models.Event;

@RestController
@RequestMapping("/api/events")
public class Events_controller {
    private final Events_service events_service;

   

    public Events_controller(Events_service events_service){
        this.events_service = events_service;
    }

    @PostMapping("/create_event")
    public ResponseEntity<?> create_event (@RequestBody Events_request request){
        if(events_service.create_event(request)){
            return ResponseEntity.ok(Map.of("message", "Event created"));
        }
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of("message", "Event was not created"));
    }

    @GetMapping("/get_events/{userId}")
    public ResponseEntity<?> get_events(@PathVariable Long userId){
       List <Event> events = events_service.get_events_by_user_id(userId);
        try{
             return ResponseEntity.ok(events==null? new ArrayList<>(): events);
        
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of(
                        "message", "Backend error while fetching events",
                        "error", e.getMessage()
                ));
            }  
        }
      
    

    @GetMapping("/{id}")
    public ResponseEntity<?> get_event_by_id(@PathVariable Long id){
        Events_request event_request = events_service.get_event_by_id(id);
        if(event_request !=null){
            return ResponseEntity.ok(event_request);
        }
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", "Event not found with id: " + id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update_event(@PathVariable Long id, @RequestBody Events_request request){
       if(events_service.update_event(id, request)) {
        return ResponseEntity.ok(Map.of("message", "Event updated successfully"));
       }
       return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", "Event not found with id: " + id));

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete_event (@PathVariable Long id){
        if(events_service.delete_event(id)){
            return ResponseEntity.ok(Map.of("message", "Event deleted successfully"));
        }
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", "Event not found with id: " + id));
    }
    
}
