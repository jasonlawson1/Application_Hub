package com.apphub.backend.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.apphub.backend.models.User;
import com.apphub.backend.repositories.User_repository;

@Service
public class User_service {

    @Autowired
    private PasswordEncoder password_encoder;

    private final User_repository user_repository;

    /*Constructs the service with the required user repository. */
    public User_service(User_repository user_repository){
        this.user_repository=user_repository;
    }

    /*encodes the password and saves a new user with the provided details to the database. */
    public void insert_user(String first, String last, String email, String password, String confirm_password){
        String hashed_password = password_encoder.encode(password);
        User user= new User(first, last, email, hashed_password);
        user_repository.save(user);

    }

    /*Returns true if an account with the given email already exists. */
    public boolean email_exists(String email){
       //if email exists return true else return false
        return user_repository.existsByEmail(email);
    }

    /*Returns true if the password and confirmation string are identical. */
    public boolean passwords_match(String password, String confirm_password){
        // return true if pw's match else return false
        return password.equals(confirm_password);
    }

    /*Authenticates a user by email and password, returning the User if credentials are valid or null otherwise. */
    public User login_user(String email, String password){
        User user= user_repository.findByEmail(email);

        //if user is not null and pw is correct return user
        if(user!=null && password_encoder.matches(password,user.getPassword())){
            return user;
        }
        return null;

    }


}
