package com.apphub.backend.Services;


import org.springframework.stereotype.Service;
import com.apphub.backend.models.Users;

@Service
public class User_service {

    public boolean insert_user(String first, String last, String email, String password, String confirm_password){
        Users user= new Users();
        user.setFirst(first);
        user.setLast(last);
        user.setEmail(email);
        user.setFirst(password);
        user.setConfirmPassword(confirm_password);
        
        return true;
    }


}
