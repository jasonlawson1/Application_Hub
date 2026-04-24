package com.apphub.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.apphub.backend.models.User;

@Repository
public interface User_repository extends JpaRepository <User, Long>{

    /*Returns true if a user with the given email address already exists. */
    Boolean existsByEmail(String email);

    /*Returns the user with the given email address, or null if not found. */
    User findByEmail(String email);


}
