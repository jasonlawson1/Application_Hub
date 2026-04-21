package com.apphub.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apphub.backend.models.Upgrade_resume;
import com.apphub.backend.models.User;

public interface Upgrade_resume_repository extends JpaRepository<Upgrade_resume, Long> {
     Boolean existsByUser(User user);
    
}
