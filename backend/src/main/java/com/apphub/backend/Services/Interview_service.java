package com.apphub.backend.Services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.apphub.backend.models.Application;
import com.apphub.backend.models.Interview;
import com.apphub.backend.repositories.Application_repository;
import com.apphub.backend.repositories.Interview_repository;

@Service
public class Interview_service {

    private final Interview_repository interview_repository;
    private final Application_repository application_repository;

    /*Constructs the service with the required interview and application repositories. */
    public Interview_service(Interview_repository interview_repository,
                             Application_repository application_repository) {
        this.interview_repository = interview_repository;
        this.application_repository = application_repository;
    }

    /*Creates and saves a new interview linked to the specified application. */
    public Interview create_interview_for_application(Long applicationId, Interview interview) {
        Optional<Application> applicationOptional = application_repository.findById(applicationId);

        if (applicationOptional.isPresent()) {
            Application application = applicationOptional.get();
            interview.setApplication(application);
            return interview_repository.save(interview);
        }

        return null;
    }

    /*Returns all interviews stored in the database. */
    public List<Interview> get_all_interviews() {
        return interview_repository.findAll();
    }

    /*Returns all interviews associated with the specified application. */
    public List<Interview> get_interviews_by_application_id(Long applicationId) {
        return interview_repository.findByApplicationId(applicationId);
    }

    /*Returns the interview with the given ID, or null if not found. */
    public Interview get_interview_by_id(Long id) {
        Optional<Interview> interview = interview_repository.findById(id);
        return interview.orElse(null);
    }

    /*Updates the fields of an existing interview identified by the given ID. */
    public Interview update_interview(Long id, Interview updatedInterview) {
        Optional<Interview> existing = interview_repository.findById(id);

        if (existing.isPresent()) {
            Interview interview = existing.get();
            interview.setCompany(updatedInterview.getCompany());
            interview.setJobTitle(updatedInterview.getJobTitle());
            interview.setInterviewDate(updatedInterview.getInterviewDate());
            interview.setInterviewTime(updatedInterview.getInterviewTime());
            interview.setInterviewType(updatedInterview.getInterviewType());
            interview.setLocation(updatedInterview.getLocation());
            interview.setStatus(updatedInterview.getStatus());
            interview.setNotes(updatedInterview.getNotes());
            return interview_repository.save(interview);
        }

        return null;
    }

    /*Deletes the interview with the given ID and returns true if it existed. */
    public boolean delete_interview(Long id) {
        if (interview_repository.existsById(id)) {
            interview_repository.deleteById(id);
            return true;
        }
        return false;
    }

    /*Returns all upcoming interviews ordered by date ascending from today. */
    public List<Interview> get_upcoming_interviews() {
        return interview_repository.findByInterviewDateGreaterThanEqualOrderByInterviewDateAsc(LocalDate.now());
    }

    /*Returns the total count of all interviews in the database. */
    public long get_total_interviews() {
        return interview_repository.count();
    }
}
