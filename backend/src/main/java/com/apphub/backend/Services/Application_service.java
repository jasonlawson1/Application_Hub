package com.apphub.backend.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.apphub.backend.dto.Application_Request;
import com.apphub.backend.models.Application;
import com.apphub.backend.models.User;
import com.apphub.backend.repositories.Application_repository;
import com.apphub.backend.repositories.User_repository;


@Service
public class Application_service {

    private final Application_repository application_repository;
    private final User_repository user_repository;

    /*Constructs the service with the required application and user repositories. */
    public Application_service(Application_repository application_repository, User_repository user_repository) {
        this.application_repository = application_repository;
        this.user_repository = user_repository;
    }

    /*Creates and saves a new job application for the user identified in the request. */
    public Boolean create_application(Application_Request request) {
        User user = user_repository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Application application = new Application(
                request.getJobTitle(),
                request.getCompany(),
                request.getStatus(),
                request.getDateApplied(),
                request.getDeadline(),
                request.getLocation(),
                request.getNotes()
        );
        application.setUser(user);
        return application_repository.save(application) != null;
    }

    /*Returns all job applications stored in the database. */
    public List<Application> get_all_applications() {
        return application_repository.findAll();
    }

    /*Returns the application with the given ID, or null if not found. */
    public Application get_application_by_id(Long id) {
        Optional<Application> application = application_repository.findById(id);
        return application.orElse(null);
    }

    
    /*Returns all job applications belonging to the specified user. */
    public List<Application> get_applications_by_user_id(Long userId) {
        return application_repository.findByUserId(userId);
    }

    /*Updates the fields of an existing application identified by the given ID. */
    public Application update_application(Long id, Application updated_application) {
        Optional<Application> existing = application_repository.findById(id);

        if (existing.isPresent()) {
            Application application = existing.get();
            application.setJobTitle(updated_application.getJobTitle());
            application.setCompany(updated_application.getCompany());
            application.setStatus(updated_application.getStatus());
            application.setDateApplied(updated_application.getDateApplied());
            application.setDeadline(updated_application.getDeadline());
            application.setLocation(updated_application.getLocation());
            application.setNotes(updated_application.getNotes());
            return application_repository.save(application);
        }

        return null;
    }

    /*Deletes the application with the given ID and returns true if it existed. */
    public boolean delete_application(Long id) {
        if (application_repository.existsById(id)) {
            application_repository.deleteById(id);
            return true;
        }
        return false;
    }
}
