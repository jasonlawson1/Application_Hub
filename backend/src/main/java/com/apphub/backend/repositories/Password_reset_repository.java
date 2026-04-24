package com.apphub.backend.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.apphub.backend.models.Forgot_password_token;
import com.apphub.backend.models.User;

public interface Password_reset_repository extends JpaRepository<Forgot_password_token, Long>{

    /*Returns true if a password reset token exists for the given user. */
    Boolean existsByUser(User user);

    /*Deletes the password reset token associated with the given user. */
    void deleteByUser(User user);

    /*Returns the password reset token associated with the given email address. */
    Forgot_password_token getByEmail(String email);


}
