package com.apphub.backend.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apphub.backend.models.Interview;

public interface Interview_repository extends JpaRepository<Interview, Long> {

    /*Returns all interviews linked to the application with the given ID. */
    List<Interview> findByApplicationId(Long applicationId);

    /*Returns all interviews on or after the given date, sorted by date ascending. */
    List<Interview> findByInterviewDateGreaterThanEqualOrderByInterviewDateAsc(LocalDate date);
}
