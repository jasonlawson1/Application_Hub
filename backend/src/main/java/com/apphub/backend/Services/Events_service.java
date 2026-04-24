package com.apphub.backend.Services;

import com.apphub.backend.models.User;

import java.util.List;


import org.springframework.stereotype.Service;

import com.apphub.backend.dto.Events_request;
import com.apphub.backend.models.Event;
import com.apphub.backend.repositories.Events_repository;
import com.apphub.backend.repositories.User_repository;

@Service
public class Events_service {

    private final User_repository user_repository;
    private final Events_repository events_repository;


    /*Constructs the service with the required user and events repositories. */
    public Events_service(User_repository user_repository, Events_repository events_repository){
        this.user_repository = user_repository;
        this.events_repository = events_repository;
    }

    /*Creates and saves a new event for the user identified in the request. */
    public Boolean create_event(Events_request request){
        User user = user_repository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Event event = new Event(
            request.getTitle(),
            request.getLocation(),
            request.getDate(),
            request.getStartTime(),
            request.getNotes()
            );
        event.setUser(user);
        return events_repository.save(event) !=null;
    }

    /*Returns all events belonging to the specified user. */
    public List<Event> get_events_by_user_id(Long userId){
        User user = user_repository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return events_repository.getEventsByUser(user);

    }

    /*Returns the event with the given ID mapped to an Events_request DTO. */
    public Events_request get_event_by_id(Long id){
        Event event = events_repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        Long userId = event.getUser().getID();
        return new Events_request(
                event.getTitle(),
                event.getLocation(),
                event.getDate(),
                event.getStartTime(),
                event.getNotes(),
                userId,
                id
            );

    }

    /*Updates the fields of an existing event identified by the given ID. */
    public Boolean update_event(Long id, Events_request request){
        Event event = events_repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        event.setTitle(request.getTitle());
        event.setLocation(request.getLocation());
        event.setDate(request.getDate());
        event.setStartTime(request.getStartTime());
        event.setNotes(request.getNotes());

        return (events_repository.save(event)!=null);

    }

    /*Deletes the event with the given ID and returns true if it existed. */
    public Boolean delete_event(Long id){
        if(events_repository.existsById(id)){
            events_repository.deleteById(id);
            return true;
        }
        return false;
    }

}
