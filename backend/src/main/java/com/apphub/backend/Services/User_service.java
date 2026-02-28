package com.apphub.backend.Services;


import org.springframework.stereotype.Service;
import com.apphub.backend.models.User;
import com.apphub.backend.repositories.User_repository;

@Service
public class User_service {

    private final User_repository user_repository;
    public User_service(User_repository user_repository){
        this.user_repository=user_repository;
    }



    public void insert_user(String first, String last, String email, String password, String confirm_password){
        User user= new User(first, last, email, password);
        user_repository.save(user);
        
    }

    public boolean email_exists(String email){
        if(user_repository.existsByEmail(email)){
            return true;
        }
        return false;
    }

    public boolean passwords_match(String password, String confirm_password){
        if (password.equals(confirm_password)){
            return true;
        }
        return false;
    }


}
