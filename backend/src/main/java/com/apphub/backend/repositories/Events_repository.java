package com.apphub.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apphub.backend.models.Event;
import com.apphub.backend.models.User;

public interface Events_repository extends JpaRepository<Event, Long> {

    /*Returns all events associated with the given user. */
    List<Event> getEventsByUser(User user);


}
